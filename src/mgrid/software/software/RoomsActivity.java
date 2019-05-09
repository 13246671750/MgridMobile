package mgrid.software.software;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import mgrid.software.software.entity.RoomEntity;

public class RoomsActivity extends Activity {

	public ArrayList<RoomEntity> rooms = new ArrayList<RoomEntity>();
	SQLiteDatabase sqldb;
	public String DB_NAME = "db.sqlite";
	public String DB_TABLE = "num";
	public int DB_VERSION = 1;
	public int nposition = 0;
	ListAdapter adapter;
	public int lastitem = 0;
	public int listindex = 10;
	public ProgressDialog m_dlg;
	public boolean bPause = false;
	public boolean bQuery = true;

	final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);

	private EditText etsearch;
	private Button btnsearch;
	private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();

	// DbHelper类在DbHelper.java文件里面创建的
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	
		handler.sendEmptyMessage(0);

		setContentView(R.layout.stationroomslayout);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
					.detectNetwork().detectAll().penaltyLog().build());
			StrictMode.setVmPolicy(
					new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
		}
		sqldb = helper.getWritableDatabase();
		lv = (ListView) findViewById(R.id.stationroomslv);

		// Iterator<HashMap<String, Object>> it = DataAccess.roomslisttitems.iterator();
		int i = 0;
		for (; i < DataAccess.roomslisttitems.size(); i++) {
			HashMap<String, Object> hmObj = DataAccess.roomslisttitems.get(i);
			Integer id = (Integer) hmObj.get("id");

			// assert
			if (null == id)
				continue;

		}

		adapter = new SimpleAdapter(this, listData, R.layout.pageforstationroomslistitem,
				new String[] { "image", "name" }, new int[] { R.id.room_ItemImage, R.id.room_list_Name });

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemListener);

		etsearch = (EditText) findViewById(R.id.etsearch);
		etsearch.setHint("输入关键字");
		btnsearch = (Button) findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String text = etsearch.getText().toString();
				listData.clear();
				if (text == null || text.equals("")) {

					setListData();
					lv.invalidateViews();
				} else {

					for (int j = 0; j < DataAccess.roomslisttitems.size(); j++) {
						HashMap<String, Object> data = DataAccess.roomslisttitems.get(j);
						String name = (String) data.get("name");
						if (name.contains(text)) {
							listData.add(data);
						}

					}
					lv.invalidateViews();
				}
				// System.out.println("数据源："+listData.size()+"...."+DataAccess.roomslisttitems.size());
			}
		});

		registerForContextMenu(lv);

		SetNavation(0);
	}

	private void setListData() {
		
		listData.clear();
		for (int j = 0; j < DataAccess.roomslisttitems.size(); j++) {
			HashMap<String, Object> data = DataAccess.roomslisttitems.get(j);
			listData.add(data);
		}
		
	}

	public void run() {

		while (!bPause) {
			for (int i = 0; i < 60; i++) {
				if (bPause) {
					return;
				}
				if (bQuery) {
					break;
				}
				try {
					Thread.sleep(1000);

				} catch (InterruptedException e) {
					// new Thread(RoomsActivity.this).start();
					e.printStackTrace();
				}

			}

			bQuery = false;
			handler.sendEmptyMessage(0);
		}
	}

	OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			try {

				// System.out.println("调到设备");
				// DataAccess.roomid=
				// (Integer)DataAccess.roomslisttitems.get(position).get("id");
				// DataAccess.roomname=
				// (String)DataAccess.roomslisttitems.get(position).get("name");
				DataAccess.roomid = (Integer) listData.get(position).get("id");
				DataAccess.roomname = (String) listData.get(position).get("name");

				Intent intent = new Intent(RoomsActivity.this, EquipmentsActivity.class);

				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				Window w = RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("实时信号", intent);
				View view1 = w.getDecorView();
				RealtimeActivityGroup.rtgroup.setContentView(view1);
				RealtimeActivityGroup.activityindex = 2;

			} catch (Exception e) {
			}
		}
	};

	@Override
	protected void onDestroy() {// 关闭数据库
		// TODO Auto-generated method stub
		super.onDestroy();
		if (helper != null) {
			helper.close();
		}
	}

	private Handler handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:

				LoginActivity.xianChengChi.execute(new Runnable() {

					@Override
					public void run() {

						DataAccess.GetRoomsWithOverViewData(DataAccess.stationid, handler);

					}
				});

				break;

			case 1:

				
				setListData();
				lv.setVisibility(View.GONE);
				((BaseAdapter) adapter).notifyDataSetChanged();
				lv.setVisibility(View.VISIBLE);

				Log.e("123", "更新");

				break;
			}

		}
	};

	public void SetNavation(int level) {
		TextView pos;
		switch (level) {
		case 0:
			pos = (TextView) findViewById(R.id.navigationstation);
			pos.setText(DataAccess.stationname);

			pos = (TextView) findViewById(R.id.navigationText1);
			pos.setText("");

			pos = (TextView) findViewById(R.id.navigationroom);
			pos.setText("");

			break;
		case 1:
			pos = (TextView) findViewById(R.id.navigationstation);
			pos.setText(DataAccess.stationname);

			pos = (TextView) findViewById(R.id.navigationText1);
			pos.setText(" >> ");

			pos = (TextView) findViewById(R.id.navigationroom);
			pos.setText(DataAccess.roomname);

			break;
		case 2:
			pos = (TextView) findViewById(R.id.navigationstation);
			pos.setText(DataAccess.stationname);

			pos = (TextView) findViewById(R.id.navigationText1);
			pos.setText(" >> ");

			pos = (TextView) findViewById(R.id.navigationroom);
			pos.setText(DataAccess.roomname);

			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		bPause = true;
	}

	@Override
	public void onResume() {
		super.onResume();
		SetNavation(0);
		RealtimeActivityGroup.activityindex = 1;
		bPause = false;
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);

					// 你要执行的方法

					// 执行完毕后给handler发送一个空消息
					handler.sendEmptyMessage(0);
				} catch (Exception e) {

				}
			}
		}.start();
	}
}
