
package mgrid.software.software;
import java.util.ArrayList;  
import java.util.List;  
import mgrid.software.software.R;

import mgrid.software.software.protocol.ReportGroupType;
import mgrid.software.software.protocol.ReportType;
  
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;  
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.view.Window;  
import android.widget.AbsListView;  
import android.widget.BaseExpandableListAdapter;  
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;  
 
public class ReportExpandableListActivity extends ExpandableListActivity {  
     
   List<String> group;           //���б�  
   List<List<String>> child;     //���б�  
   ContactsInfoAdapter adapter;  //����������  
   
   /** Called when the activity is first created. */  
  @Override  
  public void onCreate(Bundle savedInstanceState) {  
       super.onCreate(savedInstanceState);  
       setContentView(R.layout.reportexpandablelistactivity);  
       
       initializeData();  
       getExpandableListView().setAdapter(new ContactsInfoAdapter());  
         getExpandableListView().setCacheColorHint(0);  //�����϶��б��ʱ���ֹ���ֺ�ɫ����  
         
        
         OnChildClickListener childListener = new OnChildClickListener() 
         {
    	      public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id)
                {
    	    	  if(groupPosition==0)
    	    	  {
    	    		  switch(childPosition)
    	    		  {
    	    		  
    	    		  case 0:
    	    			  DataAccess.reporttype=ReportType.PowerOutputDaily;
    	    			  break;
    	    		  case 1:
    	    				DataAccess.reporttype=ReportType.PowerOutputMonthy;
    	    			  break;
    	    		  case 2:
    	    				DataAccess.reporttype=ReportType.AlarmLeverStatic;
    	    			  break;
    	    		  
    	    		  }
    	    		 
    	    		  
    	    	  }
    	    	  else
    	    	  {
    	    		  return false;
    	    		  
    	    	  }
    	   
  				   Intent intent= new Intent(ReportExpandableListActivity.this,ReportActivity.class);
  					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
  				    Window w=ReportActivityGroup.reportgroup.getLocalActivityManager().startActivity("�����б�", intent);
  				   View view1=w.getDecorView();
  				   ReportActivityGroup.reportgroup.setContentView(view1); 
  				   ReportActivityGroup.activityindex=2;		
            
    	         return true;
                }
         };
  
         getExpandableListView().setOnChildClickListener(childListener);
       }  
       

      
  /**  
    * ��ʼ���顢���б�����  
     */  
    private void initializeData(){  
       group = new ArrayList<String>();  
       child = new ArrayList<List<String>>();  
         
       addInfo("�걨��",new String[]{"�ź�����(����ͳ��)","�ź�����(����ͳ��)","�澯ͳ��"});  
       addInfo("�±���",new String[]{"�ź�����","�澯ͳ��"});  
       addInfo("�ձ���",new String[]{"�ź�����","�澯ͳ��"});  
           
    }  
     
   /**  
    * ģ����顢���б��������  
    * @param g-group  
     * @param c-child  
    */  
    private void addInfo(String g,String[] c){  
       group.add(g);  
       List<String> childitem = new ArrayList<String>();  
       for(int i=0;i<c.length;i++){  
           childitem.add(c[i]);  
       }  
        child.add(childitem);  
    }  
      
    class ContactsInfoAdapter extends BaseExpandableListAdapter{  
 
          
       //-----------------Child----------------//  

       public Object getChild(int groupPosition, int childPosition) {  
           return child.get(groupPosition).get(childPosition);  
       }  
          
 
       public long getChildId(int groupPosition, int childPosition) {  
           return childPosition;  
       }  
         
  
        public int getChildrenCount(int groupPosition) {  
           return child.get(groupPosition).size();  
        }  
         

      public View getChildView(int groupPosition, int childPosition,  
               boolean isLastChild, View convertView, ViewGroup parent) {  
    	  
    	  LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);    
    	    
    	//View view = inflater.inflate(R.layout.reportlistitem, (ViewGroup)findViewById(R.id.reportlistitem_name));   
    	//((TextView)view).setText( child.get(groupPosition).get(childPosition));
          String string = child.get(groupPosition).get(childPosition);   
          int icon=0;
         
          if(childPosition==0)
          {
           icon=R.drawable.lineicon;
          }
          if(childPosition==1)
          {
           icon=R.drawable.baricon;
          }
          if(childPosition==2)
          {
           icon=R.drawable.pieicon;
          }
         return getChildView1(string,icon);  
    
       }  
          
        //----------------Group----------------//  

        public Object getGroup(int groupPosition) {  
            return group.get(groupPosition);  
        }                 
  
  
       public long getGroupId(int groupPosition) {  
           return groupPosition;  
       }     
          

        public int getGroupCount() {  
            return group.size();  
       }     
          
    
       public View getGroupView(int groupPosition, boolean isExpanded,  
                View convertView, ViewGroup parent) {  
            String string = group.get(groupPosition);    
           return getGroupView(string);  
        }  
 
       //������/����ͼ    
        public TextView getGroupView(String s) {    
            // Layout parameters for the ExpandableListView    
           AbsListView.LayoutParams lp = new AbsListView.LayoutParams(    
                    ViewGroup.LayoutParams.FILL_PARENT, 40);  
   
           TextView text = new TextView(ReportExpandableListActivity.this);    
           text.setLayoutParams(lp);    
           // Center the text vertically    
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);    
            // Set the text starting position    
            text.setPadding(36, 0, 0, 0);    
            text.setTextColor(Color.BLACK); 
            text.setTextSize(18);
            text.setHeight(30);
             text.setText(s);    
           return text;    
       }    
        //������/����ͼ    
        public TextView getChildView(String s) {    
            // Layout parameters for the ExpandableListView    
           AbsListView.LayoutParams lp = new AbsListView.LayoutParams(    
                    ViewGroup.LayoutParams.FILL_PARENT, 40);  
   
            TextView text = new TextView(ReportExpandableListActivity.this);    
            text.setLayoutParams(lp);    
           // Center the text vertically    
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);    
            // Set the text starting position    
            text.setPadding(36, 0, 0, 0);    
            text.setTextColor(Color.BLUE); 
           text.setText(s); 
           text.setHeight(30);
           text.setTextSize(18);
           return text;    
       }     
         
        public View getChildView1(String s,int icon) { 
            //����Ĵ�������getChildView()������ƣ������ظ�˵��  
             
           LayoutInflater inflate=LayoutInflater.from(ReportExpandableListActivity.this);  
           View view=inflate.inflate(R.layout.reportlistitem, null);  //��grouplayout���layout��Ϊ��Ŀ����ͼ 
              
           TextView groupName=(TextView)view.findViewById(R.id.reportlistitem_name);  
           ImageView reporticon=(ImageView)view.findViewById(R.id.reporticon);
           
           reporticon.setImageResource(icon);
           groupName.setText(s);  
          
           return view;  
       }  
        public boolean hasStableIds() {  
           // TODO Auto-generated method stub  
           return false;  
       }         
  
      
        public boolean isChildSelectable(int groupPosition, int childPosition) {  
           // TODO Auto-generated method stub  
           return true;  
       }  
     
       
    }  
}  

