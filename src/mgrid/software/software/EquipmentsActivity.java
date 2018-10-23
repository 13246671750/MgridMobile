package mgrid.software.software;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
	  
	public class EquipmentsActivity extends Activity {  

	    public String DB_NAME = "db.sqlite";  
	    public String DB_TABLE = "num";  
	    public int DB_VERSION = 1;  
	    public int nposition=0;
	    ListAdapter adapter;
	    final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);
	    public boolean bPause= false;
	    public boolean bQuery=true;
	    
	    private Button btnOpen,btnClose; 
	    
		ListView lv;
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        // TODO Auto-generated method stub  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.equipmentslayout);  
	    	
	        DataAccess.GetEquipmentsWithOverViewData(DataAccess.stationid,DataAccess.roomid);
	        
	        lv = (ListView) findViewById(R.id.equipments_lv);
	        
	        String[] ColumnNames = {"seq","name","data0","data1","data2","data3","image"};    
	        adapter = new SimpleAdapter(this, DataAccess.equipmentslisttitems, 
	                R.layout.equipmentslayoutitem,  
	                ColumnNames, 
	                new int[] { R.id.equipment_list_id,R.id.equipment_list_Name,R.id.data0,R.id.data1,R.id.data2,R.id.data3,R.id.image});  
//	        adapter=new MySimpleAdapter(this, DataAccess.equipmentslisttitems, R.layout.equipmentslayoutitem, 
//	        		ColumnNames, 
//	        		new int[] { R.id.equipment_list_id,R.id.equipment_list_Name,R.id.data0,R.id.data1,R.id.data2,R.id.data3,R.id.image});      
		    lv.setAdapter(adapter);  
	     	lv.setOnItemClickListener(itemListener);
	     	
	     	
	     	

			// 设置长按事件
			registerForContextMenu(lv);
			
			SetNavation(1);
	    } 
			
		OnItemClickListener itemListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				try{
				 	 DataAccess.equipmentid=(Integer) DataAccess.equipmentslisttitems.get(position).get("id");
					 DataAccess.equipmentname= (String)DataAccess.equipmentslisttitems.get(position).get("name");

					 Intent intent= new Intent(EquipmentsActivity.this,EquipmentViewPagerActivity.class);
						
					 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 Window w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("实时信号", intent);
					 View view1=w.getDecorView();
					 RealtimeActivityGroup.rtgroup.setContentView(view1);   
					 RealtimeActivityGroup.activityindex=3;
				}
				catch(Exception e)
				{
					String msg= e.getMessage();
					msg=msg;
				}
					
			}
		};

	      
		public void run() {
	    	 
			while(!bPause)
			{	            	  
		   	    for(int i=0;i<60;i++)
		   	    {
		   	    	if(bPause)
		   	    	{return ;}
		   	    	if(bQuery)
		   	    	{
		   	    		break;
		   	    	}
		   	    	try {
					Thread.sleep(1000);
					
					} catch (InterruptedException e) {
						  //new Thread(RoomsActivity.this).start();
						e.printStackTrace();
					}	
		   	    }

		   	    bQuery=false;
		   	    handler.sendEmptyMessage(0);
	      }
		}
		
		@Override  
	    protected void onDestroy() {// 关闭数据库  
	        // TODO Auto-generated method stub  
	        super.onDestroy();  
	        if (helper != null) {  
	            helper.close();  
	        }  
	    } 
		
	    private Handler handler =new Handler()
	   	 {
	   	    @Override
	   	   //当有消息发送出来的时候就执行Handler的这个方法
	   	 	  public void handleMessage(Message msg){
	   		  super.handleMessage(msg);
	   		  
	   		DataAccess.GetEquipmentsWithOverViewData(DataAccess.stationid,DataAccess.roomid);
	   		
	   		  lv.setVisibility(View.GONE);
			  ((BaseAdapter)adapter).notifyDataSetChanged();	
			  lv.setVisibility(View.VISIBLE);
	   		
	   	    }
	     };
	     
	 

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
	   		  
	    		 break;
	    	 case 1:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText(" >> ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText(DataAccess.roomname);
	   		  
	       		 break;
	    	 case 2:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText(" >> ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText(DataAccess.roomname);
	   		  
	   	  		 pos=	(TextView)findViewById(R.id.devicename);
	   		     pos.setText(DataAccess.equipmentname);
	   		  
	    		 break;
   	 
	    	 }
	   }
	    
	    @Override
		 protected void onPause()
		 {
		  	super.onPause();
		   	bPause=true;   	
		 }
	    
	    @Override  
	    public void onResume()
	    {
	   		RealtimeActivityGroup.activityindex=2;	
	   		super.onResume();
	    	SetNavation(1);
	    	bPause=false;	    	  
	   	    new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(1000);
				
				//你要执行的方法
	   							  							    	  		
				//执行完毕后给handler发送一个空消息
				DataAccess.GetEquipmentsWithOverViewData(DataAccess.stationid,DataAccess.roomid);
				handler.sendEmptyMessage(0);
				} 
				catch ( Exception e) {
					 
				}
				}
			}.start();
	    } 
	  
	}
