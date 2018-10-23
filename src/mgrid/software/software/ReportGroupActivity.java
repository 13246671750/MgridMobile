package mgrid.software.software;

import java.util.HashMap;
import mgrid.software.software.R;

import mgrid.software.software.protocol.ReportGroupType;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ReportGroupActivity extends Activity {
    SQLiteDatabase sqldb;  
    public String DB_NAME = "db.sqlite";  
    public String DB_TABLE = "num";  
    public int DB_VERSION = 1;  
    ListAdapter adapter;
    final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);  
	OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			    DataAccess.reportgroupid=position;
			    switch(position)
			    {
			    case 0:
			    	DataAccess.reportgrouptype=ReportGroupType.Power;
			    	break;
			    case 1:
			    	DataAccess.reportgrouptype=ReportGroupType.Alarm;
			    	break;			    
			    }
			    InitReportList(position);
			    Intent intent= new Intent(ReportGroupActivity.this,ReportListActivity.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    Window w=ReportActivityGroup.reportgroup.getLocalActivityManager().startActivity("报表", intent);
			    View view1=w.getDecorView();
			    ReportActivityGroup.reportgroup.setContentView(view1);    
			    ReportActivityGroup.activityindex=1;			
		}
	};
    // DbHelper类在DbHelper.java文件里面创建的  
    ListView lv;  
    public void Init()
    {
    	DataAccess.uireportgrouplisttitems.clear();
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("Seq", 0);
    	map.put("Name", "信号报表");
    	DataAccess.uireportgrouplisttitems.add(map);
    	
    	map = new HashMap<String,Object>();
    	map.put("Seq", 1);
    	map.put("Name", "告警报表");
    	DataAccess.uireportgrouplisttitems.add(map);
    	
    }
    public void InitReportList(int type)
    {
    	DataAccess.uireportlisttitems.clear();
    	if(type==0)
    	{
    		String[] reports= new String[]{"日报表","月报表"};
    		int[] icons= new int[]{R.drawable.lineicon,R.drawable.baricon};
        	
    		for(int i=0;i<reports.length;i++)
    		{
    			HashMap<String,Object> map = new HashMap<String,Object>();
    			map.put("Seq", i);
    			map.put("Name", reports[i]);
    			map.put("Icon", icons[i]);
    			DataAccess.uireportlisttitems.add(map); 	
    	
    		}
    	}
    	if(type==1)
    	{
    		String[] reports= new String[]{"告警统计报表"};
    		int[] icons= new int[]{R.drawable.pieicon};
            
    		for(int i=0;i<reports.length;i++)
    		{
    			HashMap<String,Object> map = new HashMap<String,Object>();
    			map.put("Seq", i);
    			map.put("Name", reports[i]);
    			map.put("Icon", icons[i]);
    			DataAccess.uireportlisttitems.add(map); 	  	
    		}
    		
    	}
   
    	
    }
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        Init();
        setContentView(R.layout.reportgroupslayout);  
        sqldb = helper.getWritableDatabase();  
        lv = (ListView) findViewById(R.id.reportgrouplv);  
        
        String[] ColumnNames = {"Seq","Name"};    
        adapter = new SimpleAdapter(this, DataAccess.uireportgrouplisttitems, 
               R.layout.reportgouplistitem,  ColumnNames, new int[] { R.id.report_group_list__id,   R.id.report_group_list_Name});  
       lv.setAdapter(adapter);  
        
    //    updatelistview();  
     	lv.setOnItemClickListener(itemListener);
		// 设置长按事件
		registerForContextMenu(lv);
		DataAccess.activities.add(this);
		TextView tv=  (TextView) findViewById(R.id.pageheadtitlecenter);  
	  	tv.setText("报表 ");
	     
    }  
/*    public void updatelistview() {  	  
        //  
        Cursor cr = sqldb.query("reportgroups", null, null, null, null, null,null);  
  
        String id = cr.getColumnName(0);// 获取第1列  
        String Name = cr.getColumnName(1);// 获取第3列  
        
        String[] ColumnNames = { id,Name};  
        ListAdapter adapter = new SimpleCursorAdapter(this,  
                R.layout.reportgouplistitem, cr, ColumnNames, new int[] { R.id.report_group_list__id, R.id.report_group_list_Name, });  
        lv.setAdapter(adapter);  
       
  
    }  
    */


	
    // 更新listview  

  
    @Override  
    protected void onDestroy() {// 关闭数据库  
        // TODO Auto-generated method stub  
        super.onDestroy();  
        if (helper != null) {  
            helper.close();  
        }  
    } 
    

}
