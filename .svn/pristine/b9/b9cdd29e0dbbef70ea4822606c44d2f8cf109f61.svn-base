package mgrid.software.software;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mgrid.software.software.R;

import mgrid.software.software.protocol.AliveEvents;
import mgrid.software.software.protocol.ConfigType;
import mgrid.software.software.protocol.HistorySignal;
import mgrid.software.software.protocol.RealtimeSignal;


import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class SocketService extends Service implements Runnable {	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		//Log.i(TAG,"socket service  onCreate ----");
	  
		super.onCreate();
	}
	public void onStart(Intent intent,int startId)
	{
		
		//Log.i(TAG,"socket service  onStart ----");
		
	    new Thread(SocketService.this).start();
		
		super.onStart(intent, startId);
		
	}
	public void onDestroy()
	{
		//Log.i(TAG,"socket service onDestroy ----");
		
		super.onDestroy();
	}
    public void run()
    {
      	while(true)
    	{
        			 try {
  
        				 	Thread.sleep(1000);
        			    	// GetRooms();
        				 	Thread.sleep(1000);
        				    //GetEquipments();
        				 	Thread.sleep(1000);
        				    //GetSignals();
     					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							 		
    	}

     }  
  
}
	
