package mgrid.software.software;

	import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

	public class EquipmentViewPagerActivity extends Activity implements Runnable{
		
		
	
	    private ViewPager mPager;//页卡内容
	    private List<View> listViews; // Tab页面列表
	    private ImageView cursor;// 动画图片
	    private TextView t1, t2, t3;// 页卡头标
	    private int offset = 0;// 动画图片偏移量
	    private int currIndex = 0;// 当前页卡编号
	    private int bmpW;// 动画图片宽度
		private ViewPager myViewPager;
		private MyPagerAdapter myAdapter;
		private LayoutInflater mInflater;
		private List<View> mListViews;
		private View layout1 = null;
		private View layout2 = null;
		private View layout3 = null;
		private View layout4 = null;
		private Handler msgHandler;
		ListAdapter adapteranalog;
		ListAdapter adapterswitch;
		
		ListAdapter adapteralarm;
		
		ListAdapter adapterconmmnd;
		
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
	    ListView lvconmmnd;
		boolean bGetSwitch = false;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			

			
			try
			{
				setContentView(R.layout.equipmentsignalslayout);
			}
			catch(Exception e)
			{ 
				
				String aa=e.getMessage();
				aa = e.getMessage();
			}
			
			DataAccess.GetEquipmentAnalogSignalsCfg(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
    	 	//liangyj//DataAccess.GetEquipmentSwitchSignalsCfg(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
    	 	//liangyj//DataAccess.GetEquipmentActiveAlarm(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);
    	 	   
			myAdapter = new MyPagerAdapter();
			myViewPager = (ViewPager) findViewById(R.id.equpmentviewpager);
			myViewPager.setAdapter(myAdapter);

			mListViews = new ArrayList<View>();
			mInflater = getLayoutInflater();
			layout1 = mInflater.inflate(R.layout.pageforanalogsignals, null);
			layout2 = mInflater.inflate(R.layout.pageforswitchsignal, null);
			layout3 = mInflater.inflate(R.layout.pageforequipmentalarm, null);
			layout4 = mInflater.inflate(R.layout.pageforequipmentcontrol, null);

			mListViews.add(layout1);
			mListViews.add(layout2);
			mListViews.add(layout3);
			mListViews.add(layout4);
			InitImageView();
			// 初始化当前显示的view
			myViewPager.setCurrentItem(0);

			myViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
			try
			{		
				String[] ColumnNames = {"Seq","Name","Value","Unit"};    
	     	    adapteranalog = new SimpleAdapter(this, DataAccess.equipmentanalogsignalsitems, 
		                    R.layout.equipmentanalogsignalitem,  
		                    ColumnNames, 
		                    new int[] { R.id.signals_list_id,   R.id.signals_list_Name, R.id.signals_list_value, R.id.signals_list_unit});  
	     	    lvanalog = (ListView) layout1.findViewById(R.id.equipment_analog_signallv);  
	     	    lvanalog.setAdapter(adapteranalog);  
			     
	     	    String[] ColumnNames1 = {"Seq","Name","Value"};    
	     	    adapterswitch = new SimpleAdapter(this, DataAccess.equipmentswitchsignalsitems, 
		                    R.layout.equipmentswitchsignalitem,  
		                    ColumnNames1, 
		                    new int[] { R.id.signals_list_id,   R.id.signals_list_Name, R.id.signals_list_value});  
	     	   lvswitch = (ListView) layout2.findViewById(R.id.equipment_switch_signallv);  
	     	   lvswitch.setAdapter(adapterswitch);  

			     
	     	   String[] ColumnNames2 = {"Seq","Name","StartTime","Level"};    
			   adapteralarm = new SimpleAdapter(this, DataAccess.equipmentalarmitems, 
		                    R.layout.equipmentalarmsignalitem,  
		                    ColumnNames2, 
		                    new int[] { R.id.equipment_active_alarm_id,   R.id.equipment_active_alarm_descript,  R.id.equipment_active_alarm_starttime, R.id.equipment_active_alarm_level});
			   lvalarm = (ListView) layout3.findViewById(R.id.equipment_alarm_signallv);  
		       lvalarm.setAdapter(adapteralarm);
		       
		       
		       String[] ColumnNames3 = {"Seq","Name","Value"};    
		       adapterconmmnd = new SimpleAdapter(this, DataAccess.equipmentcommanditems, 
		                    R.layout.equipmentcommad,  
		                    ColumnNames3, 
		                    new int[] { R.id.commnd_list_id,   R.id.commnd_list_Name, R.id.commnd_list_value});  
		       lvconmmnd = (ListView) layout4.findViewById(R.id.equipment_controls_signallv);  
		       lvconmmnd.setAdapter(adapterconmmnd);  
		       
		       registerForContextMenu(lvanalog);
			}
			catch(Exception e)
			{
				String aa= e.getMessage();
				aa=aa;
			}
			
			SetNavation(2);	 	
			lvconmmnd.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					AlertDialog.Builder builder=new AlertDialog.Builder(RealtimeActivityGroup.rtgroup);
					builder.setTitle("提示");
					builder.setMessage("确认发送命令？");
					builder.setPositiveButton("确认", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									DataAccess.getEquipmentCommandControl(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid,arg2);	
									
								}
							}).start();
							
							
							
						}
					});
					builder.setNegativeButton("取消", new  OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							
							
						}
					});
				
					 builder.show();
				
	
					
				}
			});
		}
		
		//控制命令页面Items点击事件
		
		
		
		
		
		public void run()
	    {
	    	try
	    	{
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
            	 	   Thread.sleep(1000);	
    		   	    }
    		        bQuery=false;
    		        
            	 	handler.sendEmptyMessage(pageindex);
            	 	
                 }
	    	}	         
   		   catch (InterruptedException e) 
   		   {
	   			bPause=false;
	   			bQuery=false;
	   			new Thread(EquipmentViewPagerActivity.this).start();
   			 
				e.printStackTrace();
   		   }	    	
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
	                  	intent = new Intent(EquipmentViewPagerActivity.this, EquipmentsActivity.class);  
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
	                  	intent = new Intent(EquipmentViewPagerActivity.this, RoomsActivity.class);  
			    		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
					   	w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
						view1=w.getDecorView();
						RealtimeActivityGroup.rtgroup.setContentView(view1);   
						RealtimeActivityGroup.activityindex=3;	
		    		}
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
		    
		    
		 private Handler handler =new Handler()
	   	 {
	   	    @Override
	   	   //当有消息发送出来的时候就执行Handler的这个方法
	   	 	  public void handleMessage(Message msg){  	    	
	   		  super.handleMessage(msg);
	   		  switch(pageindex)
	   		  {
	   		  case 0:
	   			System.out.println("数字0");
	   			DataAccess.GetRealTimeAnalogSignals(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
	   			
	   	   		lvanalog.setVisibility(View.GONE);	
			    ((BaseAdapter) adapteranalog).notifyDataSetChanged();			
			    lvanalog.setVisibility(View.VISIBLE);
	   			  break;
	   		  case 1:
	   			System.out.println("数字1");
	   			DataAccess.GetRealTimeSwitchSignals(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
	   			
	   			lvswitch.setVisibility(View.GONE);		
	   			((BaseAdapter) adapterswitch).notifyDataSetChanged();		
	   			lvswitch.setVisibility(View.VISIBLE);
	   			  break;
	   		  case 2:
	   			System.out.println("数字2");
	   			DataAccess.GetEquipmentActiveAlarm(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
	   			
	   			lvalarm.setVisibility(View.GONE);
	   			((BaseAdapter) adapteralarm).notifyDataSetChanged();	
	   			lvalarm.setVisibility(View.VISIBLE);
	   			  break;
	   			  
	   		  case 3:
	   			System.out.println("数字3");
	   			DataAccess.context=getApplicationContext();
		   		DataAccess.GetEquipmentCommand(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);	
		   			
		   			lvalarm.setVisibility(View.GONE);
		   			((BaseAdapter) adapterconmmnd).notifyDataSetChanged();	
		   			lvalarm.setVisibility(View.VISIBLE);
		   			  break;
	   		  
	   		  }
	    		
	   	    }
	      };
		private class MyPagerAdapter extends PagerAdapter {

			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				Log.d("k", "destroyItem");
				((ViewPager) arg0).removeView(mListViews.get(arg1));
			}

			@Override
			public void finishUpdate(View arg0) {
				Log.d("k", "finishUpdate");
			}

			@Override
			public int getCount() {
				Log.d("k", "getCount");
				return mListViews.size();
			}

			@Override
			public Object instantiateItem(View arg0, int arg1) {
				Log.d("k", "instantiateItem");
				((ViewPager) arg0).addView(mListViews.get(arg1), 0);
				return mListViews.get(arg1);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				Log.d("k", "isViewFromObject");
				return arg0 == (arg1);
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {
				Log.d("k", "restoreState");
			}

			@Override
			public Parcelable saveState() {
				Log.d("k", "saveState");
				return null;
			}

			@Override
			public void startUpdate(View arg0) {
				Log.d("k", "startUpdate");
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
		    protected void onResume()
		    {   
		    	super.onResume();
		    	RealtimeActivityGroup.activityindex=3;	
		    	bPause=false;
		    	SetNavation(2);
		    	handler.sendEmptyMessage(pageindex);
		        new Thread(EquipmentViewPagerActivity.this).start();
		    	
		    }
		    @Override
		    protected void onPause()
		    {
		    	super.onPause();
		    	bPause=true;
		    	
		    }
		   
		 
		     private void InitImageView() {
		         cursor = (ImageView) findViewById(R.id.cursor);
		        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.blue)
		               .getWidth();
		         // 获取图片宽度
		         DisplayMetrics dm = new DisplayMetrics();
		         getWindowManager().getDefaultDisplay().getMetrics(dm);
		         int screenW = dm.widthPixels;// 获取分辨率宽度
		         offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		         Matrix matrix = new Matrix();
		         //int start=  cursor.getLeft();
		         matrix.postTranslate(0, 0);
		         
		         cursor.setImageMatrix(matrix);// 设置动画初始位置
		      
		    
		         LinearLayout  myv = (LinearLayout) findViewById(R.id.signaltypelayout);
		     
		          int wight=0;
		          //int wight1=0;
		          int hight=0;
		          myv.measure(wight, hight);
		          offset=  myv.getMeasuredWidth()/4;
		     }
		     /**
		     * 初始化头标
		     */
		     private void InitTextView() {
		    	 
			     t1 = (TextView) findViewById(R.id.text1);
			     t2 = (TextView) findViewById(R.id.text2);
			     t3 = (TextView) findViewById(R.id.text3);
	
			     t1.setOnClickListener(new MyOnClickListener(0));
			     t2.setOnClickListener(new MyOnClickListener(1));
			     t3.setOnClickListener(new MyOnClickListener(2));
		     }
		     /**
		     * 头标点击监听
		     */
		     public class MyOnClickListener implements View.OnClickListener {
		     private int index = 0;

		     public MyOnClickListener(int i) {
		     index = i;
		     }

		 
		     public void onClick(View v) {
		      mPager.setCurrentItem(index);
		     }
		     };
		     /**
		     * 页卡切换监听
		     */
	
		     public class MyOnPageChangeListener implements OnPageChangeListener {

		   //  int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		  //   int two = one * 2;// 页卡1 -> 页卡3 偏移量
             int one=0;
             int two =offset*1;
		     int three=offset*2;
		     int four=offset*3;
		     public void onPageSelected(int arg0) {
		     pageindex=arg0;

		     bQuery=true;
		     if(arg0 == 1 && bGetSwitch == false)
		     {
		    	 DataAccess.GetEquipmentSwitchSignalsCfg(DataAccess.stationid,DataAccess.roomid,DataAccess.equipmentid);
		    	 bGetSwitch = true;
		     }
		     Animation animation = null;
		     switch (arg0) {
		     case 0: 
			     if (currIndex == 1) {
			     animation = new TranslateAnimation(two, one, 0, 0);
			     } else if (currIndex == 2) {
			     animation = new TranslateAnimation(three, one, 0, 0);
			     }else if (currIndex == 3) {
				     animation = new TranslateAnimation(four, one, 0, 0);
				     }
			     break;
		     case 1:
			     if (currIndex == 0) {
			     animation = new TranslateAnimation(one,two, 0, 0);
			     } else if (currIndex == 2) {
			     animation = new TranslateAnimation(three, two, 0, 0);
			     }else if (currIndex == 3) {
				     animation = new TranslateAnimation(four, two, 0, 0);
				     }
			     break;
		     case 2:
			     if (currIndex == 1) {
			     animation = new TranslateAnimation(two, three, 0, 0);
			     } else if (currIndex == 0) {
			     animation = new TranslateAnimation(one, three, 0, 0);
			     }else if (currIndex == 3) {
				     animation = new TranslateAnimation(four, three, 0, 0);
				     }
			     break;
		     case 3:
			     if (currIndex == 0) {
			     animation = new TranslateAnimation(one, four, 0, 0);
			     } else if (currIndex == 1) {
			     animation = new TranslateAnimation(two, four, 0, 0);
			     }else if (currIndex == 2) {
				     animation = new TranslateAnimation(three, four, 0, 0);
				     }
			     break;
		     }
		     currIndex = arg0;
		     animation.setFillAfter(true);// True:图片停在动画结束位置
		     animation.setDuration(100);
		     cursor.startAnimation(animation);
		     }

		   
		     public void onPageScrolled(int arg0, float arg1, int arg2) {
		     }

		     public void onPageScrollStateChanged(int arg0) {
		     }
		     }
	}