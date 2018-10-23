
package mgrid.software.software;

import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;

import mgrid.software.software.R;
import org.achartengine.ChartFactory; 
import org.achartengine.chart.PointStyle; 
import org.achartengine.model.XYMultipleSeriesDataset; 
import org.achartengine.model.XYSeries; 
import org.achartengine.renderer.XYMultipleSeriesRenderer; 
import org.achartengine.renderer.XYSeriesRenderer;

import mgrid.software.software.protocol.ReportType;

import android.R.color;
import android.app.Activity; 
import android.content.Intent;
import android.graphics.Color; 
import android.os.Bundle; 
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ReportActivity extends Activity { 
  	
	private  ArrayList<HashMap<String,Object>> powerdata= new ArrayList<HashMap<String,Object>>();
    private ListAdapter adapter;
    private ListView lv;  
    private int year=2012;
    private double[] xdate= new double[365];
    private double[] xdate1= new double[365];
    
    private double[] ydate= new double[365];
    private double[] ydate1= new double[365];

     private int viewindex=0;
     private HashMap<String,Integer> piedata;
    private Handler handler =new Handler()
  	 {
  	    @Override
  	   //当有消息发送出来的时候就执行Handler的这个方法
  	 	  public void handleMessage(Message msg){
  		  super.handleMessage(msg);
  		
   		
  		switch(DataAccess.reporttype)
		{
			case  PowerOutputDaily:
				if(DataAccess.poweroutputdaily.size()>0)
				{
				DrawPowerChart();
				}
				
			break;	 
			case  PowerOutputMonthy:
				if(DataAccess.poweroutputmonthycurves.size()>0)
				{
				DrawBarChart();
				}
				break;	 
			case  AlarmLeverStatic:	
				if(DataAccess.alarmstaticsmap.size()>0)
				{
				DrawPieChart(DataAccess.alarmstaticsmap);
				}
				break;	 
		}
  	//    lv.setVisibility(View.GONE);
  	 //   ((BaseAdapter) adapter).notifyDataSetChanged();
  	 //   lv.setVisibility(View.VISIBLE);
  		 // refleshlistview();		
  	    }
       };
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poweroutputreport);  
      
        View view=findViewById(R.id.addbutton);
	    
 	   view.setOnClickListener(new View.OnClickListener() {
 		
 		public void onClick(View V) {
 			 
 			TextView txt=(TextView) findViewById(R.id.yeartextview);
 		          year=Integer.parseInt(txt.getText().toString());
 		        year++;
 		        txt.setText(Integer.toString(year));
 	 	 
 			
 		}
        });
 	   
 	  View view1=findViewById(R.id.subbutton);
	    
	   view1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View V) {
			 
			TextView txt=(TextView) findViewById(R.id.yeartextview);
		          year=Integer.parseInt(txt.getText().toString());
		        year--;
		        txt.setText(Integer.toString(year));
	 	 
			
		}
       });
	   	
	   View view2=findViewById(R.id.refleshbutton);
	    
 	   view2.setOnClickListener(new View.OnClickListener() {
 		
 		public void onClick(View V) {
 			 
 			  new Thread(){
 		    		@Override
 		    		public void run(){
 		    			try {
 							//Thread.sleep(1000);
 							switch(DataAccess.reporttype)
 							{
 								case  PowerOutputDaily:
 							 
 									DataAccess.GetPowerOutputDaily(year);
 									if(DataAccess.poweroutputdaily.size()>0)
 									{
 									handler.sendEmptyMessage(0);
 									}
 									break;	 								 
 								case  PowerOutputMonthy:
 									
 									DataAccess.GetPowerOutputMonthy(year,DataAccess.stationid,0,14);
 									if(DataAccess.poweroutputmonthycurves.size()>0)
 									{
 									handler.sendEmptyMessage(0);
 									}
 									break;	 
 								case  AlarmLeverStatic:
 									DataAccess.GetAlarmStatics(DataAccess.stationid,year);
 									if(DataAccess.alarmstaticsmap.size()>0)
 									{
 									handler.sendEmptyMessage(0);
 									}
 									break;	 
 							}
 		    			} catch (Exception e) {
 							// TODO Auto-generated catch bl
 		    			}
 		    				
 				   }
 		    	}.start();
 	 	 
 			
 		}
        });
	   	
	   	
	
	
    }
   
  
  public void DrawPowerChart()
  {
	 
	    ReportChartFactory fact= new   ReportChartFactory();
	    LinearLayout charlayout=	(LinearLayout)findViewById(R.id.poweroutputchart);
	    charlayout.removeAllViews();
	    View chart =fact.GetLineChartView(this,   GetLines());
	//    charlayout.setBackgroundColor(color.white);
	 //   charlayout.getBackground().setAlpha(50);
        charlayout.addView(chart);
	  
  }
  public LineCharProp[] GetLines()
  {
	    LineCharProp[] lines = new LineCharProp[1];   
	    LineCharProp line= new LineCharProp ();
	    line.color=Color.RED;
	    line.styles= PointStyle.CIRCLE;
	    line.titles="机房";
	    double[] xdate= new double[DataAccess.poweroutputdaily.size()];
	    double[] ydate= new double[DataAccess.poweroutputdaily.size()];

	    for(int i=0;i<DataAccess.poweroutputdaily.size();i++)
	    {
	    	xdate[i]=i;//(double)DataAccess.poweroutputdaily.get(i).secondtime/(24*60*60);
	      	ydate[i]=DataAccess.poweroutputdaily.get(i).value;
	  	  
	    }
    	line.xdouble=xdate;
    	line.ydouble=ydate;
    	lines[0]=line;
   
	    return lines;

	  
  }
  public void DrawPieChart(HashMap<String,Integer> map)
  {
	    ReportChartFactory fact= new   ReportChartFactory();
	    LinearLayout charlayout=	(LinearLayout)findViewById(R.id.poweroutputchart);
	    charlayout.removeAllViews();
	    LineCharProp line= new LineCharProp ();
	 
	    View chart =fact.GetPieChartView(this, map);
	   // charlayout.setBackgroundColor);
	   // charlayout.getBackground().setAlpha(50);
        charlayout.addView(chart);
	  
  }

  public void DrawBarChart()
  {
	  
	    ReportChartFactory fact= new   ReportChartFactory();
	    LinearLayout charlayout=	(LinearLayout)findViewById(R.id.poweroutputchart); 
	    charlayout.removeAllViews();
	    View chart =fact.GetBarChartView(this);
	 
	 //  charlayout.setBackgroundColor(Color.BLACK);
	   
	 //   charlayout.getBackground().setAlpha(50);
        charlayout.addView(chart);
	  
  }
     @Override  
	 public void onResume()
	    {
	    	super.onResume();
//	        	TextView pos=	(TextView)findViewById(R.id.poweroutputtitle);
//		      pos.setText("逆变器发电量日报表");
		   
	   	    new Thread(){
	    		@Override
	    		public void run(){
	    		try {
						Thread.sleep(1000);
						switch(DataAccess.reporttype)
						{
							case  PowerOutputDaily:
						 
								DataAccess.GetPowerOutputDaily(year);
								if(DataAccess.poweroutputdaily.size()>0)
								{
								handler.sendEmptyMessage(0);
								}
								break;	 								 
							case  PowerOutputMonthy:
								
								DataAccess.GetPowerOutputMonthy(year,DataAccess.stationid,0,0);
								if(DataAccess.poweroutputmonthycurves.size()>0)
								{
								handler.sendEmptyMessage(0);
								}
								break;	 
							case  AlarmLeverStatic:
								DataAccess.GetAlarmStatics(DataAccess.stationid,year);
 								
								if(DataAccess.alarmstaticsmap.size()>0)
								{
								handler.sendEmptyMessage(0);
								}
								break;	 
						}
	    			} catch (Exception e) {
						// TODO Auto-generated catch bl
	    			}
	    				
			   }
	    	}.start();
	 
	    }
}