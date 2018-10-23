package mgrid.software.software;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mgrid.software.software.R;

import mgrid.software.software.protocol.ConfigType;

import android.R.bool;
import android.app.Activity;  
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;  
import android.widget.ListView;  
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
	  
	public class RealTimeActivity extends Activity implements Runnable{  
		private Handler msgHandler;
		ListAdapter adapteranalog;
		ListAdapter adapterswitch;
		
		ListAdapter adapteralarm;
		
	    SQLiteDatabase sqldb;  
	    public String DB_NAME = "db.sqlite";  
	    public String DB_TABLE = "num";  
	    public int DB_VERSION = 1;  
	    public int nposition=0;
	    public boolean bPause= true;
	    final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);  
	    public int pageindex=0;
	    public boolean bQuery=true;
	    // DbHelper类在DbHelper.java文件里面创建的  
	    ListView lvanalog;  
	    ListView lvswitch;
	    ListView lvalarm;
	    @Override
	    public boolean dispatchKeyEvent(KeyEvent event) {
	                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
	                	
	                	
	                   Intent intent;
	  		    	   Window w;
	  		    	   View view1;
	                   	intent = new Intent(this, EquipmentsActivity.class);  
			    		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
						w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
						   view1=w.getDecorView();
						RealtimeActivityGroup.rtgroup.setContentView(view1);   
						RealtimeActivityGroup.activityindex=3;
				          return true;
	                }
	      
	                return super.dispatchKeyEvent(event);
	       } 
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        // TODO Auto-generated method stub  
	        super.onCreate(savedInstanceState);  
	        try{
	            setContentView(R.layout.signalslayout);  
	               findViewById(R.id.signals_layout_analog).setVisibility(0);
		           findViewById(R.id.signals_layout_alarms).setVisibility(4);

		           findViewById(R.id.signals_layout_switch).setVisibility(4);
			
	              ( (RadioGroup)findViewById(R.id.signaltab_radio_group)).setOnCheckedChangeListener(new OnCheckedChangeListener()
	              {
	            	  
	            		public void onCheckedChanged(RadioGroup group, int checkedId) {
	            			switch(checkedId){
	            			case R.id.signalshosttab_radio_button0:
	            		           findViewById(R.id.signals_layout_analog).setVisibility(0);
	            		           findViewById(R.id.signals_layout_switch).setVisibility(4);
	            		           findViewById(R.id.signals_layout_alarms).setVisibility(4);
	            		           pageindex=0;
	            		           bQuery=true;
	            				break;
	            			case R.id.signalshosttab_radio_button1:
	            		         findViewById(R.id.signals_layout_analog).setVisibility(4);
	            		         findViewById(R.id.signals_layout_switch).setVisibility(0);      	
	            		         findViewById(R.id.signals_layout_alarms).setVisibility(4);
	            		         pageindex=1;
	             		           bQuery=true;
	             		 	     
	            		         break;
	            			case R.id.signalshosttab_radio_button2:
	            		         findViewById(R.id.signals_layout_analog).setVisibility(4);
	            		           findViewById(R.id.signals_layout_switch).setVisibility(4);
	            		         findViewById(R.id.signals_layout_alarms).setVisibility(0);
	            		         pageindex=2;
	             		           bQuery=true;
	             		 	     
	            				break;		
	            			}
	            		}
	            	  
	              });;
	           
	     	   String[] ColumnNames = {"Seq","Name","Value","Unit"};    
	     	    adapteranalog = new SimpleAdapter(this, DataAccess.uiequipmentanalogsignalsitems, 
		                    R.layout.signalslistviewitem,  ColumnNames, new int[] { R.id.signals_list_id,   R.id.signals_list_Name, R.id.signals_list_value, R.id.signals_list_unit});  
	     	   lvanalog = (ListView) findViewById(R.id.equipment_analog_signallv);  
	     	  lvanalog.setAdapter(adapteranalog);  
			     
	     	     String[] ColumnNames1 = {"Seq","Name","Value"};    
	     	    adapterswitch = new SimpleAdapter(this, DataAccess.uiequipmentswitchsignalsitems, 
		                    R.layout.equipmentswitchsignalitem,  ColumnNames1, new int[] { R.id.signals_list_id,   R.id.signals_list_Name, R.id.signals_list_value});  
	     	   lvswitch = (ListView) findViewById(R.id.equipment_switch_signallv);  
	     	  lvswitch.setAdapter(adapterswitch);  

			     
	 	     String[] ColumnNames2 = {"Seq","Name","StartTime","Level"};    
	 	    
			     adapteralarm = new SimpleAdapter(this, DataAccess.uiequipmentalarmitems, 
		                    R.layout.equipmentalarmsignalitem,  ColumnNames2, new int[] { R.id.equipment_active_alarm_id,   R.id.equipment_active_alarm_descript,  R.id.equipment_active_alarm_starttime, R.id.equipment_active_alarm_level});
			       lvalarm = (ListView) findViewById(R.id.equipment_alarm_signallv);  
		        

			     lvalarm.setAdapter(adapteralarm);  
			 	DataAccess.activities.add(this);
			     
	        }
	        catch(Exception e)
	        {
	        	
	        	e.getMessage();
	        }

	
	     //   sqldb = helper.getWritableDatabase();  
		    	
	        View v=findViewById(R.id.navigationroom);
		    
	        v.setOnClickListener(naigationclick);
	        
	        View v1=findViewById(R.id.navigationstation);
		    
	        v1.setOnClickListener(naigationclick);
	    }  

	    View.OnClickListener naigationclick= new  View.OnClickListener()
	    {
	    	public void onClick(View v) 
			{
	    		switch(v.getId())
	    		{
	    		
	    		case R.id.navigationroom:
	    		{
		    	    Window w;
 		    	    View view1;
 		    	   Intent intent;
                  	intent = new Intent(RealTimeActivity.this, EquipmentsActivity.class);  
		    		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				   	w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
					view1=w.getDecorView();
					RealtimeActivityGroup.rtgroup.setContentView(view1);   
					RealtimeActivityGroup.activityindex=3;	
	    		}
					break;
	    		case R.id.navigationstation:
	    		{
		    	    Window w;
 		    	    View view1;
 		    	   Intent intent;
                  	intent = new Intent(RealTimeActivity.this, RoomsActivity.class);  
		    		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				   	w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
					view1=w.getDecorView();
					RealtimeActivityGroup.rtgroup.setContentView(view1);   
					RealtimeActivityGroup.activityindex=2;	
	    		}
					break;
	    		}
								
				}
	    	
	    };
	 private Handler handler =new Handler()
   	 {
   	    @Override
   	   //当有消息发送出来的时候就执行Handler的这个方法
   	 	  public void handleMessage(Message msg){  	    	
   		  super.handleMessage(msg);
   		  switch(pageindex)
   		  {
   		  case 0:
   			DataAccess.uiequipmentanalogsignalsitems.clear();
   	   		
   	 	lvalarm.setVisibility(View.GONE);	
		   ((BaseAdapter) adapteranalog).notifyDataSetChanged();			
		   lvalarm.setVisibility(View.VISIBLE);
   			  break;
   		  case 1:
   			DataAccess.uiequipmentswitchsignalsitems.clear();
   	   		
   	 	lvalarm.setVisibility(View.GONE);		
		   ((BaseAdapter) adapterswitch).notifyDataSetChanged();		
		   lvalarm.setVisibility(View.VISIBLE);
   			  break;
   		  case 2:
   			DataAccess.uiequipmentalarmitems.clear();
   	   		
   			lvalarm.setVisibility(View.GONE);
		   ((BaseAdapter) adapteralarm).notifyDataSetChanged();	
		   lvalarm.setVisibility(View.VISIBLE);
   			  break;
   		  
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
	    public void SetNavation(int level)
	     {
	    	 TextView pos;
	    	 switch(level)
	    	 { 
	    	 case 0:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText("");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText("");
	   		  
	   			
	   			 pos=	(TextView)findViewById(R.id.navigationText2);
	   		     pos.setText("");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationequipment);
	   		     pos.setText("");
	   		  
	   		  
	    		 break;
	    	 case 1:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText(" > ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText(DataAccess.roomname);
	   		  
	   			
	   			 pos=	(TextView)findViewById(R.id.navigationText2);
	   		     pos.setText("");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationequipment);
	   		     pos.setText("");
	    		 break;
	    	 case 2:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText(" > ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText(DataAccess.roomname);
	   		  
	   			
	   			 pos=	(TextView)findViewById(R.id.navigationText2);
	   		     pos.setText(" > ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationequipment);
	   		     pos.setText(DataAccess.equipmentname);
	   		  
	    		 break;
	    	 
	    	 
	    	 
	    	 
	    	 }
	    	

	    	 
	     }
	   
	    @Override
	    protected void onResume()
	    {   
	    	super.onResume();
	    	bPause=false;
	    	SetNavation(2);
	        new Thread(RealTimeActivity.this).start();
	    	
	    }
	    @Override
	    protected void onStart()
	    {
	     
	    	super.onStart();
	    //   	new Thread(RealTimeActivity.this).start();
	    	
	    }
	    @Override
	    protected void onPause()
	    {
	    	super.onPause();
	    	bPause=true;
	    	
	    }
	    public void run()
	    {
	    	try
	    	{
	    		//if(pageindex ==1)
	    		//{
	    			DataAccess.GetEquipmentAnalogSignalsCfg(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);
	    			  Thread.sleep(500);	
	    		//}
	    		//if(pageindex==0)
	    		//{
	    				DataAccess.GetEquipmentSwitchSignalsCfg(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);
	    		    		//}
	            handler.sendEmptyMessage(0);
	            while(!bPause)
	                 {	            	  
	    		   	    for(int i=0;i<4;i++)
	    		   	    {
	    		   	    	if(bPause)
	    		   	    	{return ;}
	    		   	    	if(bQuery)
	    		   	    	{
	    		   	    		break;
	    		   	    	}
	            	 	   Thread.sleep(1000);	
	    		   	    }
	    		        bQuery=false;
	    		        if(pageindex ==0){
	            	 	DataAccess.GetRealTimeAnalogSignals(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
	    		        }
	    		        if(pageindex ==1){
		            	 	DataAccess.GetRealTimeSwitchSignals(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
		    		        }
	    		        if(pageindex ==2){
		            	 	   DataAccess.GetEquipmentActiveAlarm(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
		    		        }  	    		        
	            	 	handler.sendEmptyMessage(0);
	            	 	
	                 }
	    	}	         
   		   catch (InterruptedException e) 
   		   {
			
				  e.printStackTrace();
   		   }
	    	
	      }
	
 }
	  

