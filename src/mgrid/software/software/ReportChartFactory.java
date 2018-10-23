package mgrid.software.software;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import mgrid.software.software.R;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;

public class ReportChartFactory {  
	
	
    private double[] value = {22,22,22};
	private int CHART_MARGINS_LEFT=20;
	private int CHART_MARGINS_TOP=30;
	private int CHART_MARGINS_BOTTOM=20;
	private int CHART_MARGINS_RIGHT=30;    

    public ReportChartFactory(){
        value[0]= 1;
        value[1]=2;
        value[2] = 3;
    }
    
    public ReportChartFactory(double values[]){
        for (int i=0;i<values.length;i++){
            value[i] = values[i];
        }
    }
    

    public View GetPieChartView (Context context,HashMap<String,Integer> map) { 
        int[] colors = new int[] {Color.RED,Color.MAGENTA, Color.YELLOW,Color.GREEN  }; 
        DefaultRenderer renderer = buildPieRenderer(colors); 
        CategorySeries categorySeries = new CategorySeries("饼图");           
		categorySeries.add("致命("+ map.get("致命").toString()+")", (double)map.get("致命"));
		categorySeries.add("严重("+ map.get("严重").toString()+")", (double)map.get("严重"));
		categorySeries.add("一般("+ map.get("一般").toString()+")", (double)map.get("一般"));
		categorySeries.add("通知("+ map.get("通知").toString()+")", (double)map.get("通知"));
		return ChartFactory.getPieChartView(context, categorySeries, renderer); 
		
		
			
    } 
   
    
    public View GetBarChartView(Context context) { 
        int[] colors = new int[] { Color.RED}; 
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors); 
   
