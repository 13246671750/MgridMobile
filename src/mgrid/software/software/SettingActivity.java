package mgrid.software.software;

import java.lang.reflect.Method;

//	import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingActivity extends Activity {
	/** Called when the activity is first created. */
	SQLiteDatabase sqldb;
	public String DB_NAME = "db.sqlite";
	public String DB_TABLE = "num";
	public int DB_VERSION = 1;
	final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sqldb = helper.getWritableDatabase();
		SystemSetting.ServerIp = GetSettings("SERVERIP");
		SystemSetting.ServerPort = GetSettings("SERVERPORT");
		// sqldb.query("settings", columns, selection, "Key='IPADDRESS'", groupBy,
		// having, orderBy)
		setContentView(R.layout.settingslayout);
		// RadioGroup group= (RadioGroup)this.findViewById(R.id.radioButtonrunmodel);
		EditText ip = (EditText) findViewById(R.id.serveripeditText);
		ip.setText(SystemSetting.ServerIp);
		EditText port = (EditText) findViewById(R.id.serverporteditText);
		port.setText(SystemSetting.ServerPort);
		// group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		// public void onCheckedChanged(RadioGroup arg0,int arg1)
		// {
		// int radionButtonId=arg0.getCheckedRadioButtonId();
		// if(radionButtonId==R.id.radioButtonrun)
		// {
		// SystemSetting.RunModel=0;
		// }
		// if(radionButtonId==R.id.radioButtondemo)
		// {
		// SystemSetting.RunModel=1;
		// }

		// }
		// });
		View v2 = findViewById(R.id.ifrefreshbutton);

		v2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v2) {
				EditText ip = (EditText) findViewById(R.id.serveripeditText);

				SystemSetting.ServerIp = ip.getText().toString();
				UpdateKeyValue("SERVERIP", SystemSetting.ServerIp);

				EditText port = (EditText) findViewById(R.id.serverporteditText);

				SystemSetting.ServerPort = port.getText().toString();
				UpdateKeyValue("SERVERPORT", SystemSetting.ServerPort);
				SystemSetting.ipChanged = 1;
			}
		});

		// View v3=findViewById(R.id.logout);

		// v3.setOnClickListener(new View.OnClickListener() {

		// public void onClick(View v3) {
		// SendNotification();
		// return;
		/*
		 * AlertDialog.Builder builder = new Builder(SettingActivity.this); //
		 * builder.setIcon(R.drawable.imagebutton5); builder.setTitle("提示");
		 * builder.setMessage("你真的要退出MGRID手机监控系统吗？"); Builder setPositiveButton =
		 * builder.setPositiveButton("是", new
		 * android.content.DialogInterface.OnClickListener() {
		 * 
		 * 
		 * public void onClick(DialogInterface dialog, int which) { Intent i = new
		 * Intent(Intent.ACTION_MAIN);
		 * 
		 * i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * 
		 * i.addCategory(Intent.CATEGORY_HOME);
		 * 
		 * startActivity(i); } }); builder.setNegativeButton("否", new
		 * android.content.DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int which) { dialog.dismiss(); } });
		 * builder.create().show();
		 */
		// }
		// });
		// TextView tv= (TextView) findViewById(R.id.pageheadtitlecenter);
		// tv.setText("参数设置");

	}

	public void UpdateKeyValue(String key, String value) {

		String sqlstr = "UPDATE settings SET Value = " + "'" + value + "'" + "  WHERE Keys='" + key + "'";
		try {
			sqldb.execSQL(sqlstr);
		} catch (Exception e) {

			e.getMessage();
		}

	}

	public String GetSettings(String key) {
		String value = "";
		try {
			Cursor cr = sqldb.query("settings", new String[] { "Value" }, "Keys=?", new String[] { key }, null, null,
					null);
			cr.moveToFirst();
			value = cr.getString(0);
			cr.close();
		} catch (Exception e) {
			String str = e.getMessage();

		}
		return value;

	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new Builder(SettingActivity.this);
		builder.setTitle("MainTabActivity提示");
		builder.setMessage("你真的要退出手机监控系统吗？");
		Builder setPositiveButton = builder.setPositiveButton("是",
				new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Intent i = new Intent(Intent.ACTION_MAIN);

						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						// i.addCategory(Intent.CATEGORY_HOME);

						// startActivity(i);

						System.exit(0);
					}
				});
		builder.setNegativeButton("否", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();

	}

	public void SendNotification() {
		// 定义NotificationManager

		String ns = Context.NOTIFICATION_SERVICE;

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.eventseverity4;

		CharSequence tickerText = "MGRID手机监控系统 福永光伏电站故障通知";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		Context context = getApplicationContext();

		CharSequence contentTitle = "光伏逆变器故障";

		CharSequence contentText = "光伏逆变器2012-9-8 11:12:00 短路故障";

		Intent notificationIntent = new Intent(this, SettingActivity.class);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		// notification.setLatestEventInfo(context, contentTitle,
		// contentText,contentIntent);

		if (Build.VERSION.SDK_INT < 16) {

			Class clazz = notification.getClass();
			try {
				Method m2 = clazz.getDeclaredMethod("setLatestEventInfo", Context.class, CharSequence.class,
						CharSequence.class, PendingIntent.class);
				m2.invoke(context, contentTitle, contentText, contentIntent);
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {
			notification = new Notification.Builder(context).setAutoCancel(true).setContentTitle(contentTitle)
					.setContentText(contentText).setContentIntent(contentIntent).setSmallIcon(icon)
					.setWhen(System.currentTimeMillis()).build();
		}

		mNotificationManager.notify(1, notification);

	}

}