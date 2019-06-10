package mgrid.software.software.crash;


import android.app.Application;

public class MyApplication extends Application{

	
	private static MyApplication instance;

	public static MyApplication getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		
		instance=this;
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		crashHandler.getMianEx();		
		
		
	}
	
	
}
