package mgrid.software.software.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

import mgrid.software.software.BitConverter;
import mgrid.software.software.DataAccess;
import mgrid.software.software.HistData;
import mgrid.software.software.HistDataCurve;
import mgrid.software.software.R;

import org.json.JSONArray;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

public class PacketParse {
	SQLiteDatabase sqldb;
	public static int headsize = 23;
	public int length;

	public void updatedatabase(String name) {

		//
		String sql = "insert into  stations (Name) values (" + "'" + name + "')";
		DataAccess.sqldb.execSQL(sql);

	}

	public void updateroomsdatabase(String name) {

		//
		String sql = "insert into  rooms (Name) values (" + "'" + name + "')";
		DataAccess.sqldb.execSQL(sql);

	}

	public void updateequipmentsdatabase(String name) {

		//
		String sql = "insert into  equipments (Name) values (" + "'" + name + "')";
		DataAccess.sqldb.execSQL(sql);

	}

	public void updatesingalsdatabase(String name) {

		//
		String sql = "insert into  signals (Name) values (" + "'" + name + "')";
		DataAccess.sqldb.execSQL(sql);

	}

	public void ClearTable(String name) {

		//
		DataAccess.sqldb.execSQL("delete from " + name);

	}

	public PacketParse() {

	}

	public static void parseCustomConfig(byte[] buffer) {

		if (buffer.length < PacketParse.headsize) {

			return;
		}
		// DataAccess.m_realSignals.clear();
		int index = PacketParse.headsize;
		int totaldata = 0;
		String name;

		for (int i = 0; i < 8; i++) {
			int len = BitConverter.getInt(buffer, index);
			index = index + 4;
			DataAccess.customdisplay[i] = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
		}
		return;

	}

	public static void ParseAlarmStatics(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		String[] name = new String[] { "致命", "严重", "一般", "通知" };
		DataAccess.alarmstaticsmap.clear();
		int index = PacketParse.headsize;
		for (int i = 0; i < 4; i++) {
			int value = BitConverter.getInt(buffer, index);
			index = index + 4;
			DataAccess.alarmstaticsmap.put(name[i], value);
		}
		return;
	}

	public static void ParsePowerDaily(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		DataAccess.poweroutputdaily.clear();
		int index = PacketParse.headsize;
		int count = BitConverter.getInt(buffer, index);
		index = index + 4;
		if (count == 0) {
			HistData histdata = new HistData();
			histdata.secondtime = 0;
			histdata.value = 0;
			DataAccess.poweroutputdaily.add(histdata);

		}
		for (int i = 0; i < count; i++) {
			HistData histdata = new HistData();
			histdata.secondtime = BitConverter.getInt(buffer, index);
			java.util.Date myDate = new java.util.Date();
			// myDate.setTime(histdata.secondtime*1000);
			long ltime = (long) histdata.secondtime * 1000;
			histdata.sampletime.setTime(ltime);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strdate = format.format(histdata.sampletime);

			index = index + 4;
			histdata.value = BitConverter.getDouble(buffer, index);
			index = index + 8;
			DataAccess.poweroutputdaily.add(histdata);
		}
		return;
	}

	public static void ParsePowerMonthy(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		DataAccess.poweroutputmonthycurves.clear();
		int index = PacketParse.headsize;
		HistDataCurve curve = new HistDataCurve();
		int histdatacount = BitConverter.getInt(buffer, index);
		index = index + 4;

		for (int j = 0; j < histdatacount; j++) {
			HistData histdata = new HistData();
			histdata.secondtime = BitConverter.getInt(buffer, index);
			index = index + 4;

			histdata.value = BitConverter.getDouble(buffer, index);
			index = index + 8;
			curve.poweroutputmonthy.add(histdata);
		}
		DataAccess.poweroutputmonthycurves.add(curve);
		return;

	}

