package mgrid.software.software;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgrid.software.software.protocol.AliveEvents;
import mgrid.software.software.protocol.HistorySignal;
import mgrid.software.software.protocol.PacketParse;
import mgrid.software.software.protocol.RealtimeSignal;
import mgrid.software.software.protocol.ReportGroupType;
import mgrid.software.software.protocol.ReportType;
import mgrid.software.software.protocol.RequestEntity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressLint("UseSparseArrays") public class DataAccess {
	public static  Boolean networkstatus=false; 
	public static SQLiteDatabase sqldb;  
	public static Context  context;  
	public static String usrName="";
	
	
	 public static List<Activity> activities = new ArrayList<Activity>(); 
	 public static int clientid=-1;
	 public static boolean inLoginactivetiy=false;
	 public static boolean bExit=false;
	 public static int stationid=1;
	 public static int parentId;
	 public static String stationname="";
	 public static int roomid=0;
	 public static int totalalarm=0;
	 public static int equipmentid=0;
	 public static int reportgroupid=0;
	 public static TabHost tabhost;
	 public static double[] stationoverdata=new double[]{99.34,343.34,3434.33,343.234};
	 public static String[] weatherstationdata=new String[]{"--","--","--","--"};
	 public static int nConnectedErrorNum=0;
	 public static int nErrorCode=0;
	 public static String stationsname="";
	 public static String equipmentname="";
	 public static String roomname="";
	 public static ReportType reporttype;
	 public static ReportGroupType reportgrouptype;
	 public static HashMap<String,Integer>  alarmstaticsmap= new HashMap<String,Integer>();
	 public static ArrayList<HistData>  poweroutputdaily= new ArrayList<HistData>();	
	 public static  ArrayList<HistDataCurve> poweroutputmonthycurves=new ArrayList<HistDataCurve>();	 

   	 public static ArrayList<HashMap<String,Object>> stationslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> stationweather= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> stationoverview= new ArrayList<HashMap<String,Object>>();
  	
 	 public static ArrayList<HashMap<String,Object>> roomslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> equipmentslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> signalslisttitems= new ArrayList<HashMap<String,Object>>(); 
 	 public static ArrayList<HashMap<String,Object>> aliveevents= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> equipmentanalogsignalsitems= new ArrayList<HashMap<String,Object>>();  	
 	 public static ArrayList<HashMap<String,Object>> equipmentswitchsignalsitems= new ArrayList<HashMap<String,Object>>();	
 	 public static ArrayList<HashMap<String,Object>> equipmentalarmitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> equipmentcommanditems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> equipmentcommandsenddata= new ArrayList<HashMap<String,Object>>();
 	
 
 	 
   	 public static ArrayList<HashMap<String,Object>> uistationslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> uiroomslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> uiequipmentslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> uisignalslisttitems= new ArrayList<HashMap<String,Object>>();
 	 public static ArrayList<HashMap<String,Object>> uiequipmentanalogsignalsitems= new ArrayList<HashMap<String,Object>>();  	
 	 public static ArrayList<HashMap<String,Object>> uiequipmentswitchsignalsitems= new ArrayList<HashMap<String,Object>>();	
 	 public static ArrayList<HashMap<String,Object>> uiequipmentalarmitems= new ArrayList<HashMap<String,Object>>();
 	 
 	 public static ArrayList<HashMap<String,Object>> uialiveevents= new ArrayList<HashMap<String,Object>>();
	 public static ArrayList<HashMap<String,Object>> uistationweather= new ArrayList<HashMap<String,Object>>();
	 public static ArrayList<HashMap<String,Object>> uistationoverview= new ArrayList<HashMap<String,Object>>();


	 public static ArrayList<HashMap<String,Object>> uireportgrouplisttitems= new ArrayList<HashMap<String,Object>>();
	 public static ArrayList<HashMap<String,Object>> uireportlisttitems= new ArrayList<HashMap<String,Object>>();

	 public static String[] customdisplay= new  String[]{"输出功率(kW)","日发电量(kWh)","总发电量(kWh)","CO2减排(Kg)","输出功率(kW)","日发电量(kWh)","总发电量(kWh)","CO2减排(Kg)"};


	// public static List<Map<Integer,AliveEvents>> aliveevents= new ArrayList<Map<Integer,AliveEvents>>();
	 public static List<Map<Integer,Float>> m_realSignals= new ArrayList<Map<Integer,Float>>();
	
	
	public static List<HistorySignal> m_historysignal= new ArrayList<HistorySignal>();
	public static Map<Integer,RealtimeSignal> m_realSignalAnalog= new HashMap<Integer,RealtimeSignal>();
	public static List<RealtimeSignal> m_realSignalSwitch= new ArrayList<RealtimeSignal>();
	public static List<AliveEvents> m_realtimeEvents= new ArrayList<AliveEvents>();
	

	public static ArrayList<HashMap<String,Object>> stations= new ArrayList<HashMap<String,Object>>();
	public static ArrayList<HashMap<String,Object>> parents = new ArrayList<HashMap<String,Object>>();
	public static HashMap<Integer,ArrayList<HashMap<String,Object>>> parentMap = new HashMap<>();
	
	
	public static void  DeepCopy(List<Map<String,Object>> source,List<Map<String,Object>> des)
	{
		des.clear();
		for(int i=0;i<source.size();i++)
		{
			
		//	Map map= new HashMap();
		//	map.
			
		}
	
		
		return ;
	}
	public static List<HashMap<String,Object>> GetRealtimeSignalAnalog()
	{
		 signalslisttitems.clear();
	     for(int i=0;i<m_realSignalAnalog.size();i++)
	     {
	    	 HashMap<String,Object> map = new HashMap<String,Object>();
	      	 map.put("Seq", i);	    	 
	    	 map.put("Name", ((RealtimeSignal)DataAccess.m_realSignalAnalog.get(i)).m_name);
	    	 map.put("Value",( (RealtimeSignal)DataAccess.m_realSignalAnalog.get(i)).m_fvalue);
	    	 map.put("Unit",( (RealtimeSignal)DataAccess.m_realSignalAnalog.get(i)).m_unit);
	    	 signalslisttitems.add(map);
	     }
		
		 return signalslisttitems;
		
	}

//	private static final String TAG="TEST";
  /*  public static void GetCustomCfg()
    {

    	
    	MobilePacket mobilepacket = new MobilePacket();
    	mobilepacket.GetCustomCfgPacket();
    	byte buffer [] =new byte[4*1024];
    	try {
			SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
    
    	
    	MobilePacket receive = new MobilePacket();
    	receive.RawPacket=buffer;
    	//receive.length=temp;
    	receive.ParseReceivePacket();
		
    }
    */
	 public static int GetClientId(String username,String password) throws Exception
	    {	    	
		 	byte[] receive=null;
	    	 
		 	int logRet = -1;
	    	try {
				 receive=SendAndReceive(RequestEntity.GetClientId(username,password));
			} catch (Exception e) {
	 
				throw new Exception(e.getMessage().toString());			
			}
	    	
	        if(receive!=null)
	        {
	        	DataAccess.clientid=BitConverter.getInt(receive,5);
	        	logRet = BitConverter.getInt(receive, 23);
	        	
	        	if(logRet == 0)
	        	{
	        		return -2;
	        	}
	        	
	        	return DataAccess.clientid;
	        }
	        else
	        {
	        	return -1;
	        }      	
	    }
	 
	    public static void GetStations(int clientid)
	    {
	
	    	 byte[] receive=null;
	    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.GetStations(clientid));
				} catch (Exception e) {
		 
					//throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	if(receive!=null)
		        {
		    		PacketParse.parseStationData(receive);
		        }			
	    }

	  /*  public static void GetRooms(int stationid)
	    {
	    	
	    	MobilePacket mobilepacket = new MobilePacket();
	    	mobilepacket.GetConfigPacket(ConfigType.EpvArrays,stationid,-1,-1);
	    	byte buffer [] =new byte[4*1024];
	    	try {
				SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	  
	    
	    	MobilePacket receive = new MobilePacket();
	    	receive.RawPacket=buffer;
	    	//receive.length=temp;
	    	receive.ParseReceivePacket();
			
	    }
	    */
	    public static void GetRoomsWithOverViewData(int stationid)
	    {
	    	
	    	 byte[] receive=null;
	    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.getRoomsWithOverViewDataBytes(DataAccess.clientid,stationid));
