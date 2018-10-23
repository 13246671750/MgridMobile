
package mgrid.software.software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mgrid.software.software.R;

import mgrid.software.software.protocol.ConfigType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AlarmViewPagerActivity extends Activity implements Runnable{
	
    private ViewPager mPager;//页卡内容
    private List<View> listViews=new ArrayList<View>(); // Tab页面列表
    private List<ListView> listlv=new ArrayList<ListView>(); // Tab页面列表
    private List<SimpleAdapter> listadapter=new ArrayList<SimpleAdapter>();
    private ImageView cursor;// 动画图片
    private TextView t1, t2, t3,t4,t5;// 页卡头标
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
	private View layout5= null;
	private Handler msgHandler;
	ListAdapter adapteranalog;
	ListAdapter adapterswitch;
	ListView lv;  
	 private int alarmlevel=0; 
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
    ListAdapter adapter;
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bPause=false;
	 
			setContentView(R.layout.alarmviewpagerlayout);
			
			myAdapter = new MyPagerAdapter();
			myViewPager = (ViewPager) findViewById(R.id.viewpager);
			myViewPager.setAdapter(myAdapter);

			mListViews = new ArrayList<View>();
			mInflater = getLayoutInflater();
			layout1 = mInflater.inflate(R.layout.pageforalarmdatalist, null);
		 	layout2 = mInflater.inflate(R.layout.pageforalarmdatalist, null);
		 	layout3 = mInflater.inflate(R.layout.pageforalarmdatalist, null);
		    layout4 = mInflater.inflate(R.layout.pageforalarmdatalist, null);
		    layout5 = mInflater.inflate(R.layout.pageforalarmdatalist, null);
			
			mListViews.add(layout1);
			mListViews.add(layout2);
	    	mListViews.add(layout3);
			mListViews.add(layout4);
			mListViews.add(layout5);
	 
		
			InitImageView();
			// 初始化当前显示的view
			myViewPager.setCurrentItem(0);
		 
		  	myViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		  	DataAccess.uialiveevents.clear();
		  	for(int i=0;i<5;i++)
		  	{
		        String[] ColumnNames = {"Seq","Name","Position","StartTime","Level"};    
		 	    adapter = new SimpleAdapter(this, DataAccess.uialiveevents, 
		                    R.layout.alarmlistviewitem,  ColumnNames, new int[] { R.id.active_alarm_id,   R.id.active_alarm_descript, R.id.active_alarm_position, R.id.active_alarm_starttime, R.id.active_alarm_level});  
		 	    lv = (ListView) mListViews.get(i).findViewById(R.id.active_alarm_lv);  
		 	    lv.setAdapter(adapter);
		 	    listadapter.add((SimpleAdapter) adapter);
		 	    listlv.add(lv);
		  	}
		  	TextView tv=  (TextView) findViewById(R.id.pageheadtitlecenter);  
		  	tv.setText("故障信息");
		 	  
		 	//	DataAccess.activities.add(this);
		 	     
     	    //InitImageView();
	       //  View v1=findViewById(R.id.navigationstation);    
	       //  v1.setOnClickListener(naigationclick);

	}
	  
	   
	    private Handler handler =new Handler()
	  	 {
	  	    @Override
	  	   //当有消息发送出来的时候就执行Handler的这个方法
	  	 	  public void handleMessage(Message msg){
	  	    	
	  		 super.handleMessage(msg);	
	  		DataAccess.uialiveevents.clear();
	   		for(int i=0;i<DataAccess.aliveevents.size();i++)
	   		{
	   			DataAccess.uialiveevents.add((HashMap<String, Object>) (DataAccess.aliveevents.get(i).clone()));
	   			
	   		}
	   		listlv.get(currIndex).setVisibility(View.GONE);
			   ((BaseAdapter)listadapter.get(currIndex)).notifyDataSetChanged();
		
			 listlv.get(currIndex).setVisibility(View.VISIBLE);
	    
	   		
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
	         cursor.setMaxWidth(screenW /5);
	         bmpW=screenW /5;
	       //  bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.lineblue).getWidth();// 获取图片宽度	     
	         offset = (screenW /5 - bmpW) / 2;// 计算偏移量
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
	    	 		t3 = (TextView) findViewById(R.id.text3);
	    	 		t4 = (TextView) findViewById(R.id.text4);
	    	 		t5 = (TextView) findViewById(R.id.text5);
	    	 		
	    	 		t1.setOnClickListener(new MyOnClickListener(0));
	    	 		t2.setOnClickListener(new MyOnClickListener(1));
	    	 		t3.setOnClickListener(new MyOnClickListener(2));
	    	 		t4.setOnClickListener(new MyOnClickListener(3));
	    	 		t5.setOnClickListener(new MyOnClickListener(4));
	    	 	
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

	     int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	     int two = one *2;// 页卡1 -> 页卡3 偏移量
	     int three = one * 3;// 页卡1 -> 页卡3 偏移量
	     int four = one * 4;// 页卡1 -> 页卡3 偏移量
	     int five = one * 5;// 页卡1 -> 页卡3 偏移量

	   
	     public void onPageSelected(int arg0) {
	    
	     Animation animation = null;
	     switch (arg0) {
	     case 0:
	     alarmlevel=0;
	     		if (currIndex == 1)
	     		{
	     				animation = new TranslateAnimation(one, 0, 0, 0);
	     				
	     		} 
	     		else if (currIndex == 2) {

	     			animation = new TranslateAnimation(two, 0, 0, 0);
	     				}
	     		
	     break;
	     case 1:
	         alarmlevel=4;     	
	         if (currIndex == 0) {
	        	 animation = new TranslateAnimation(offset, one, 0, 0);
	         		} else if (currIndex == 2) {
	         			animation = new TranslateAnimation(two, one, 0, 0);
	         		}
	     break;
	     case 2:
	    	   alarmlevel=3;
		     	 
	    	   if (currIndex == 1) 
	    	   {
	    		   						
	    		   animation = new TranslateAnimation(one, two, 0, 0);
	    	   							
	    	   } 
	    	   else if (currIndex == 3)
	    	   {
	    	   	 animation = new TranslateAnimation(three, two, 0, 0);
	    	   		}
	    	   break;
	   
	     case 3:
	    	   alarmlevel=2;
		     	 
	    	   if (currIndex == 2) 
	    	   {
	    		   						
	    		   animation = new TranslateAnimation(two, three, 0, 0);
	    	   							
	    	   } 
	    	   else if (currIndex == 4) {
	    	   	 animation = new TranslateAnimation(four, three, 0, 0);
	    	   		}
	    	   break;
	    
	     case 4:
	    	   alarmlevel=1;
		     	 
	    	   if (currIndex == 3)
	    	   { 		   						
	    		   animation = new TranslateAnimation(three, four, 0, 0);
	    	   		}
	    	   else if (currIndex == 5)
	    	   {
	    	   	 animation = new TranslateAnimation(five, four, 0, 0);
	    	   		}
	    	   break;
	     }
	     bQuery=true;
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
	    	  try
	    	  {
	        	new Thread(AlarmViewPagerActivity.this).start();
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  
	    		  String aa= e.getMessage();
	    		  aa=aa;
	    		  
	    	  }
	        
		   	  
		    }



	     public void run()
	     {
	     	try
	     	{
	     	 	DataAccess.GetActiveAlarm(DataAccess.stationid,alarmlevel,0,10);	
	     	 	handler.sendEmptyMessage(0);
	               while(!bPause)
	                  {
	     		   	    for(int i=0;i<5;i++)
	     		   	    {
	     		   	    	if(bPause)
	     		   	    	{return ;}
	     		   	    	if(bQuery)
	     		   	    	{
	     		   	    		break;
	     		   	    	}
	             	 	   Thread.sleep(1000);	
	     		   	    }
	     		   	    bQuery= false;
	     		   	DataAccess.GetActiveAlarm(DataAccess.stationid,alarmlevel,0,10);	       	 
	             	 	handler.sendEmptyMessage(0);
	             	 	
	                  }
	     	}	         
	 	   catch (InterruptedException e) 
	 		{
	 		  bPause=false;
	 		  bQuery=false;
	 	     	new Thread(AlarmViewPagerActivity.this).start();
	 			  e.printStackTrace();
	 		   }
	     	
	       }
}