        XYMultipleSeriesDataset dataset = buildBarDataset(); 
      return ChartFactory.getBarChartView(context, dataset, renderer,Type.DEFAULT);
    } 
   
    private XYMultipleSeriesDataset buildBarDataset() { 	
    
   	  XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
   	  
          for ( int i = 0; i < DataAccess.poweroutputmonthycurves.size() ; i++) 
          {
          //   CategorySeries series = new CategorySeries("年度月发电量");
        	   XYSeries series=new XYSeries("年度月信号量");
              for ( int k = 0; k < DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.size(); k++)
              {
                // series.add( DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value); 
                 
            	  series.add(k+1,DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value);
               
               }
            dataset.addSeries(series);
         } 
          return dataset;
  
    
      }
    public double GetMaxValue()
    {
    	 double max=0;
    	  for ( int i = 0; i < DataAccess.poweroutputmonthycurves.size() ; i++) 
          {
               for ( int k = 0; k < DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.size(); k++) {
               if(DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value>max)
               {
            	   
            	   max=DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value;
               }
              
            }
          }
    	  return max;

    	
    }
    public double GetMinValue()
    {
    	 double min=0;
    	try{
    	   min=DataAccess.poweroutputmonthycurves.get(0).poweroutputmonthy.get(0).value;
    	  for ( int i = 0; i < DataAccess.poweroutputmonthycurves.size() ; i++) 
          {
               for ( int k = 0; k < DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.size(); k++) {
               if(DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value<min)
               {
            	   
            	   min=DataAccess.poweroutputmonthycurves.get(i).poweroutputmonthy.get(k).value;
               }
              
            }
          }
    	}
    	catch(Exception e)
    	{
    		
    		return 0;
    	}
    	  return min;

    	
    }
    private XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    	
    	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    	for(int i=0;i<colors.length;i++)
    	{
    		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    		r.setColor(colors[i]);
    		renderer.addSeriesRenderer(r);
    	}
  //  	renderer.setLegendHeight(CHART_MARGINS_RIGHT);
    	renderer.setChartTitle( "年度月发电量" );  	 
        renderer.setXTitle( "发电月份" );
        renderer.setYTitle( "发电量(kWh)");
        
  
    
        renderer.setAxisTitleTextSize(20);  
        renderer.setChartTitleTextSize(24);  
     
        renderer.setLegendTextSize(18);  
        renderer.setShowGrid(true);
        renderer.setXLabels(0);  
       
        renderer.setBarSpacing(0.5);  
        renderer.setChartValuesTextSize(18);
        
     
      

     // 坐标轴颜色 
        renderer.setAxisTitleTextSize(20);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0,Color.BLACK);
      
        // 设置坐标刻度
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(12);
        renderer.setYAxisMin(GetMinValue()*0.8);
        renderer.setYAxisMax(GetMaxValue()*1.1);
        //设置刻度的字体颜色
       renderer.setYLabelsAlign(Align.LEFT);
       renderer.setXLabelsAlign(Align.CENTER);
     // renderer.setLabelsColor(Color.rgb(25, 110, 172)); 
        renderer.setLabelsTextSize(16);
    
    //    renderer.setDisplayChartValues(true);  
        renderer.addTextLabel(1, "1");
        renderer.addTextLabel(2, "2");  
        renderer.addTextLabel(3, "3");  
        renderer.addTextLabel(4, "4");  
        renderer.addTextLabel(5, "5");  
        renderer.addTextLabel(6, "6");  
        renderer.addTextLabel(7, "7");  
        renderer.addTextLabel(8, "8");  
        renderer.addTextLabel(9, "9");  
        renderer.addTextLabel(10, "10"); 
        renderer.addTextLabel(11, "11");  
        renderer.addTextLabel(12, "12"); 
    //    renderer.setLabelsColor(Color.BLUE);
      //  renderer.setBackgroundColor(Color.BLUE);
   //     renderer.setApplyBackgroundColor(t);
   //   renderer.setAxesColor(Color.BLUE);
    //  renderer.setApplyBackgroundColor(true);
    //    renderer.setMargins(new int[]{0,0,100,100});
      renderer.setMarginsColor(0x9000);

       return renderer;

	}

	public Intent GetColumnChart (Context context) { 
        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE }; 
        DefaultRenderer renderer = buildPieRenderer(colors); 
        CategorySeries categorySeries = new CategorySeries("Vehicles Chart"); 
        categorySeries.add("cars ", value[0]); 
        categorySeries.add("trucks", value[1]); 
        categorySeries.add("bikes ", value[2]); 
        return ChartFactory.getPieChartIntent(context, categorySeries, renderer,"jialia"); 
    } 
    
    public Intent GetLineChartIntent  (Context context) { 
        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE }; 
        DefaultRenderer renderer = buildPieRenderer(colors); 
        CategorySeries categorySeries = new CategorySeries("Vehicles Chart"); 
        categorySeries.add("cars ", value[0]); 
        categorySeries.add("trucks", value[1]); 
        categorySeries.add("bikes ", value[2]); 
        return ChartFactory.getPieChartIntent(context, categorySeries, renderer,"jialia"); 
    } 
    public View GetLineChartView(Context context, LineCharProp[] lines ) { 
         String[] titles = new String[lines.length];// { "艾默生逆变器", "阳光逆变器"};
         int[] colors = new int[lines.length]; 
        PointStyle[] styles = new PointStyle[lines.length]; // { Color.BLUE, Color.GREEN}; 
        List x = new ArrayList(); 
        List y = new ArrayList();    
        for(int i=0;i<lines.length;i++)
        {     	
        	titles[i]=lines[i].titles;
        	colors[i]=lines[i].color;
        	styles[i]=lines[i].styles;  
        	double[] xaix= new double[DataAccess.poweroutputdaily.size()];
        	double[] yaix= new double[DataAccess.poweroutputdaily.size()];
            
        	for(int j=0;j<DataAccess.poweroutputdaily.size();j++)
        	{
        		xaix[j]=DataAccess.poweroutputdaily.get(j).secondtime;
        	    yaix[j]=DataAccess.poweroutputdaily.get(j).value;
        	  
        		
        	}
        	lines[i].xdouble=xaix;
        	lines[i].ydouble=yaix;
        	x.add(lines[i].xdouble);
        	y.add(lines[i].ydouble);
        }         
         XYMultipleSeriesDataset dataset = buildDataset(titles, x, y);           
        XYMultipleSeriesRenderer renderer = buildRenderer(colors);
        double[] ymaxmin=GetLineChartYMaxMin();
        double xmin= 0;
        double xmax=380;
        
        double ymin=ymaxmin[1]*0.9;
        double ymax= ymaxmin[0]*1.1;
        
        setChartSettings(renderer, "发电量曲线", "发电日期", "发电量/kwh",0,DataAccess.poweroutputdaily.size(), ymin,ymax, Color.BLACK, Color.BLACK);
        View chart = ChartFactory.getLineChartView(context, dataset, renderer);
        return chart;    
    } 
    private double[] GetLineChartYMaxMin()
    {
    	double maxmin[] = new double[2];
    	double max =0;
    	double min=0;
    	min=DataAccess.poweroutputdaily.get(0).value;
    	for(int i=0;i<DataAccess.poweroutputdaily.size();i++)
    	{
    	
    		if(DataAccess.poweroutputdaily.get(i).value>max)
    		{  			
    			max =DataAccess.poweroutputdaily.get(i).value;
    		}
    		if(DataAccess.poweroutputdaily.get(i).value<min)
    		{
    			
    			min=DataAccess.poweroutputdaily.get(i).value;
    		}
    		
    	}
    	 maxmin[0]=max;
    	 maxmin[1]=min;
    	return maxmin;
    	
    }
    private double[] GetLineChartXMaxMin()
    {
    	double maxmin[] = new double[2];
    	double max =0;
    	double min=0;
    	min=DataAccess.poweroutputdaily.get(0).value;
    	for(int i=0;i<DataAccess.poweroutputdaily.size();i++)
    	{
    	
    		if(DataAccess.poweroutputdaily.get(i).value>max)
    		{  			
    			max =DataAccess.poweroutputdaily.get(i).value;
    		}
    		if(DataAccess.poweroutputdaily.get(i).value<min)
    		{
    			
    			min=DataAccess.poweroutputdaily.get(i).value;
    		}
    		
    	}
    	 maxmin[0]=max;
    	 maxmin[1]=min;
    	return maxmin;
    	
    }
    protected XYMultipleSeriesRenderer buildRenderer(int[] colors) 
    { 
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); 
        int length = colors.length; 
        for (int i = 0; i < length; i++) 
        { 
            XYSeriesRenderer r = new XYSeriesRenderer(); 
            r.setColor(colors[i]); 
            r.setPointStyle(PointStyle.CIRCLE); 
            r.setFillPoints(true); 
            r.setLineWidth(3);
            renderer.addSeriesRenderer(r); 
        } 
        
        renderer.setMargins(new int[] { CHART_MARGINS_TOP, CHART_MARGINS_LEFT, CHART_MARGINS_BOTTOM, CHART_MARGINS_RIGHT });
      
        // 设置坐标轴标题文本大小
         renderer.setChartTitleTextSize(20);
     
    
          // 设置轴标签文本大小
         renderer.setLegendTextSize(20);
       //  renderer.setShowGrid(true);
      //   renderer.setGridColor(Color.GREEN);
      // 坐标轴颜色 
         renderer.setAxisTitleTextSize(20);
         renderer.setAxesColor(Color.rgb(242, 103, 16));
       
      // 坐标轴单位文字颜色、字号 
         // 坐标轴文字对齐  
     //    renderer.setYLabelsAlign(Paint.Align.RIGHT);
         renderer.setLabelsColor(Color.rgb(25, 110, 172)); 
         renderer.setLabelsTextSize(16);
         renderer.setMargins(new int[] {40, 60, 15,0});
         renderer.setMarginsColor(Color.argb(0,0xff,0,0));
       //设置原点大小  
         renderer.setPointSize(3);
         
         //设置X时间坐标
         renderer.setShowGrid(true);
         renderer.setXLabels(0);  
         renderer.setYLabelsAlign(Align.LEFT);
         renderer.setXLabelsAlign(Align.RIGHT);
         renderer.setXLabelsColor(Color.BLACK);
         renderer.setAxesColor(Color.BLACK);
         renderer.setLabelsColor(Color.BLACK);
         renderer.setXLabelsColor(Color.BLACK);
         renderer.setYLabelsColor(0,Color.BLACK);
        
         renderer.addTextLabel(0, "1");
         renderer.addTextLabel(30, "2");  
         renderer.addTextLabel(60, "3");  
         renderer.addTextLabel(90, "4");  
         renderer.addTextLabel(120, "5");  
         renderer.addTextLabel(150, "6");  
         renderer.addTextLabel(180, "7");  
         renderer.addTextLabel(210, "8");  
         renderer.addTextLabel(240, "9");  
         renderer.addTextLabel(270, "10"); 
         renderer.addTextLabel(300, "11");  
         renderer.addTextLabel(330, "12"); 
         
   
         if(DataAccess.poweroutputdaily.size()<1)
         {
        	 
        	 return renderer; 
        	 
         }
     /*    int monthy= DataAccess.poweroutputdaily.get(0).sampletime.getMonth();
         for(int i=0;i<DataAccess.poweroutputdaily.size();i++)
         {
        	 if(monthy!=DataAccess.poweroutputdaily.get(i).sampletime.getMonth())
        	 {
        		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        		 String strdate=format.format(DataAccess.poweroutputdaily.get(i).sampletime);
        	
        		 renderer.addTextLabel(i+1,strdate);
        		 monthy=DataAccess.poweroutputdaily.get(i).sampletime.getMonth();
        	 }
        	 else
        	 {
        	 renderer.addTextLabel(i+1,"");
        	 }
         }
         */
         return renderer; 
    }

   protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, 
                                String xTitle,String yTitle, double xMin, 
                                double xMax, double yMin, double yMax, 
                                int axesColor,int labelsColor) 
    { 
        renderer.setChartTitle(title); 
        renderer.setXTitle(xTitle); 
       
        renderer.setYTitle(yTitle); 
        renderer.setXAxisMin(0); 
        renderer.setXAxisMax(366); 
        renderer.setYAxisMin(yMin); 
        renderer.setYAxisMax(yMax); 
        renderer.setAxesColor(axesColor); 
        renderer.setLabelsColor(labelsColor); 
    } 
    protected XYMultipleSeriesDataset buildDataset(String[] titles,  List xValues, List yValues) 
    { 
    	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

    	int length = titles.length;                  //有几条线 
    	for (int i = 0; i < length; i++) 
    	{ 
    		XYSeries series = new XYSeries(titles[i]);    //根据每条线的名称创建 
    		double[] xV = (double[]) xValues.get(i);                 //获取第i条线的数据 
    		double[] yV = (double[]) yValues.get(i); 
    		int seriesLength = xV.length;                 //有几个点

    		for (int k = 0; k < seriesLength; k++)        //每条线里有几个点 
    		{ 
    			series.add(xV[k], yV[k]); 
    			
    		}

    		dataset.addSeries(series); 
    	}

    	return dataset; 
    }
    protected DefaultRenderer buildPieRenderer(int[] colors) { 
        DefaultRenderer renderer = new DefaultRenderer(); 
       
     //   renderer.setApplyBackgroundColor(true);
        renderer.setLabelsTextSize(18);
        renderer.setChartTitle("告警统计报表");
        renderer.setChartTitleTextSize(24);
        renderer.setLegendTextSize(20);
        renderer.setLabelsColor(Color.BLACK);
     //   renderer.setBackgroundColor(0x77ffffff);
        
        //renderer.setBackgroundColor(Color.BLACK);
 
       renderer.setLegendHeight(100);
        for (int i=0;i<colors.length;i++ )
        { 
            SimpleSeriesRenderer r = new SimpleSeriesRenderer(); 
            r.setColor(colors[i]); 
            renderer.addSeriesRenderer(r); 
        } 
        return renderer; 
    } 
}