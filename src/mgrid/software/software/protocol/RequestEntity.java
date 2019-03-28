package mgrid.software.software.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import mgrid.software.software.BitConverter;
import mgrid.software.software.DataAccess;
import mgrid.software.software.MessageType;

public class RequestEntity {

	public static byte[] getBytes(MessageType msgtype, ConfigType submsgtype,
			int clientid) {
		Byte head = 0x7e;
		int checksum = 0;
		byte[] buffer = new byte[1024];

		if (msgtype == MessageType.RegisterRequest) {
			int index = 0;
			String mobiletype = android.os.Build.MODEL;
			byte[] tempbyte = BitConverter.getBytes(mobiletype, "UTF-8");

			// head
			buffer[index] = head;
			index = index + 1;
			// length
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// clientid
			System.arraycopy(BitConverter.getBytes(-1), 0, buffer, index, 4);
			index = index + 4;
			// checksum
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
			index = index + 2;

			// messagetyype
			int messagetype = MessageType.RegisterRequest.ordinal();
			System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer,
					index, 4);
			index = index + 4;

			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;

			// body
			System.arraycopy(BitConverter.getBytes(tempbyte.length), 0, buffer,
					index, 4);
			index = index + 4;

			System.arraycopy(tempbyte, 0, buffer, index, tempbyte.length);
			index = index + tempbyte.length;

			byte[] rtnbuffer = new byte[index];
			System.arraycopy(buffer, 0, rtnbuffer, 0, index);
			return rtnbuffer;

		} else if (msgtype == MessageType.ConfigRequest) {
			int index = 0;
			// head
			buffer[index] = head;
			index = index + 1;
			// length
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// clientid
			System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index,
					4);
			index = index + 4;
			// checksum
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
			index = index + 2;