	public static void parseEquipmentSwitchSignalCfg(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		int index = PacketParse.headsize + 4;
		DataAccess.equipmentswitchsignalsitems.clear();
		           
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		float fvalue2 = 0;
		int len2 = 0;
		String signalname2 = "";
		String strValue = "";
		for (int i = 0; i < totaldata; i++) {
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;

			len2 = BitConverter.getInt(buffer, index);
			index = index + 4;

			signalname2 = BitConverter.getUTF8String(buffer, index, len2);
			index = index + len2;

			len2 = BitConverter.getInt(buffer, index);
			index = index + 4;

			strValue = BitConverter.getUTF8String(buffer, index, len2);
			index = index + len2;
			// updateroomsdatabase(name);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Seq", i + 1);
			map.put("Id", id);
			map.put("Name", signalname2);
			map.put("Value", "--");

			DataAccess.equipmentswitchsignalsitems.add(map);

		}
	}

	public static void parseEquipmentAnalogSignalCfg(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		int index = PacketParse.headsize + 4;
		DataAccess.equipmentanalogsignalsitems.clear();
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		float fvalue1 = 0;
		int len1 = 0;
		String signalname1 = "";
		String unit1 = "";
		for (int i = 0; i < totaldata; i++) {
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;

			len1 = BitConverter.getInt(buffer, index);
			index = index + 4;

			signalname1 = BitConverter.getUTF8String(buffer, index, len1);
			index = index + len1;

			fvalue1 = BitConverter.getFloat(buffer, index);
			index = index + 4;

			len1 = BitConverter.getInt(buffer, index);
			index = index + 4;

			unit1 = BitConverter.getUTF8String(buffer, index, len1);
			index = index + len1;

			// updateroomsdatabase(name);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Seq", i + 1);
			map.put("Id", id);
			map.put("Name", signalname1);
			map.put("Value", "--");
			map.put("Unit", unit1);

			DataAccess.equipmentanalogsignalsitems.add(map);
		}

	}

	public static void parseDataConfig(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		int[] pos = new int[] { 19938496, 109692993 };// 临高
		List<int[]> poses = new ArrayList<int[]>();

		int[] pos1 = new int[] { 18780217, 109522705 };// 五指山
		int[] pos2 = new int[] { 18284493, 109565921 };// 海口
		int[] pos3 = new int[] { 19562378, 110806732 };// 三亚

		poses.add(pos1);
		poses.add(pos2);
		poses.add(pos3);

		int index = 15;
		int totaldata = 0;
		String name;
		ConfigType cfgtype = ConfigType.values()[BitConverter.getInt(buffer, index)];
		index = index + 4;
		switch (cfgtype) {
		case Stations:
			DataAccess.stationslisttitems.clear();
			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;

			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;
				int len = BitConverter.getInt(buffer, index);
				index = index + 4;

				name = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;
				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("seq", i);
				map.put("id", id);
				map.put("Name", name);
				// map.put("latitude", poses.get(i)[0]);
				// map.put("longitude",poses.get(i)[1]);
				DataAccess.stationslisttitems.add(map);

			}
			break;
		case EpvArrays:
			DataAccess.roomslisttitems.clear();
			// ClearTable("rooms");

			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;

			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				int len = BitConverter.getInt(buffer, index);
				index = index + 4;
				name = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;
				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("seq", i);
				map.put("id", id);
				map.put("Name", name);

				DataAccess.roomslisttitems.add(map);

			}
			break;
		case Equipments:
			// ClearTable("equipments");
			DataAccess.equipmentslisttitems.clear();
			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;

			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				int len = BitConverter.getInt(buffer, index);
				index = index + 4;
				name = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;
				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("seq", i);
				map.put("id", id);
				map.put("Name", name);

