package mgrid.software.software;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

 public class LoginActivity extends Activity {
	/*author: conowen
	 * date: 2012.4.2
	 * 
	 */
		SQLiteDatabase sqldb;  
		public String DB_NAME = "db.sqlite";  
		public String DB_TABLE = "num";  
		public int DB_VERSION = 1;  
		final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);  
		public static String dbName="db.sqlite";//���ݿ������
		private static String DATABASE_PATH="/data/data/mgrid.software.software/databases/";//���ݿ����ֻ����·��
        
		AutoCompleteTextView cardNumAuto;
		EditText passwordET;
		Button logBT;
		EditText serverip;
		EditText serverport;
		CheckBox savePasswordCB;
		SharedPreferences sp;
		String cardNumStr;
		String passwordStr;
		ProgressBar progressbar;
		

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		     DataAccess.Init();
			   boolean dbExist = checkDataBase();
		        if(dbExist){
		        	
		        }else{//�����ھͰ�raw������ݿ�д���ֻ�
		        	try{
		        		copyDataBase();
		        	}catch(IOException e){
		        		throw new Error("Error copying database");
		        	}
		        }
		    sqldb = helper.getWritableDatabase(); 
		    DataAccess.clientid=-1;
		    SystemSetting.ServerIp=GetSettings("SERVERIP");
		    //SystemSetting.ServerIp = "220.231.192.87";

		    SystemSetting.ServerPort=GetSettings("SERVERPORT");
			setContentView(R.layout.loginlayout);
			if (android.os.Build.VERSION.SDK_INT > 9) 
	        { 
	          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
	          StrictMode.setThreadPolicy(policy);
	          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().detectAll().penaltyLog().build());    
	          StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build()); 
	        } 
			
			progressbar = (ProgressBar) findViewById(R.id.progressBar1);
			progressbar.setVisibility(View.INVISIBLE);
			cardNumAuto = (AutoCompleteTextView) findViewById(R.id.cardNumAuto);
			passwordET = (EditText) findViewById(R.id.passwordET);
			String pwd=GetSettings("PWD");
			if(pwd.length() > 0)
			{
				passwordET.setText(pwd);
				
			}
			logBT = (Button) findViewById(R.id.logBT);
			serverip=(EditText)findViewById(R.id.login_serverip);
			if(SystemSetting.ServerIp.length() > 0)
			{
				serverip.setText(SystemSetting.ServerIp);
			}

			serverport=(EditText)findViewById(R.id.login_serverport);
			if(SystemSetting.ServerPort.length() > 0)
			{
				serverport.setText(SystemSetting.ServerPort);
			}
			
			sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
			savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
			savePasswordCB.setChecked(true);// Ĭ��Ϊ��ס����
			cardNumAuto.setThreshold(1);// ����1����ĸ�Ϳ�ʼ�Զ���ʾ
			passwordET.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			// ��������ΪInputType.TYPE_TEXT_VARIATION_PASSWORD��Ҳ����0x81
			// ��ʾ����ΪInputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD��Ҳ����0x91
	 
			cardNumAuto.addTextChangedListener(new TextWatcher() {


				public void onTextChanged(CharSequence s, int start, int before,
						int count) {
					// TODO Auto-generated method stub
					String[] allUserName = new String[sp.getAll().size()];// sp.getAll().size()���ص����ж��ٸ���ֵ��
					allUserName = sp.getAll().keySet().toArray(new String[0]);
					// sp.getAll()����һ��hash map
					// keySet()�õ�����a set of the keys.
					// hash map����key-value��ɵ�

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							LoginActivity.this,
							android.R.layout.simple_dropdown_item_1line,
							allUserName);

					cardNumAuto.setAdapter(adapter);
					// ��������������

				}

				 
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub

				}

			 
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					passwordET.setText(sp.getString(cardNumAuto.getText()
							.toString(), ""));// �Զ���������

				}
			});

			// ��½
			logBT.setOnClickListener(new OnClickListener() {

				 
				public void onClick(View v) {
					progressbar.setVisibility(View.VISIBLE);

					SystemSetting.ServerIp= serverip.getText().toString();
					UpdateKeyValue("SERVERIP",SystemSetting.ServerIp);
	//				String Ip=GetSettings("SERVERIP");
					
					SystemSetting.ServerPort= serverport.getText().toString();
					UpdateKeyValue("SERVERPORT",SystemSetting.ServerPort);
					String port=GetSettings("SERVERPORT");
					
					if(port.length() <= 0 && SystemSetting.ServerPort.length() > 0)
					{
						InsertKeyValue("SERVERPORT",SystemSetting.ServerPort);
						port=GetSettings("SERVERPORT");
					}
					cardNumStr = cardNumAuto.getText().toString();
					passwordStr = passwordET.getText().toString();
					
				//	showprogressbar();

						if (savePasswordCB.isChecked()) {// ��½�ɹ��ű�������
							sp.edit().putString(cardNumStr, passwordStr).commit();
							UpdateKeyValue("PWD",passwordStr);
							String pwd=GetSettings("PWD");
							if(pwd.length() <= 0)
							{
								InsertKeyValue("PWD",passwordStr);
							}
						}
					 	  new Thread(){
						    	 @Override
						    	 public void run(){
						    		try{		
						    		
						    		  int longinstatus=1;
						    	      int rtn=DataAccess.GetClientId(cardNumStr,passwordStr);
					    	    		
					    	    	  if( rtn==-2)
					    	    	  {
					    	    		  //�û��������ܴ���
					    	    		  int longinstatus3=3;						    	    
					    	    		  Message msg3=Message.obtain();
					    	    		  msg3.obj=longinstatus3;
					    	    		  handler.sendMessage(msg3);
					    	    		  return;
					    	    	  }
					    	    	  if(rtn<0)
					    	    	  {
					    	    		  //����ԭ���µ�½ʧ��
					    	    		  longinstatus=0;
					    	    		  Message msg1=Message.obtain();
					    	    		  msg1.obj=longinstatus;
					    	    		  handler.sendMessage(msg1);
					    	    		  return;
					    	    	  }
					    	    	   
					    	    	  longinstatus=1;					    	    
						    	      Message msg=Message.obtain();
						    	      msg.obj=longinstatus;
						    	      handler.sendMessage(msg);
						    	      
					    	    	  Thread.sleep(500);
				    	    	   	  DataAccess.GetStations(rtn);

				    	    	   	  //��½�ɹ�
				    	    	   	  int longinstatus1=2;						    	    
				    	    	   	  Message msg1=Message.obtain();
				    	    	   	  msg1.obj=longinstatus1;
				    	    	   	  handler.sendMessage(msg1);
									    
						    		}
						    		catch(Exception e)
						    		{					    			
						    		  int longinstatus=0;
						    		  Message msg=Message.obtain();
						    	      msg.obj=longinstatus;
						    	      handler.sendMessage(msg);
						    		}
						    	 //ִ����Ϻ��handler����һ������Ϣ
				    	 }
						  }.start();
					}
			});

		}
	 	 private Handler handler =new Handler()
	   	 {
	   	    @Override
	   	   //������Ϣ���ͳ�����ʱ���ִ��Handler���������
	   	 	  public void handleMessage(Message msg){
	   		  super.handleMessage(msg);
	   	      switch((Integer)msg.obj)
	   		   {
	  
	   		   case 0:
	   			Toast.makeText(LoginActivity.this, "��¼ʧ��",
	   					Toast.LENGTH_SHORT).show();
	   			progressbar.setVisibility(View.INVISIBLE);
	  		  
	   			break;
	 		   case 2:
	 			progressbar.setVisibility(View.INVISIBLE);
		
		   		  Intent intent = new Intent();
		   		  intent.setClass(LoginActivity.this, MainTabActivity.class);

		   		  startActivity(intent);
		   		  finish();
		   		  
		   		  break;
	 		   case 1:
	 				progressbar.setVisibility(View.VISIBLE);
	 			   
		   	    	Toast.makeText(LoginActivity.this, "���ڻ�ȡ ����,���Ժ󡭡�����",
		   	    			
					Toast.LENGTH_SHORT).show();		
		  
		   		  break;
	 		  case 3:
		   			Toast.makeText(LoginActivity.this, "�û����������",
		   					Toast.LENGTH_SHORT).show();
		   			progressbar.setVisibility(View.INVISIBLE);
		  		  
		   			break;	   			   
	   		   }	   		
	   	    }
	      };
	      
	      public void InsertKeyValue(String key,String value)
		   {
				try
				{
					String sqlstr = "INSERT INTO settings(Keys, Value) VALUES('" + key + "','"+ value +"')";
					sqldb.execSQL(sqlstr);
				}
				catch(Exception e)
				{
					e.getMessage();
				}
		   }
	      
		   public void UpdateKeyValue(String key,String value)
		   {
				String sqlstr="UPDATE settings SET Value = "+"'" +value+"'" +"  WHERE Keys='"+key+"'";
				try
				{
					sqldb.execSQL(sqlstr);
				}
				catch(Exception e)
				{
					InsertKeyValue(key,value);
				}
		   }
		    
		    public String GetSettings(String key)
		    {
		    	String value= "";	    	
		    	try
		    	{	    	
		    	  Cursor cr =sqldb.query("settings", new String[]{"Value"},"Keys=?", new String[]{key}, null, null,null);    	  
		    	 cr.moveToFirst();
		    	 value=cr.getString(0);
		    	 cr.close();
		    	}
		    	catch (Exception e)
		    	{
		//    		String str=e.getMessage();
		    		
		    	}
		    	 return value;
		    	
		    }
			/**
			 * �ж����ݿ��Ƿ����
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
			public void copyDataBase() throws IOException{
				String databaseFilenames =DATABASE_PATH+dbName;
				File dir = new File(DATABASE_PATH);
				if(!dir.exists())//�ж��ļ����Ƿ���ڣ������ھ��½�һ��
					dir.mkdir();
				FileOutputStream os = null;
				try{
					os = new FileOutputStream(databaseFilenames);//�õ����ݿ��ļ���д����
				}catch(FileNotFoundException e){
					e.printStackTrace();
				}
				InputStream is = LoginActivity.this.getResources().openRawResource(R.raw.db);//�õ����ݿ��ļ���������
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
			@SuppressWarnings("static-access")
			public void showprogressbar()
			{
				 AlertDialog AlterD2 = new AlertDialog.Builder(LoginActivity.this).create();  
 				//������ʾ�Ի��� 
				  LayoutInflater layoutInflater; 
				//���岼�ֹ����� 
				  LinearLayout myLayout; 
				//���岼�� 
				layoutInflater=(LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE); 
				//���ϵͳ�Ĳ��ֹ��˷��� 
				myLayout=(LinearLayout) layoutInflater.inflate(R.layout.progressbar1, null); 
				//�õ�������ƺõĲ��� 
				AlterD2.setTitle(getResources().getString(R.string.app_name)); 
				
				//���öԻ������ 
				AlterD2.setIcon(R.drawable.logo); 
				//���öԻ���ͼ��
				AlterD2.setMessage(getResources().getString(R.string.loginwait)); 
				//���öԻ�����ʾ��Ϣ 
				AlterD2.setView(myLayout); 
		 
				//���öԻ����е�View 
				AlterD2.show(); 

				
				
				
			}
			 @Override  
			    public void onResume()
			    {
			    	super.onResume();
			    	DataAccess.inLoginactivetiy=true;
			    }
			 @Override
			    protected void onPause()
			    {
			    	super.onPause();
			    	DataAccess.inLoginactivetiy=false;
			    	
			    }
			 @Override     
			 protected void onDestroy()
			 {         
				 super.onDestroy();   
				 DataAccess.bExit=true;
				 }  
			 

	}
