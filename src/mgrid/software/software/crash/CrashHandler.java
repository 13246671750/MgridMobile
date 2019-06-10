package mgrid.software.software.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import mgrid.software.software.LoginActivity;

public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance;
	private Context context;

	private CrashHandler() {
	};

	public synchronized static CrashHandler getInstance() {
		if (instance == null)
			instance = new CrashHandler();
		return instance;
	}

	public void init(Context context) {
		this.context = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}


	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {

		String str = parseEx(arg1);

		Message msg = new Message();
		msg.obj = str;
		msg.what = 1;

		LoginActivity.handlers.sendMessage(msg);

	}

	public void getMianEx() {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Looper.loop();
					} catch (Throwable e) {

						String str = parseEx(e);

						Message msg = new Message();
						msg.obj = str;
						msg.what = 1;

						LoginActivity.handlers.sendMessage(msg);

					}
				}
			}
		});
	}

	private String parseEx(Throwable e) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		Throwable cause = e.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		final String result = writer.toString();
		System.err.println("Òì³££º" + result);
		//save(result);

		return result;
	}

	private void save(String result) {

		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time = formatter.format(new Date());
			String fileName = "AndroidException.log";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/crash/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File f = new File(path + fileName);
				FileOutputStream fos = null;
				if (f.length() >= 1048576) {
					fos = new FileOutputStream(path + fileName);
				} else {
					fos = new FileOutputStream(path + fileName, true);
				}
				fos.write((time + "  " + result).getBytes());
				fos.close();
			}

		} catch (Exception e) {
			System.out.println("Ð´ÎÄ¼þÊ§°Ü");
		}

	}

}