				DataAccess.equipmentslisttitems.add(map);

			}
			break;
		case Signals:
			// ClearTable("signals");
			DataAccess.signalslisttitems.clear();
			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;
			float fvalue = 0;
			int len = 0;
			String signalname = "";
			String unit = "";
			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				len = BitConverter.getInt(buffer, index);
				index = index + 4;

				signalname = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;

				fvalue = BitConverter.getFloat(buffer, index);
				index = index + 4;

				len = BitConverter.getInt(buffer, index);
				index = index + 4;

				unit = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;

				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Seq", i);
				map.put("Id", id);
				map.put("Name", signalname);
				map.put("Value", 0);
				map.put("Unit", unit);

				DataAccess.signalslisttitems.add(map);

			}
			break;
		case AnalogSignals:
			// ClearTable("signals");
			DataAccess.equipmentanalogsignalsitems.clear();
			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;
			float fvalue1 = 0;
			int len1 = 0;
			String signalname1 = "";
			String unit1 = "";
			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				len1 = BitConverter.getInt(buffer, index);
				index = index + 4;

				signalname1 = BitConverter.getUTF8String(buffer, index, len1);
				index = index + len1;

				fvalue1 = BitConverter.getFloat(buffer, index);
				index = index + 4;

				len1 = BitConverter.getInt(buffer, index);
				index = index + 4;

				unit1 = BitConverter.getUTF8String(buffer, index, len1);
				index = index + len1;

				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Seq", i);
				map.put("Id", id);
				map.put("Name", signalname1);
				map.put("Value", 0);
				map.put("Unit", unit1);

				DataAccess.equipmentanalogsignalsitems.add(map);

			}
			break;

		case SwitchSignals:
			// ClearTable("signals");
			DataAccess.equipmentswitchsignalsitems.clear();
			totaldata = BitConverter.getInt(buffer, index);
			index = index + 4;
			float fvalue2 = 0;
			int len2 = 0;
			String signalname2 = "";
			String strValue = "";
			for (int i = 0; i < totaldata; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				len2 = BitConverter.getInt(buffer, index);
				index = index + 4;

				signalname2 = BitConverter.getUTF8String(buffer, index, len2);
				index = index + len2;

				len2 = BitConverter.getInt(buffer, index);
				index = index + 4;

				strValue = BitConverter.getUTF8String(buffer, index, len2);
				index = index + len2;

				// updateroomsdatabase(name);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Seq", i);
				map.put("Id", id);
				map.put("Name", signalname2);
				map.put("Value", strValue);

				DataAccess.equipmentswitchsignalsitems.add(map);

			}
			break;
		}

	}

	/*
	 * public void Packet(ConfigType type) { switch (type) { case Stations: //
	 * PackStationsCfg(); break; case EpvArrays: break;
	 * 
	 * case Equipments: break;
	 * 
	 * case Signals: break; }
	 * 
	 * }
	 */
	public static void parseRealTimeSignals(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		// DataAccess.m_realSignals.clear();
		int index = PacketParse.headsize;
		int totaldata = 0;
		String name;
		totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		for (int i = 0; i < totaldata; i++) {
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;
			float value = BitConverter.getFloat(buffer, index);
			index = index + 4;
			for (int j = 0; j < DataAccess.signalslisttitems.size(); j++) {

				if ((Integer) (DataAccess.signalslisttitems.get(j).get("Id")) == id) {

					DataAccess.signalslisttitems.get(j).put("Value", value);

				}

			}
		}
		return;

	}

	public static void parseStationOverViewSignals(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		// DataAccess.m_realSignals.clear();
		int index = PacketParse.headsize;
		int stationid = 0;
		String strvalue;
		int type = BitConverter.getInt(buffer, index);
		index = index + 4;
		stationid = BitConverter.getInt(buffer, index);
		index = index + 4;
		for (int i = 0; i < 4; i++) {

			int id = BitConverter.getInt(buffer, index);
			index = index + 4;

			int len = BitConverter.getInt(buffer, index);
			index = index + 4;

			strvalue = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;

			DataAccess.stationoverview.get(i).put("Value", strvalue);
		}

	}

	public static void parseStationWeatherSignals(byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}

		int index = PacketParse.headsize;
		int totaldata = 0;
		String strvalue;
		totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		if (totaldata < 4) {

			for (int i = 0; i < 4; i++) {

				DataAccess.stationweather.get(i).put("Value", "--");
			}

		} else {
			for (int i = 0; i < 4; i++) {
				int id = BitConverter.getInt(buffer, index);
				index = index + 4;

				int len = BitConverter.getInt(buffer, index);
				index = index + 4;

				strvalue = BitConverter.getUTF8String(buffer, index, len);
				index = index + len;
				DataAccess.stationweather.get(i).put("Value", strvalue);
			}
		}

	}

	public static void ParseAliveEvents(byte[] buffer, int page, int pagesize) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		int startseq = page * pagesize;
		if (page == 0) {
			DataAccess.aliveevents.clear();
		}
		// ClearTable("rooms");

		int index = PacketParse.headsize;
		DataAccess.totalalarm = BitConverter.getInt(buffer, index);
		index = index + 4;
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		int temlength = 0;
		int level = 0;
		String tempstr = "";
		for (int i = 0; i < totaldata; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Seq", startseq + i + 1);
			// AliveEvents alarm= new AliveEvents();
			// alarm.m_id=BitConverter.getInt(buffer, index);
			// index =index +4;
			// 告警名称
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("Name", tempstr);

			// 告警位置
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("Position", tempstr);

			// 告警时间
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("StartTime", tempstr);

			// 告警等级
			level = BitConverter.getInt(buffer, index);
			index = index + 4;
			switch (level) {
			case 1:
				map.put("Level", R.drawable.eventseverity1);
				break;
			case 2:
				map.put("Level", R.drawable.eventseverity2);
				break;
			case 3:
				map.put("Level", R.drawable.eventseverity3);
				break;
			case 4:
				map.put("Level", R.drawable.eventseverity4);
				break;

			}

			DataAccess.aliveevents.add(map);
		}

	}

	public static void ParseReportSignals(int page, byte[] buffer) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		int index = PacketParse.headsize;
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		int temlength = 0;
		for (int i = 0; i < totaldata; i++) {

			AliveEvents alarm = new AliveEvents();
			alarm.m_id = BitConverter.getInt(buffer, index);
			index = index + 4;
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			alarm.m_roomname = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;

			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			alarm.m_equipment = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;

			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			alarm.m_name = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;

			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			alarm.m_meaning = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;

			alarm.m_starttime.setSeconds(BitConverter.getInt(buffer, index));
			index = index + 4;

		}
	}

	public static void parseStationData(byte[] buffer, Handler handler) {
		if (buffer.length < PacketParse.headsize) {
			return;
		}

		DataAccess.stations.clear();
		DataAccess.parents.clear();
		DataAccess.parentMap.clear();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buffer.length; i++) {
			sb.append(buffer[i] + ",");
		}
		// Log.e("parseStationData+我的数据：",sb.toString());

		int index = PacketParse.headsize;
		int totaldata = BitConverter.getInt(buffer, index);
		// Log.e("parseStationData+totaldata:",totaldata+"");

		index = index + 4;

		for (int i = 0; i < totaldata; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;
			int len = BitConverter.getInt(buffer, index);
			index = index + 4; 

			String name = BitConverter.getUTF8String(buffer, index, len);

			index = index + len;
			int type = BitConverter.getInt(buffer, index);
			index = index + 4;

			int parentId = BitConverter.getInt(buffer, index);
			index = index + 4;

			/*
			 * 定制 只显示想显示的数据
			 * 
			 * if(name.equals("四川资阳市雁江区机房2")) { map.put("seq", i); map.put("id", id);
			 * map.put("name", name); map.put("type", type); map.put("image",
			 * R.drawable.padroom); DataAccess.stations.add(map); }
			 * 
			 */

			// Log.e("parseStationData+parentId", parentId+"");
			// Log.e("parseStationData+id", id+"");

			map.put("seq", i);
			map.put("parentId", parentId);
			map.put("id", id);
			map.put("name", name);
			map.put("type", type);
			map.put("image", R.drawable.padroom);

			if (parentId == 0) {
				DataAccess.parents.add(map);

			} else {
				ArrayList<HashMap<String, Object>> listData = DataAccess.parentMap.get(parentId);

				if (listData == null) {
					listData = new ArrayList<>();
					DataAccess.parentMap.put(parentId, listData);
				}

				listData.add(map);
				// DataAccess.stations.add(map);

			}

		}

		handler.sendEmptyMessage(1);
	}

	public static void parseRoomsWithOverViewData(byte[] buffer, Handler handler) {
		if (buffer.length < PacketParse.headsize) {
			return;
		}

		DataAccess.roomslisttitems.clear();
		int index = PacketParse.headsize;
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;

		for (int i = 0; i < totaldata; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;
			int len = BitConverter.getInt(buffer, index);
			index = index + 4;

			String name = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			int type = BitConverter.getInt(buffer, index);
			index = index + 4;

			map.put("seq", i);
			map.put("id", id);
			map.put("name", name);
			map.put("type", type);
			map.put("image", R.drawable.padroom);

			DataAccess.roomslisttitems.add(map);
		}

		Log.e("哈哈", buffer.toString());
		handler.sendEmptyMessage(1);

	}

	public static void parseEquipmentsWithOverViewData(byte[] buffer, Handler handler) {
		if (buffer.length < PacketParse.headsize) {

			return;
		}
		DataAccess.equipmentslisttitems.clear();
		int index = PacketParse.headsize;
		int totaldata = 0;
		String strName, Data0, Data1, Data2, Data3;
		totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		for (int i = 0; i < totaldata; i++) {
			int type = BitConverter.getInt(buffer, index);
			index = index + 4;
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;

			int len = BitConverter.getInt(buffer, index);
			index = index + 4;
			strName = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			len = BitConverter.getInt(buffer, index);
			index = index + 4;
			Data0 = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			len = BitConverter.getInt(buffer, index);
			index = index + 4;
			Data1 = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			len = BitConverter.getInt(buffer, index);
			index = index + 4;
			Data2 = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			len = BitConverter.getInt(buffer, index);
			index = index + 4;
			Data3 = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("seq", i);
			map.put("id", id);
			map.put("name", strName);
			map.put("data0", Data0);
			map.put("data1", Data1);
			map.put("data2", Data2);
			map.put("data3", Data3);
			if (type == 0) {

				map.put("image", R.drawable.padequipment);

			} else if (type == 1) {
				map.put("image", R.drawable.box);

			} else if (type == 2) {
				map.put("image", R.drawable.weather);

			} else if (type == 3) {
				map.put("image", R.drawable.converter);

			} else {

				if (strName.contains("UPS")) {
					map.put("image", R.drawable.ups1);
				} else if (strName.contains("空调")) {
					map.put("image", R.drawable.aircon2);
				} else if (strName.contains("温湿度")) {
					map.put("image", R.drawable.wsd);
				} else if (strName.contains("漏水")) {
					map.put("image", R.drawable.shuijin3);
				} else if (strName.contains("烟感")) {
					map.put("image", R.drawable.smoke);
				} else if (strName.contains("电池")) {
					map.put("image", R.drawable.xdc);
				} else if (strName.contains("电表")) {
					map.put("image", R.drawable.meter);
				} else {

					map.put("image", R.drawable.device);

				}

			}

			DataAccess.equipmentslisttitems.add(map);
		}

		handler.sendEmptyMessage(1);
	}

	public static void parseRealTimeAnalogSignals(byte[] buffer,Handler handler) {
		
		
		
		
		if (buffer.length < PacketParse.headsize) {
			return;
		}

		
		
		
		int index = PacketParse.headsize;
		int totaldata = 0;
		String name;
		totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;

		for (int i = 0; i < totaldata; i++) {
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;
			float value = BitConverter.getFloat(buffer, index);
			index = index + 4;
			for (int j = 0; j < DataAccess.equipmentanalogsignalsitems.size(); j++) {
				if ((Integer) (DataAccess.equipmentanalogsignalsitems.get(j).get("Id")) == id) {
					DecimalFormat dec = new DecimalFormat("0.00");

					DataAccess.equipmentanalogsignalsitems.get(j).put("Value", dec.format(value));
				}
			}
		}
		
		
		
		handler.sendEmptyMessage(4);

		return;
	}

	public static void parseRealTimeSwitchSignals(byte[] buffer,Handler handler) {
		
	
		if (buffer.length < PacketParse.headsize) {
			return;
		}

		
		
		
		int index = PacketParse.headsize;
		int totaldata = 0;
		String strvalue;
		totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;

		for (int i = 0; i < totaldata; i++) {
			int id = BitConverter.getInt(buffer, index);
			index = index + 4;

			int len = BitConverter.getInt(buffer, index);
			index = index + 4;

			strvalue = BitConverter.getUTF8String(buffer, index, len);
			index = index + len;

			for (int j = 0; j < DataAccess.equipmentswitchsignalsitems.size(); j++) {
				if ((Integer) (DataAccess.equipmentswitchsignalsitems.get(j).get("Id")) == id) {
					DataAccess.equipmentswitchsignalsitems.get(j).put("Value", strvalue);
				}
			}
		}
		
		handler.sendEmptyMessage(5);

		return;
	}

	public static void ParseEquipmentAliveEvents(byte[] buffer,Handler handler) {

		if (buffer.length < PacketParse.headsize) {

			return;
		}
		DataAccess.equipmentalarmitems.clear();

		int index = PacketParse.headsize;
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;
		int temlength = 0;
		int level = 0;
		String tempstr = "";

		for (int i = 0; i < totaldata; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Seq", i + 1);

			// 告警名称
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("Name", tempstr);

			// 告警位置
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("Position", tempstr);

			// 告警时间
			temlength = BitConverter.getInt(buffer, index);
			index = index + 4;
			tempstr = BitConverter.getUTF8String(buffer, index, temlength);
			index = index + temlength;
			map.put("StartTime", tempstr);

			// 告警等级
			level = BitConverter.getInt(buffer, index);
			index = index + 4;
			switch (level) {
			case 1:
				map.put("Level", R.drawable.eventseverity1);
				break;
			case 2:
				map.put("Level", R.drawable.eventseverity2);
				break;
			case 3:
				map.put("Level", R.drawable.eventseverity3);
				break;
			case 4:
				map.put("Level", R.drawable.eventseverity4);
				break;
			}
			DataAccess.equipmentalarmitems.add(map);
		}

		handler.sendEmptyMessage(6);
		
	}

	public static void ParseEquipmentCommand(byte[] buffer,Handler handler) {

		if (buffer.length < PacketParse.headsize) {

			return;

		}

		DataAccess.equipmentcommanditems.clear();
		DataAccess.equipmentcommandsenddata.clear();

		byte[] dataBuffer = new byte[1024 * 4];

		int index = PacketParse.headsize;
		int totaldata = BitConverter.getInt(buffer, index);
		index = index + 4;

		System.arraycopy(buffer, index, dataBuffer, 0, totaldata);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(dataBuffer);
		String str = "";
		String JsonText = "";
		try {
			GZIPInputStream gZip = new GZIPInputStream(in);
			BufferedReader br = new BufferedReader(new InputStreamReader(gZip, "GB2312"));

			while ((str = br.readLine()) != null) {
				JsonText += str;
			}

			// System.out.println(JsonText);

			if (JsonText != null && !JsonText.equals("[]")) {
				JSONArray array = new JSONArray(JsonText);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					HashMap<String, Object> map = new HashMap<String, Object>();
					String Name = (String) jsonObject.get("Name");
					int ParameterValue = (Integer) jsonObject.get("ParameterValue");
					System.out.println(Name + "......" + ParameterValue);
					// if((Name.equalsIgnoreCase("s1")||Name.equalsIgnoreCase("s2")||Name.equalsIgnoreCase("s3")))
					// {
					map.put("Seq", i + 1);
					map.put("Name", jsonObject.get("Name") + "");
					map.put("Value", jsonObject.get("Meaing") + "");
					DataAccess.equipmentcommanditems.add(map);
					HashMap<String, Object> sendMap = new HashMap<String, Object>();
					sendMap.put("Id", jsonObject.get("Id") + "");
					sendMap.put("Meaing", jsonObject.get("Meaing") + "");
					sendMap.put("Name", jsonObject.get("Name") + "");
					sendMap.put("ParameterId", jsonObject.get("ParameterId") + "");
					sendMap.put("ParameterName", jsonObject.get("ParameterName") + "");
					sendMap.put("ParameterValue", jsonObject.get("ParameterValue") + "");
					sendMap.put("Type", jsonObject.get("Type") + "");
					sendMap.put("usrName", DataAccess.usrName);
					DataAccess.equipmentcommandsenddata.add(sendMap);
					// }
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		handler.sendEmptyMessage(7);
	}

}
