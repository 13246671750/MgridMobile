package mgrid.software.software;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class RealtimeActivityGroup extends ActivityGroup {
	 public static ActivityGroup rtgroup;
	 public static int activityindex=0;
 
	 public void onCreate(Bundle savedInstanceState) {  
		       super.onCreate(savedInstanceState); 
		       rtgroup= this;
		     }
	  @Override
	       public boolean dispatchKeyEvent(KeyEvent event) {
	            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
	                	
	                 Intent intent;
	  		    	   Window w;
	  		    	   View view1;
	  		    	    switch(RealtimeActivityGroup.activityindex)
	  		    	    { 
	  		    	    case 0:
	  		    	    	this.onBackPressed();
	   					 break;
	  		    	    case 1:	  		    	    	
	  		    	    	 intent = new Intent(this,  StationViewPagerActivity.class);  
	  			    		 intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
	  					     w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站", intent);
	  						 view1=w.getDecorView();
	  						 RealtimeActivityGroup.rtgroup.setContentView(view1);   
	  						 RealtimeActivityGroup.activityindex=0;		
	  						 break;  	  			
	  		    	    case 2:	  		    	    	
	  		    	    	 intent = new Intent(this,  RoomsActivity.class);  
	  			    		 intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
	  						 w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
	  						 view1=w.getDecorView();
	  						 RealtimeActivityGroup.rtgroup.setContentView(view1);   
	  						 RealtimeActivityGroup.activityindex=1;		
	  						 break;
	  		    	    case 3:
	  		    	    	 intent = new Intent(this,  EquipmentsActivity.class);  
	  			    		 intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
	  						 w=RealtimeActivityGroup.rtgroup.getLocalActivityManager().startActivity("电站列表", intent);
	  						 view1=w.getDecorView();
	  						 RealtimeActivityGroup.rtgroup.setContentView(view1);   
	  						 RealtimeActivityGroup.activityindex=2;		
	  						 break;
	  		    	    }
	  		    	    return true;
	                }
	                return super.dispatchKeyEvent(event);
	        }
		
		      @Override
		     protected void onResume()
		      {
		    	   
		   	   super.onResume();
		    	   Intent intent =null;  
			       intent = new Intent(this,  StationViewPagerActivity.class);  
			       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			       Window w = getLocalActivityManager().startActivity(  
	        	            "StationViewPagerActivity", intent); 
			       View view=w.getDecorView();
			       rtgroup.setContentView(view); 
			       RealtimeActivityGroup.activityindex=1;
					
			        
		     }
		      
		      @Override
			    public void onBackPressed() {
			    	 
					   AlertDialog.Builder builder = new Builder(RealtimeActivityGroup.this);
					   builder.setTitle("MainTabActivity提示");
					   builder.setMessage("你真的要退出手机监控系统吗？");
					   Builder setPositiveButton = builder.setPositiveButton("是",
					     new android.content.DialogInterface.OnClickListener() {
					   

						public void onClick(DialogInterface dialog, int which) {
							  //Intent i = new Intent(Intent.ACTION_MAIN);
								 
						      //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						 
						      //i.addCategory(Intent.CATEGORY_HOME);
						 
						      //startActivity(i);
						      
						      System.exit(0);
						}
					     });
					   builder.setNegativeButton("否",
					     new android.content.DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					       dialog.dismiss();
					      }
					     });
					   builder.create().show();
					 
					 }

		  }  
	 
