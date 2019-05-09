package mgrid.software.software;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;


/**
 * 闂冨弶鏌婂ù顏勪簳閸楁艾绨抽柈銊ヤ紣閸忛攱鐖惃鍑綼bActivity閵嗕精ndroid瀵拷锟介崣鎴炲Η閺堫垯姘﹀ù浣哄參86686524濞嗐垼绻嬫径褍顔嶆禍銈嗙ウ鐎涳缚绡�
 * @author 妞嬬偤娲╅弮鐘冲剰
 * @since 2011-3-8
 */
public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{
	private RadioGroup mainTab;
	private TabHost mTabHost;
	public static String dbName="db.sqlite";//鏁版嵁搴撶殑鍚嶅瓧
	private static String DATABASE_PATH="/data/data/enpc.software.solarweb/databases/";//鏁版嵁搴撳湪鎵嬫満閲岀殑璺緞

    public static ProgressBar bar;
	
	   //     private GridView gridview; 

	      //   private ImageAdapter1 adapter; 
	//閸愬懎顔怚ntent
	private Intent mHomeIntent;
	private Intent mStationsIntent;
	private Intent mAlarmIntent;
	private Intent mSearchIntent;
	private Intent mSettingIntent;
	private Intent mRealTimeIntent;
	private Intent mRealTimeIntentgroup;
	private Intent mReportIntentgroup;
	
	private final static String TAB_TAG_HOME="tab_tag_home";
	private final static String TAB_TAG_REALTIME="tab_tag_news";
	private final static String TAB_TAG_ALARM="tab_tag_info";
	private final static String TAB_TAG_REPORT="tab_tag_search";
	private final static String TAB_TAG_SETTING="tab_tag_more";
    /** Called when the activity is first created. */
	 private Handler handler =new Handler()
   	 {
   	    @Override
   	   //褰撴湁娑堟伅鍙戦�佸嚭鏉ョ殑鏃跺�欏氨鎵цHandler鐨勮繖涓柟娉�
   	 	  public void handleMessage(Message msg){
   		  super.handleMessage(msg);
   		  switch((Integer)msg.obj)
   		  {
   		  case 0:
   	      Intent intent = new Intent(MainTabActivity.this,LoginActivity.class);  
   	       MainTabActivity.this.startActivity(intent);
   	       break;
   		  case 1:
   			  Toast.makeText(MainTabActivity.this, "缃戠粶閫氳涓柇",
 					Toast.LENGTH_SHORT).show();
   			  
   		  }
   		
   	    }
     };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
     
      requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintab);
        

     
        
        if (android.os.Build.VERSION.SDK_INT > 9) 
        { 
          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
          StrictMode.setThreadPolicy(policy);
          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().detectAll().penaltyLog().build());    
          StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build()); 
        } 
   //   ActionBar actionBar= this.getActionBar();
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        Init();
        prepareIntent();
        setupIntent();
     //   Intent intent=new Intent(MainTabActivity.this, SocketService.class);
      //  startService(intent);
    	DataAccess.activities.add(this);
    	 mTabHost.setOnTabChangedListener(new OnTabChangeListener() {     
    		 public void onTabChanged(String tabId) {  
    			   if(tabId==TAB_TAG_REALTIME)
    			   {
    				   RadioButton button= (RadioButton)findViewById(R.id.radio_button1);
    				   button.setChecked(true);
    				   
    			   }
    	            
    	            }  
    	        
    	    });  
    	 
    	   new Thread(){
	    		@Override
	    		public void run(){
	    			 	    			   
	    				while(!DataAccess.bExit)
	    				{
	    					
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						if(DataAccess.inLoginactivetiy)
						{
							
							continue;
						}
	    					
						if(DataAccess.nErrorCode==-4)
						{
							DataAccess.nErrorCode=0;
						Message msg=new Message();
						msg.obj=0;
	    				handler.sendMessage(msg);
	    				break;
						}
						if(DataAccess.nConnectedErrorNum>2)
						{
							Message msg=new Message();
							msg.obj=1;
							handler.sendMessage(msg);
							DataAccess.nConnectedErrorNum=3;
							
							
						}
		    			 
	    			}
	    		}
	    		}.start();
        
    }
    
    public void Init()
    {
    	//鏁版嵁搴撳垵濮嬪寲
           String DB_NAME = "db.sqlite";  
           int DB_VERSION = 1;  
           DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION); 
    	   DataAccess.sqldb = helper.getWritableDatabase();  
    	//瀹㈡埛绔疘d鍒濆鍖�   
    	//   DataAccess.clientid=-1;    	
    }
   

  
    @Override
    protected void onStart()
    {
    	
    	super.onStart();
    }
    /**
     * 閸戝棗顦竧ab閻ㄥ嫬鍞寸�圭ntent
     */
	private void prepareIntent() {
		//mHomeIntent=new Intent(this, MainActivityGroup.class);
	//	mStationsIntent=new Intent(this, StationsActivity.class);
		//mAlarmIntent=new Intent(this, AlarmViewPagerActivity.class);
		mAlarmIntent=new Intent(this, AlarmActivity.class);
	//	mSearchIntent=new Intent(this,ReportActivity.class);
		//mSettingIntent=new Intent(this, SettingActivity.class);
		mSettingIntent=new Intent(this, SettingActivity.class);
	//	mSettingIntent=new Intent(this, StationViewPagerActivity.class);
		mRealTimeIntent=new Intent(this, RealTimeActivity.class);
		mRealTimeIntentgroup=new Intent(this, RealtimeActivityGroup.class);
		mReportIntentgroup=new Intent(this, ReportActivityGroup.class);
	//	mReportIntentgroup=new Intent(this, ReportExpandableListActivity.class);
		

	}
	/**
	 * 
	 */
	private void setupIntent() {
		this.mTabHost=getTabHost();
		TabHost localTabHost=this.mTabHost;
		DataAccess.tabhost =this.mTabHost;
	//	localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.menu_home, R.drawable.icon_1_n, mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_REALTIME, R.string.menu_realtime, R.drawable.padrealtime, mRealTimeIntentgroup));
		localTabHost.addTab(buildTabSpec(TAB_TAG_ALARM, R.string.menu_alarm, R.drawable.padalarm, mAlarmIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_REPORT, R.string.menu_report, R.drawable.padrealtime, mReportIntentgroup));
		localTabHost.addTab(buildTabSpec(TAB_TAG_SETTING, R.string.menu_more, R.drawable.padsetting, mSettingIntent));
	}
	/**
	 * 閺嬪嫬缂揟abHost閻ㄥ嚲ab妞わ拷锟�
	 * 
	 * @param tag 閺嶅洩顔�
	 * @param resLabel 閺嶅洨顒�
	 * @param resIcon 閸ョ偓鐖�
	 * @param content 鐠囶殤ab鐏炴洜銇氶惃鍕敶鐎癸拷锟�
	 * @return 娑擄拷锟芥稉鐚糰b
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	} 
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
	//	case R.id.radio_button0:
	//		this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
	//		break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_REALTIME);
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ALARM);
			break;
		case R.id.radio_button3:
			//this.mTabHost.setCurrentTabByTag(TAB_TAG_REPORT);
			System.exit(0);
			break;
		case R.id.radio_button4:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_SETTING);
			break;
		}
	}
	/**
	 * 鍒ゆ柇鏁版嵁搴撴槸鍚﹀瓨鍦�
	 * @return false or true
	 */
	public boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			String databaseFilename = DATABASE_PATH+dbName;
			checkDB =SQLiteDatabase.openDatabase(databaseFilename, null,
					SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			
		}
		if(checkDB!=null){
			checkDB.close();
		}
		return checkDB !=null?true:false;
	}
	
	/**
	 * 澶嶅埗鏁版嵁搴撳埌鎵嬫満鎸囧畾鏂囦欢澶逛笅
	 * @throws IOException
	 */
	public void copyDataBase() throws IOException{
		String databaseFilenames =DATABASE_PATH+dbName;
		File dir = new File(DATABASE_PATH);
		if(!dir.exists())//鍒ゆ柇鏂囦欢澶规槸鍚﹀瓨鍦紝涓嶅瓨鍦ㄥ氨鏂板缓涓�涓�
			dir.mkdir();
		FileOutputStream os = null;
		try{
			os = new FileOutputStream(databaseFilenames);//寰楀埌鏁版嵁搴撴枃浠剁殑鍐欏叆娴�
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		InputStream is = MainTabActivity.this.getResources().openRawResource(R.raw.db);//寰楀埌鏁版嵁搴撴枃浠剁殑鏁版嵁娴�
	    byte[] buffer = new byte[8192];
	    int count = 0;
	    try{
	    	
	    	while((count=is.read(buffer))>0){
	    		os.write(buffer, 0, count);
	    		os.flush();
	    	}
	    }catch(IOException e){
	    	
	    }
	    try{
	    	is.close();
	    	os.close();
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
	}
}