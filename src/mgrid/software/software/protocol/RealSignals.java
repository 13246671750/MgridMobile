package mgrid.software.software.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;
import mgrid.software.software.BitConverter;
import mgrid.software.software.DataAccess;
import mgrid.software.software.SocketService;

public class RealSignals {
    SQLiteDatabase sqldb;  
 	
	   public byte[] RawPacket= new  byte[1024];
       public int length;  
    	public static List<Map<Integer,Float>> m_realSignals= new ArrayList<Map<Integer,Float>>();
    	 
       public void updatedatabase(String name) {  
 	  	  
	        // 
    	   String sql="insert into  stations (Name) values (" +"'"+name+"')";
    	   DataAccess.sqldb.execSQL(sql );
	  
	   
	    }  
       public void updateroomsdatabase(String name) {  
  	  	  
	        // 
   	   String sql="insert into  rooms (Name) values (" +"'"+name+"')";
   	DataAccess.sqldb.execSQL(sql );
	  
	   
	    }  
       public void updateequipmentsdatabase(String name) {  
   	  	  
	        // 
  	   String sql="insert into  equipments (Name) values (" +"'"+name+"')";
  	 DataAccess.sqldb.execSQL(sql );
	  
	   
	    }  
       public void updatesingalsdatabase(String name) {  
   	  	  
	        // 
  	   String sql="insert into  signals (Name) values (" +"'"+name+"')";
  	 DataAccess.sqldb.execSQL(sql );
	  
	   
	    }  
       public void ClearTable(String name) {  
  	  	  
	        //  
    	   DataAccess.sqldb.execSQL("delete from "+ name );
	  
	   
	    }  
       public RealSignals( )
       {
    	   

       }

       public void parse()
       {
    	   m_realSignals.clear();
    	   int index =8;
    	   int totaldata=0;
    	   String name;
     	   totaldata = BitConverter.getInt(RawPacket, 4) ;
              for(int i=0;i<totaldata;i++)
           	   {
           		 int id=BitConverter.getInt(RawPacket, index);
           		 index =index +4;
           		 float value=BitConverter.getFloat(RawPacket, index);
           		 index =index +4;           			
           	//	 updateroomsdatabase(name);
           		 Map<String,Object>map = new  HashMap<String,Object>();
           		((Map<Integer,Float>) m_realSignals).put(id, value);         	
           	//	 SocketService.stationslisttitems.add(map);		   	   
           	   }
             return;

       }
       
       public void Packet(ConfigType type)
       {
           switch (type)
           { 
               case Stations:
                   PackStationsCfg();
                   break;
               case EpvArrays:
                   break;

               case Equipments:
                   break;

               case Signals:
                   break;               
           }
       
       }
       
       public void PackStationsCfg()
       {
        /*   int index = 0;
           int messagetype = (int)MessageType.ConfigRequest;
         
           Array.Copy(BitConverter.GetBytes(messagetype), 0, RawPacket, index, 4);
            index = index + 4;
            length = length + 4;
           foreach (ECfgStation station in m_center.m_stations.Values)
           {
               byte[] tempData = Encoding.UTF8.GetBytes(station.m_Name);
               int length1 = tempData.Length;
               Array.Copy(tempData, 0, RawPacket, index, length1);
               length = length + length1;
           }
           */
           
       }
}