			// messagetyype
			int messagetype = MessageType.RegisterRequest.ordinal();
			System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer,
					index, 4);
			index = index + 4;

			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;

			// SubMessageType

			System.arraycopy(BitConverter.getBytes(4), 0, buffer, index, 4);
			index = index + 4;

			int subtype = submsgtype.ordinal();
			System.arraycopy(BitConverter.getBytes(subtype), 0, buffer, index,
					4);
			index = index + 4;
			byte[] rtnbuffer = new byte[index];
			System.arraycopy(buffer, 0, rtnbuffer, 0, index);
			return rtnbuffer;
		} else {
			int index = 0;
			// head
			buffer[index] = head;
			index = index + 1;
			// length
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// clientid
			System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index,
					4);
			index = index + 4;
			// checksum
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
			index = index + 2;

			// messagetyype
			int messagetype = MessageType.RegisterRequest.ordinal();
			System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer,
					index, 4);
			index = index + 4;

			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;
			// 保留
			System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
			index = index + 4;

			byte[] rtnbuffer = new byte[index];
			System.arraycopy(buffer, 0, rtnbuffer, 0, index);
			return rtnbuffer;

		}

	}

	public static byte[] GetClientId(String username, String password) {
		Byte head = 0x7e;
		int checksum = 0;
		byte[] buffer = new byte[1024];

		int index = 0;
		String mobiletype = android.os.Build.MODEL;
		byte[] tempbyte = BitConverter.getBytes(mobiletype, "UTF-8");
		byte[] usernamebyte = BitConverter.getBytes(username, "UTF-8");
		byte[] passwordbyte = BitConverter.getBytes(password, "UTF-8");

		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(
				BitConverter.getBytes(tempbyte.length + 12
						+ usernamebyte.length + passwordbyte.length), 0,
				buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(-1), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;
		// messagetyype
		int messagetype = MessageType.RegisterRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;
		// 保留字节
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留字节
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// body
		System.arraycopy(BitConverter.getBytes(tempbyte.length), 0, buffer,
				index, 4);
		index = index + 4;

		System.arraycopy(tempbyte, 0, buffer, index, tempbyte.length);
		index = index + tempbyte.length;

		System.arraycopy(BitConverter.getBytes(usernamebyte.length), 0, buffer,
				index, 4);
		index = index + 4;

		System.arraycopy(usernamebyte, 0, buffer, index, usernamebyte.length);
		index = index + usernamebyte.length;

		System.arraycopy(BitConverter.getBytes(passwordbyte.length), 0, buffer,
				index, 4);
		index = index + 4;

		System.arraycopy(passwordbyte, 0, buffer, index, passwordbyte.length);
		index = index + passwordbyte.length;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	public static byte[] getRoomsWithOverViewDataBytes(int clientid,
			int stationid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(4), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.RoomsOverViewData.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;
	}

	public static byte[] getStationOverViewDataBytes(int clientid, int stationid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(4), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.StationOverView.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	public static byte[] getStationWeatherBytes(int clientid, int stationid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(4), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.StationWeather.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	public static byte[] getEquipmentsWithOverViewDataBytes(int clientid,
			int stationid, int roomid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(8), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.EquipmentOverViewData.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;
		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < rtnbuffer.length; i++) {
			sb.append(rtnbuffer[i]);
		}
		//System.out.println("请求数据："+sb.toString());
		//Log.e("请求数据", sb.toString());
		return rtnbuffer;

	}

	public static byte[] GetEquipmentAnalogSignalsCfgBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];
		int length = 16;

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(16), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.ConfigRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		// messagetyype
		int subtype = ConfigType.AnalogSignals.ordinal();
		System.arraycopy(BitConverter.getBytes(subtype), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	public static byte[] GetEquipmentSwitchSignalsCfgBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(16), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.ConfigRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		// messagetyype
		int subtype = ConfigType.SwitchSignals.ordinal();
		System.arraycopy(BitConverter.getBytes(subtype), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	public static byte[] getAliveEquipmentAlarmsBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];
		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(12), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.EquipmentAlarmRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
	
	
	public static byte[] getEquipmentCommandBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;


		byte[] buffer = new byte[1024];
		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(12), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.CommandRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		
		
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		

		
		
		
		return rtnbuffer;

	}
	
	public static byte[] getEquipmentCommandControlBytes(int clientid,
			int stationid, int roomid, int equipmentid,int arg) {
		Byte head = 0x7e;


		byte[] buffer = new byte[1024];
		
		//对象转json
		String jsonStr=toJSONStr(arg);
		
	    //压缩
		byte[] dataBuffer=decGzip(jsonStr);
		
		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		int length=16+dataBuffer.length;
		
		System.arraycopy(BitConverter.getBytes(length), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.SetCommandRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;
	
		
		
	
		
	
	
		//正文长度
		System.arraycopy(BitConverter.getBytes(dataBuffer.length), 0, buffer, index,
					4);
		index = index + 4;
		//正文
		System.arraycopy(dataBuffer, 0, buffer, index,
				dataBuffer.length);

		
		index=index+dataBuffer.length;
		
		byte[] rtnbuffer = new byte[index];
		
		
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		
		//System.out.println("总长度"+rtnbuffer.length);
		
		return rtnbuffer;
	}
	
	public static String toJSONStr(int arg)
	{
		JSONArray array=new JSONArray();
		JSONObject jsonObject=null;
		byte[] dataBuffer =null;
        HashMap<String, Object> hashMap=DataAccess.equipmentcommandsenddata.get(arg);
		jsonObject=new JSONObject();
			try {
				jsonObject.put("Id", hashMap.get("Id"));
				jsonObject.put("Meaing", hashMap.get("Meaing"));
				jsonObject.put("Name", hashMap.get("Name"));
				jsonObject.put("ParameterId", hashMap.get("ParameterId"));
				jsonObject.put("ParameterName", hashMap.get("ParameterName"));
				jsonObject.put("ParameterValue", hashMap.get("ParameterValue"));
				jsonObject.put("Type", hashMap.get("Type"));
				jsonObject.put("usrName", hashMap.get("usrName"));
				array.put(jsonObject);
				String jsonStr=array.toString();
				return jsonStr;			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
	}
	
	public static byte[] decGzip(String jsonStr)
	{
		
		try {
			ByteArrayOutputStream out =new ByteArrayOutputStream();
			GZIPOutputStream gZip=new GZIPOutputStream(out);
			gZip.write(jsonStr.getBytes("GB2312"));
			gZip.close();
			byte[] dataBuffer=out.toByteArray();	
			return dataBuffer;
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return null;
		
		
	}


	public static byte[] getAliveEquipmentAnalogSignalsBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;
	

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(12), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.AnalogRealSignalRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;
	}

	public static byte[] getAliveEquipmentSwitchSignalsBytes(int clientid,
			int stationid, int roomid, int equipmentid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];
		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(12), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.SwitchRealSignalRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index,
				4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}

	

	public static byte[] GetAlarmStaticsBytes(int clientid,int stationid,int year) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(8), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.AlarmStatics.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(year), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
	public static byte[] GetPowerOutputDailyBytes(int clientid,int stationid,int year) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(8), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.PowerDaily.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		
		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;
		
		System.arraycopy(BitConverter.getBytes(year), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
	public static byte[] GetPowerOutputMonthyBytes(int clientid,int year,int stationid,int roomid,int equipmentid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(16), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.PowerMonthy.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(year), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(roomid), 0, buffer, index, 4);
		index = index + 4;
		System.arraycopy(BitConverter.getBytes(equipmentid), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
	
	
	public static byte[] GetStations(int clientid) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.GetStations.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
	
	
	public static byte[] getAliveAlarmsBytes(int clientid, int stationid,
			int level, int page, int pagesize) {
		Byte head = 0x7e;
		int checksum = 0;

		byte[] buffer = new byte[1024];

		int index = 0;
		// head
		buffer[index] = head;
		index = index + 1;
		// length
		System.arraycopy(BitConverter.getBytes(16), 0, buffer, index, 4);
		index = index + 4;
		// clientid
		System.arraycopy(BitConverter.getBytes(clientid), 0, buffer, index, 4);
		index = index + 4;
		// checksum
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 2);
		index = index + 2;

		// messagetyype
		int messagetype = MessageType.AlarmRequest.ordinal();
		System.arraycopy(BitConverter.getBytes(messagetype), 0, buffer, index,
				4);
		index = index + 4;

		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;
		// 保留
		System.arraycopy(BitConverter.getBytes(0), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(stationid), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(level), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(page), 0, buffer, index, 4);
		index = index + 4;

		System.arraycopy(BitConverter.getBytes(pagesize), 0, buffer, index, 4);
		index = index + 4;

		byte[] rtnbuffer = new byte[index];
		System.arraycopy(buffer, 0, rtnbuffer, 0, index);
		return rtnbuffer;

	}
}
