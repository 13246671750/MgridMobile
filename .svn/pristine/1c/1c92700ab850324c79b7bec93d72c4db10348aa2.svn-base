package mgrid.software.software;

import mgrid.software.software.protocol.ReportGroupType;
import mgrid.software.software.protocol.ReportType;
import mgrid.software.software.R;
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

public class ReportListActivity extends Activity {
	
	    SQLiteDatabase sqldb;  
	    public String DB_NAME = "db.sqlite";  
	    public String DB_TABLE = "num";  
	    public int DB_VERSION = 1;  
	    ListAdapter adapter;
	    final DataHelper helper = new DataHelper(this, DB_NAME, null, DB_VERSION);  
		OnItemClickListener itemListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// Intent intent= new Intent(ReportListActivity.this,ReportPoweroutputActivity.class);
				if(DataAccess.reportgrouptype==ReportGroupType.Power)
				{
				   switch(position)
				    {
				    case 0:
				    	DataAccess.reporttype=ReportType.PowerOutputDaily;
				    	break;
				    case 1:
				    	DataAccess.reporttype=ReportType.PowerOutputMonthy;
				       break;			    
				    }
				}
				if(DataAccess.reportgrouptype==ReportGroupType.Alarm)
				{
				   switch(position)
				    {
				    case 0:
				       	DataAccess.reporttype=ReportType.AlarmLeverStatic;
						   	break;
				  	    
				    }
				}
				 Intent intent= new Intent(ReportListActivity.this,ReportActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				  Window w=ReportActivityGroup.reportgroup.getLocalActivityManager().startActivity("报表列表", intent);
				   View view1=w.getDecorView();
				   ReportActivityGroup.reportgroup.setContentView(view1); 
				   ReportActivityGroup.activityindex=2;			
			}
		};
	    // DbHelper类在DbHelper.java文件里面创建的  
	    ListView lv;  
	  
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        // TODO Auto-generated method stub  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.reportlistlayout);  
	    //    sqldb = helper.getWritableDatabase();  
	        lv = (ListView) findViewById(R.id.reportlistlv);  
	        String[] ColumnNames = {"Seq","Name","Icon"};    
	        adapter = new SimpleAdapter(this, DataAccess.uireportlisttitems, 
	               R.layout.reportlistitem,  ColumnNames, new int[] { R.id.reportlistitem_id,  R.id.reportlistitem_name,R.id.reporticon});  
	       lv.setAdapter(adapter);  

	       // updatelistview();  
	     	lv.setOnItemClickListener(itemListener);
			// 设置长按事件
			registerForContextMenu(lv);
			TextView tv=  (TextView) findViewById(R.id.pageheadtitlecenter);  
		  	tv.setText("报表");
	    }  
	
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
