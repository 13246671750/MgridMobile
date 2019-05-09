package mgrid.software.software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class StationViewPagerActivity extends Activity implements Runnable{
	
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
	private Handler msgHandler;
	ListAdapter adapteranalog;
	ListAdapter adapterswitch;
	ListView lvrooms;  
	ListView lvweather;  
	ListView lvstation;  
	   
	ListAdapter adapteralarm;
	ListAdapter adapterweather;
	ListAdapter adapterstation;
    SQLiteDatabase sqldb;  
    public String DB_NAME = "db.sqlite";  
    public String DB_TABLE = "num";  
    public int DB_VERSION = 1;  
    public int nposition=0;
    public boolean bPause= false;
    final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);  
    public int pageindex=0;
    public boolean bQuery=true;
    // DbHelper类在DbHelper.java文件里面创建的  
    ListView lvanalog;  
    ListView lvswitch;
    ListView lvalarm;
    ListAdapter adapter;
    static int viewpagerindex=0;
    private boolean bfirst=true;
    private ArrayList<HashMap<String, Object>>  data=new ArrayList<>();
    

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.stationviewpagerlayout);
			myAdapter = new MyPagerAdapter();
			myViewPager = (ViewPager) findViewById(R.id.viewpager);
			myViewPager.setAdapter(myAdapter);

			mListViews = new ArrayList<View>();
			mInflater = getLayoutInflater();
			//layout1 = mInflater.inflate(R.layout.pageforstationoverview, null);
			layout2 = mInflater.inflate(R.layout.pageforstations, null);

			//mListViews.add(layout1);
			mListViews.add(layout2);
			myViewPager.setCurrentItem(0);
	
    		
		  	myViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		  		
		  	lvrooms = (ListView) layout2.findViewById(R.id.stationlv);       
		  	
		    data=DataAccess.parents;
		  	setAdapter(data);
		 
     	    registerForContextMenu(lvrooms);
     	    //InitImageView();
	        //View v1=findViewById(R.id.navigationstation);    
	        //v1.setOnClickListener(naigationclick);
	 	 	  
     	     //String[] ColumnNamesstation = { "Name","Icon","Value"};    
     	     //adapterstation = new SimpleAdapter(this, DataAccess.uistationoverview, 
	         //R.layout.stationoverviewitems,  ColumnNamesstation, new int[] {R.id.name,R.id.icon, R.id.value});  
     	     //lvstation = (ListView)layout1.findViewById(R.id.lv);  
     	     //lvstation.setAdapter(adapterstation);  
	}
    
    
    private void setAdapter(ArrayList<HashMap<String, Object>> datas)
    {
     	adapter = new SimpleAdapter(getApplicationContext(),datas, 
                R.layout.pageforstationlistitem,  
                new String[] {"image", "name"}, 
                new int[] { R.id.station_ItemImage, R.id.station_list_Name} 
    		  	);
    		  	
    		  	lvrooms.setAdapter(adapter);          
    		  	lvrooms.setOnItemClickListener(itemListener);
    }
    
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
					  new Thread(StationViewPagerActivity.this).start();
					e.printStackTrace();
				}	
 	 	      
	   	    }

	   	    bQuery=false;
	   	    handler.sendEmptyMessage(0);
      }
	}
	
    OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view,  int position,
				long id) {
			try
			{
				
				DataAccess.parentId=(Integer)data.get(position).get("parentId");
				
				if(DataAccess.parentId==0)
				{
					
					int id_qy=(int) DataAccess.parents.get(position).get("id");
					
					data=DataAccess.parentMap.get(id_qy);
								
				  	setAdapter(data);
				
					lvrooms.setVisibility(View.GONE);
		   			((BaseAdapter)adapter).notifyDataSetChanged();	
				    lvrooms.setVisibility(View.VISIBLE);
					
				}else
				{
					
					DataAccess.stationid=(Integer)data.get(position).get("id");
					DataAccess.stationname= (String)data.get(position).get("name");
					Intent intent= new Intent(StationViewPagerActivity.this,RoomsActivity.class);			 		 
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);			   
					Window w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("实时信号", intent);					
					View view1=w.getDecorView();
					RealtimeActivityGroup.rtgroup.setContentView(view1);     
					RealtimeActivityGroup.activityindex=1;	
					
				}

			
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
			}
		}
	};
	
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
		                  	intent = new Intent(StationViewPagerActivity.this, EquipmentsActivity.class);  
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
		                  	intent = new Intent(StationViewPagerActivity.this, RoomsActivity.class);  
				    		intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
						   	w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
							view1=w.getDecorView();
							RealtimeActivityGroup.rtgroup.setContentView(view1);   
							RealtimeActivityGroup.activityindex=1;	
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
		   		switch(msg.what)
		   		{
		   			/*case 0:
		   			{
		   				DataAccess.uistationoverview.clear();
		   				for(int i=0;i<DataAccess.stationoverview.size();i++)
		   				{
		   					DataAccess.uistationoverview.add((HashMap<String, Object>) (DataAccess.stationoverview.get(i).clone()));
		   			
		   				}
		   				lvstation. setVisibility(View.GONE);
		   				((BaseAdapter)adapterstation).notifyDataSetChanged();	
		   				lvstation.setVisibility(View.VISIBLE);
		   			
		   			 }
		   			
		   			{
		   			    
		   		   		DataAccess.uistationweather.clear();
		   		   			for(int i=0;i<DataAccess.stationweather.size();i++)
		   		   			{
		   		   				DataAccess.uistationweather.add((HashMap<String, Object>) (DataAccess.stationweather.get(i).clone()));
		   	   			
		   		   			}
		   		   		
		   		   		
		   		   			((TextView)layout1.findViewById(R.id.weather0)).setText(DataAccess.uistationweather.get(0).get("Value").toString());      	
		   		   			((TextView)layout1.findViewById(R.id.weather1)).setText(DataAccess.uistationweather.get(1).get("Value").toString());     
		   		   			((TextView)layout1.findViewById(R.id.weather2)).setText(DataAccess.uistationweather.get(2).get("Value").toString());     
		   		   			((TextView)layout1.findViewById(R.id.weather3)).setText(DataAccess.uistationweather.get(3).get("Value").toString());     
		   			}
		   		   break;*/
		   		
		   			case 0:
		   			
		   			
		   				
		   				LoginActivity.xianChengChi.execute(new Runnable() {
							
							@Override
							public void run() {
								
								DataAccess.GetStations(DataAccess.clientid,handler);	
								
							}
						});
		   				
		   				
		   			
					    break;
					    
		   			case 1:
		   				
		   			
			   			lvrooms.setVisibility(View.GONE);
			   			((BaseAdapter)adapter).notifyDataSetChanged();	
					    lvrooms.setVisibility(View.VISIBLE);				    
						
					   
					    
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
	   		     pos.setText(" > ");
	   		  
	   		     pos=	(TextView)findViewById(R.id.navigationroom);
	   		     pos.setText(DataAccess.roomname);
	   		  
	       		 break;
	    	 case 2:
	    		 pos=	(TextView)findViewById(R.id.navigationstation);
	   	         pos.setText( DataAccess.stationname );
	   	     
	   	         pos=	(TextView)findViewById(R.id.navigationText1);
	   		     pos.setText(" > ");
	   		  
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
	 
	    
	     private void InitImageView() {
	    		 DisplayMetrics dm = new DisplayMetrics();
			     getWindowManager().getDefaultDisplay().getMetrics(dm);
			     int screenW = dm.widthPixels;// 获取分辨率宽度
		         cursor = (ImageView) findViewById(R.id.cursor);
		         cursor.setMaxWidth(screenW /2);
		         bmpW=screenW /2;
		       //  bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.lineblue).getWidth();// 获取图片宽度	     
		         offset = (screenW /2- bmpW) / 1;// 计算偏移量
		         Matrix matrix = new Matrix();
		         matrix.postTranslate(offset, 0);
		         cursor.setImageMatrix(matrix);// 设置动画初始位置
	     }
	     /**
	     * 初始化头标
	     */
	     private void InitTextView() {
	    	 		t1 = (TextView) findViewById(R.id.text1);
	    	 		t2 = (TextView) findViewById(R.id.text2);
	    	 	//	t3 = (TextView) findViewById(R.id.text3);

	    	 		t1.setOnClickListener(new MyOnClickListener(0));
	    	 		t2.setOnClickListener(new MyOnClickListener(1));
	    	   // 	t3.setOnClickListener(new MyOnClickListener(2));
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
	    	 	viewpagerindex=index;
	     		}
	     };
	     
	     /**
	     * 页卡切换监听
	     */
	     public class MyOnPageChangeListener implements OnPageChangeListener {

	     int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	     int two = one * 2;// 页卡1 -> 页卡3 偏移量

	   
	     public void onPageSelected(int arg0) {
	     pageindex=arg0;
	     Animation animation = null;
	     switch (arg0) {
	     case 0:
	    	 viewpagerindex=0;
	    	 handler.sendEmptyMessage(0); 
		     if (currIndex == 1) {
		     animation = new TranslateAnimation(one, 0, 0, 0);
		     } else if (currIndex == 2) {
		     animation = new TranslateAnimation(two, 0, 0, 0);
		     }
		     break;
	     case 1:
	    	 viewpagerindex=1;
	    	 handler.sendEmptyMessage(0);
		     if (currIndex == 0) {
		     animation = new TranslateAnimation(offset, one, 0, 0);
		     } else if (currIndex == 2) {
		     animation = new TranslateAnimation(two, one, 0, 0);
		     }
		     break;
	     case 2:
	    	 viewpagerindex=2;
	    	 handler.sendEmptyMessage(0);
		     if (currIndex == 0) {
		     animation = new TranslateAnimation(offset, two, 0, 0);
		     } else if (currIndex == 1) {
		     animation = new TranslateAnimation(one, two, 0, 0);
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
	     @Override  
		 public void onResume()
		    {
	    	  super.onResume();
	    	  RealtimeActivityGroup.activityindex=0;	

	    	  myViewPager.setCurrentItem(viewpagerindex);
	    	  handler.sendEmptyMessage(0);
	    	  
	  	 	  new Thread(StationViewPagerActivity.this).start();
	    	  bPause=false;
		    }			
}