//					 StringBuffer sb=new StringBuffer();
//					 for (int i = 0; i < receive.length; i++) {
//						 sb.append(receive[i]);
//					 }
//					 System.out.println("我的数据："+sb.toString());
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseRoomsWithOverViewData(receive);
			        }
		    	  
		    	  byte[] receive2=null;
			    	 
			    	try {
			    		receive2=SendAndReceive(RequestEntity.GetStations(DataAccess.clientid));
					} catch (Exception e) {
			 
					//	throw new Exception(e.getMessage().toString());			
					}	  
		    
			    	  if(receive2!=null)
				        {
				    	  PacketParse.parseStationData(receive2);
				        }
			
	    }
	    public static void GetEquipmentsWithOverViewData(int stationid,int roomid)
	    {
	    	 byte[] receive=null;
	    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.getEquipmentsWithOverViewDataBytes(DataAccess.clientid,stationid,roomid));
						StringBuffer sb=new StringBuffer();
						for (int i = 0; i < receive.length; i++) {
							sb.append(receive[i]);
						}
						//System.out.println("收到数据："+sb.toString());
					 
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseEquipmentsWithOverViewData(receive);
			        }
			
	    }
	    public static void GetStationWeather(int stationid)
	    {
	    
	    	 byte[] receive=null;
	    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.getStationWeatherBytes (DataAccess.clientid,stationid));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseStationWeatherSignals(receive);
			        }
			 
	    }
	    public static void Init()
	    {
	    	 DataAccess.stationoverview.clear();
	    	  DataAccess.stationweather.clear();
	    	  HashMap<String,Object> map;
	 		  map= new  HashMap<String,Object>();
	 		  map.put("Id",1);
		      map.put("Name","总发电量(kWh)");
		      map.put("Icon",R.drawable.powermeter1);
		      map.put("Value","--");
		      DataAccess.stationoverview.add(map);
		      map= new  HashMap<String,Object>();
		      map.put("Id",2);
		      map.put("Name","日发电量(kWh)");
		      map.put("Icon",R.drawable.wattmeter);
		      map.put("Value","--");
		      DataAccess.stationoverview.add(map);
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",3);
		      map.put("Name","输出功率(kW)");
		      map.put("Icon",R.drawable.powermeter2);
		      map.put("Value","--");
		      DataAccess.stationoverview.add(map);
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",4);
		      map.put("Name","节能减排(Kg)");
		      map.put("Icon",R.drawable.padjienengjianpai);
		      map.put("Value","--");
		      DataAccess.stationoverview.add(map);
		      
		      
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",1);
		      map.put("Name","光照强度(lux)");
		      map.put("Icon",R.drawable.padguanzhaoqiangdu);
		      map.put("Value","--");
		      DataAccess.stationweather.add(map);
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",2);
		      map.put("Name","温度(°C)");
		      map.put("Icon",R.drawable.padweather474_278);
		      map.put("Value","--");
		      DataAccess.stationweather.add(map);
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",3);
		      map.put("Name","风向");
		      map.put("Icon",R.drawable.padfengxiang);
		      map.put("Value","--");
		      DataAccess.stationweather.add(map);
		      
		      map= new  HashMap<String,Object>();
		      map.put("Id",4);
		      map.put("Name","风速(米/秒)");
		      map.put("Icon",R.drawable.padfengshu);
		      map.put("Value","--");
		      DataAccess.stationweather.add(map);
		      DataAccess.bExit=false;
		      DataAccess.inLoginactivetiy=true;
		      
	 
	    	
	    }
	    public static void GetStationOverView(int stationid)
	    {
	    	
	    	 byte[] receive=null;
	    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.getStationOverViewDataBytes(DataAccess.clientid,stationid));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	               
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseStationOverViewSignals(receive);
			        }
			
			
	    }
	/*    public static void GetEquipments(int stationid,int roomid)
	    {
	    	
	    	MobilePacket mobilepacket = new MobilePacket();
	    	mobilepacket.GetConfigPacket(ConfigType.Equipments,stationid,roomid,-1);
	    	byte buffer [] =new byte[4*1024];
	    	try {
				SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	  
	    
	    	MobilePacket receive = new MobilePacket();
	    	receive.RawPacket=buffer;
	    	//receive.length=temp;
	    	receive.ParseReceivePacket();
			
	    }
	    */
	    /*
	    public static void GetSignals(int stationid,int roomid,int equipmentid)
	    {
	    	
				MobilePacket mobilepacket = new MobilePacket();
				mobilepacket.GetConfigPacket(ConfigType.Signals,stationid,roomid,equipmentid);
				byte buffer [] =new byte[4*1024];
		    	try {
					SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
		    
				MobilePacket receive = new MobilePacket();
				receive.RawPacket=buffer;
				//receive.length=temp;
				receive.ParseReceivePacket();
			
	    }
	    */
	 
	 /*   public static void GetSignalsCfg(int stationid,int roomid,int equipmentid,ConfigType ntype)
	    {
	    		MobilePacket mobilepacket = new MobilePacket();
				mobilepacket.GetConfigPacket(ntype,stationid,roomid,equipmentid);
				byte buffer [] =new byte[4*1024];
		    	try {
					SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
		    
				MobilePacket receive = new MobilePacket();
				receive.RawPacket=buffer;
				//receive.length=temp;
				receive.ParseReceivePacket();
			
	    }
	    */
	    public static void GetEquipmentAnalogSignalsCfg(int stationid,int roomid,int equipmentid)
	    {
	    	  byte[] receive=null;
		    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.GetEquipmentAnalogSignalsCfgBytes(DataAccess.clientid,stationid,roomid,equipmentid));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseEquipmentAnalogSignalCfg(receive);
			        }
			
	    }
	    public static void GetEquipmentSwitchSignalsCfg(int stationid,int roomid,int equipmentid)
	    {
	     	  byte[] receive=null;
		    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.GetEquipmentSwitchSignalsCfgBytes(DataAccess.clientid,stationid,roomid,equipmentid));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.parseEquipmentSwitchSignalCfg(receive);
			        }
			
	    }
	    /*
	    public static void GetRealTimeSignals(int stationid,int roomid,int equipmentid)
	    {
	    			MobilePacket mobilepacket = new MobilePacket();
				mobilepacket.GetRealTimeSignals(equipmentid);
				byte buffer [] =new byte[4*1024];
		    	try {
					SendAndReceive(mobilepacket.RawPacket,mobilepacket.length,buffer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
		    
				MobilePacket receive = new MobilePacket();
				receive.RawPacket=buffer;
			//	receive.length=temp;
				receive.ParseReceivePacket();
			
	    }*/
	    
	    
	    
	    public static void GetAlarmStatics(int stationid,int year)
	    {
	  	  
	    	  byte[] receive=null;
		    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.GetAlarmStaticsBytes(DataAccess.clientid,stationid,year));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.ParseAlarmStatics(receive);
			        }
	    	
	  	 	
		
		
	  	  
	    }
	    public static void  GetPowerOutputDaily(int year)
	    {
	  	  
	   	   
	  	  byte[] receive=null;
	    	 
	    	try {
				 receive=SendAndReceive(RequestEntity.GetPowerOutputDailyBytes(DataAccess.clientid,stationid,year));
			} catch (Exception e) {
	 
			//	throw new Exception(e.getMessage().toString());			
			}	  
  
	    	  if(receive!=null)
		        {
		    	  PacketParse.ParsePowerDaily(receive);
		        }
  	
	 	
	
				
				
		
	  	  
	    }
	    public static void  GetPowerOutputMonthy(int year,int stationid,int roomid,int equipmentid)
	    {
	  	  
	   	   
	  	  byte[] receive=null;
	    	 
	    	try {
				 receive=SendAndReceive(RequestEntity.GetPowerOutputMonthyBytes(DataAccess.clientid,year,stationid,roomid,equipmentid));
			} catch (Exception e) {
	 
			//	throw new Exception(e.getMessage().toString());			
			}	  
  
	    	  if(receive!=null)
		        {
		    	  PacketParse.ParsePowerMonthy(receive);
		        }
  	
	 	
	
				
				
		
	  	  
	    }
	    public  static boolean CheckPacketHead(byte[] buffer)
	    {
	    	
	    	return true;
	    }
	   
	    

	    
	    
	    public static  byte[] SendAndReceive(byte[] senddata) throws Exception
	    { 
	    	
	    	byte[] receive=SendAndReceivedetail(senddata);

	    	StringBuffer sb=new StringBuffer();
	    	for (int i = 0; i < receive.length; i++) {
	    		sb.append(receive[i]);
			}
	    	//System.out.println("数据:"+sb.toString());
	    	
	    	if(receive!=null)
	    	{
	    	int errorcode=CheckPacket(receive);
	    	if(errorcode>=0)
	    	{
	    		DataAccess.nConnectedErrorNum=0;
	    		DataAccess.nErrorCode=0;
	    		return receive;
	    		
	    	}
	    	else if(errorcode==-2)
	    	{
	    		DataAccess.nErrorCode=-2;
	    		
	    		return null;
	    		
	    	}
	    	else if(errorcode==-4)
	    	{
	    		DataAccess.nErrorCode=-4;
	    		
	    		return null; 
	    		
	    	}
	    	else
	    	{
	    		
	    		DataAccess.nConnectedErrorNum++;
	    		if(DataAccess.nConnectedErrorNum>3)
	    		{
	    			DataAccess.nConnectedErrorNum=3;
	    			
	    		}
	    		return null;
	    		
	    	}
	    	}
	    	else
	    	{
	    		DataAccess.nConnectedErrorNum++;
	    		if(DataAccess.nConnectedErrorNum>3)
	    		{
	    			DataAccess.nConnectedErrorNum=3;
	    			
	    		}
	    		return null;
	    	}
	    
	    }
	    
	    public static  byte[] SendAndReceivedetail(byte[] senddata) throws Exception
	    {
	    	byte[] receivetemp=new byte[80000];
	        Socket socket;
		    socket = new Socket(); // first	
	    	 try {	
	    		 	 int port = Integer.parseInt(SystemSetting.ServerPort);
				     socket.connect(new InetSocketAddress(SystemSetting.ServerIp, port),15 *1000);			
				     OutputStream out=socket.getOutputStream();
				     InputStream in=socket.getInputStream();	    	     	        	 
				     out.write(senddata,0,senddata.length);
				     out.flush();
				     int index=0;
		   	    	 while(in.read(receivetemp, index, 1)==1)
		    	     {
		   	    		//System.out.println(",,我进来了");
		   	    	    index=index+1;
		    		    if(receivetemp[0]==0x7e)
		    		    {
		    			   if(in.read(receivetemp, index,22)==22)
		    			  {
		    				  index=index+22;
		    				  CheckPacketHead(receivetemp);
		    				  int datacount=BitConverter.getInt(receivetemp, 1);	    				 
		    				  int totalreadcount=0;
		    				  int readcount=0;
		    				  while((readcount=in.read(receivetemp,index,datacount))>0 && (datacount>0))
		    		    	  {
		    					  index=index+readcount;
		    					  totalreadcount=readcount+totalreadcount;
		    		    	      if(totalreadcount<datacount)
		    		    	      {
		    		    	    	  continue;
		    		    	      }
		    		    	      else
		    		    	      {	  
		    		    	    	  byte[] rtnbuff=new byte[datacount+23];
		    		    	    	  System.arraycopy(receivetemp, 0, rtnbuff, 0, rtnbuff.length);
		    		    	    	  socket.close();
		    		    	    	  return rtnbuff;
		    		    	      }
		    		    	  }    				  
		    			  }
		    			  else
		    			  {
		    				  socket.close();
		    			  }		
		    		  }
		    	  }
		   	    	 
		   	      byte[] rtnbuff=new byte[index];
		    	  System.arraycopy(receivetemp, 0, rtnbuff, 0, index);
		    	  
		    	  if(!socket.isClosed())
		    	  {
		    		  socket.close();
		    	  }
		    	 
		    	 // System.out.println(",,我出去了");
		    	  
		    	  return rtnbuff;
	    	
	    	 } catch (Exception e) {
					// TODO Auto-generated catch block
		    	 if(!socket.isClosed())
		    	 {
		    		 socket.close();
		    	 }    
				 return null;//	throw new Exception(e.getMessage().toString());	
				} 
	    } 
	   public static int CheckPacket(byte[] buff)
	   {
		   if(buff.length<23)
		   {
			   return -1;
		   }
		   long receivechecksum=BitConverter.getUnsignedShort(buff,9);
		   long checksum=CheckSum(buff);
		   if(receivechecksum==checksum)
		   {
			   return BitConverter.getInt(buff,5);
		   }
		   else
		   {		   
		     return -1;
		   }
	   }
	   public static long CheckSum(byte[] buff)
	   {
		//   short nchecksum=0;
		   buff[9]=0x00;
		   buff[10]=0x00;
		   long sum=0;
		   int index=0;
		   int count=buff.length;
		   while(count>1)
		   {
			   sum+=BitConverter.getUnsignedShort(buff,index);
			   index=index+2;
			  count= count-2;			   
		   }
		   if(count>0)
		   {
			   
			   sum+=BitConverter.getUnsignedByte(buff,index);
			   
		   }
		   while((sum>>16)>0)
		   {
			   sum=(sum & 0xffff)+(sum>>16);
					   
		   }
		   		 
		  return  (~sum)&0xffff;	   
	   }
	   
	    public static void GetRealTimeAnalogSignals(int stationid,int roomid,int equipmentid)
	    {
    	    byte[] receive=null;
    	 
	    	try {
				 receive=SendAndReceive(RequestEntity.getAliveEquipmentAnalogSignalsBytes(DataAccess.clientid,stationid,roomid,equipmentid));
			} catch (Exception e) {
			//	throw new Exception(e.getMessage().toString());			
			}	  
    
	    	if(receive!=null)
		    {
		    	 PacketParse.parseRealTimeAnalogSignals(receive);
		    }
			
	    }
	    
	    public static void GetRealTimeSwitchSignals(int stationid,int roomid,int equipmentid)
	    {
    	    byte[] receive=null;
	    	 
	    	try {
				 receive=SendAndReceive(RequestEntity.getAliveEquipmentSwitchSignalsBytes(DataAccess.clientid,stationid,roomid,equipmentid));
			} catch (Exception e) {
			//	throw new Exception(e.getMessage().toString());			
			}	  
    
	    	if(receive!=null)
		    {
		    	 PacketParse.parseRealTimeSwitchSignals(receive);
		    }	
	    }
	    
	    public static void GetEquipmentActiveAlarm(int stationid,int roomid,int equipmentid)
	    {
	    	byte[] receive=null;
		    	 
		    try {
				 receive=SendAndReceive(RequestEntity.getAliveEquipmentAlarmsBytes(DataAccess.clientid, stationid,roomid,equipmentid));
			} catch (Exception e) { 
				//	throw new Exception(e.getMessage().toString());			
			}	  
	    
		    if(receive!=null)
			{
			     PacketParse.ParseEquipmentAliveEvents(receive);
			}	
	    }
	    
	    public static void GetEquipmentCommand(int stationid,int roomid,int equipmentid)
	    {
	    	byte[] receive=null;
		    	
	    	
	    	//System.out.println("数字："+DataAccess.clientid+":"+stationid+":"+roomid+":"+equipmentid);
		    try {
				 receive=SendAndReceive(RequestEntity.getEquipmentCommandBytes(DataAccess.clientid, stationid,roomid,equipmentid));
			} catch (Exception e) { 
				//	throw new Exception(e.getMessage().toString());			
			}	  
	    
		    if(receive!=null)
			{
			   PacketParse.ParseEquipmentCommand(receive);
			   
			}	
	    }
	    
	    public static void getEquipmentCommandControl(int stationid,int roomid,int equipmentid,int arg)
	    {
	    	byte[] receive=null;
		    	
	    	
	    
		    try {
				 receive=SendAndReceive(RequestEntity.getEquipmentCommandControlBytes(DataAccess.clientid, stationid,roomid,equipmentid,arg));
			} catch (Exception e) { 
				//	throw new Exception(e.getMessage().toString());			
			}	  
	    
		    if(receive!=null)
			{
			   //PacketParse.ParseEquipmentCommand(receive);
			   
			}	
	    }
	    
	    public static void GetActiveAlarm(int stationid,int level,int page,int pagesize)
	    {
	    	   byte[] receive=null;
		    	 
		    	try {
					 receive=SendAndReceive(RequestEntity.getAliveAlarmsBytes( DataAccess.clientid, stationid, level, page, pagesize));
				} catch (Exception e) {
		 
				//	throw new Exception(e.getMessage().toString());			
				}	  
	    
		    	  if(receive!=null)
			        {
			    	  PacketParse.ParseAliveEvents(receive,page,pagesize);
			        }
			
		
	    }

}
