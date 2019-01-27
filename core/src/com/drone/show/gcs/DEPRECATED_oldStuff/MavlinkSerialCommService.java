package com.drone.show.gcs.DEPRECATED_oldStuff;


import java.io.IOException;
import java.net.Socket;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario01;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario01bis;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario01ter;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario02;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario03;
import com.drone.show.gcs.DEPRECATED_oldStuff.scenarii.Scenario04;
import com.drone.show.generic.Tools;
import com.fazecast.jSerialComm.SerialPort;	
import io.dronefleet.mavlink.Mavlink2Message;
import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.ardupilotmega.ArdupilotmegaDialect;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.GpsRawInt;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.HomePosition;
import io.dronefleet.mavlink.common.LocalPositionNed;
import io.dronefleet.mavlink.common.MavAutopilot;
import io.dronefleet.mavlink.common.MavModeFlag;
import io.dronefleet.mavlink.common.ParamValue;
import io.dronefleet.mavlink.common.Statustext;
import io.dronefleet.mavlink.standard.StandardDialect;



public class MavlinkSerialCommService {	


//	public final static int USB_BAUD_RATE = 115200; //115200 (USB) 57600 (telemetry/radio)
//	public final static int RADIO_BAUD_RATE = 57600; //115200 (USB) 57600 (telemetry/radio)
//	public final static long TIMEOUT = 1000;	
//
//	private MavlinkConnection connection;
//	private MavlinkCommunicationModel realWorldDroneModel;
//
//	private long sysout_delay = 100; //in ms
//	private long heartbeat_delay = 1000; //in ms
//	private long tryConnection_delay = 2000; //in ms
//	private long sysout_timer, heartbeat_timer, tryConnection_timer;
//
//	private Scenario myScenario;
//
//	private float deltaTime; //en seconde
//
//	
//	
//	
//	/** standalone start */
////	public static void main(String[] args) {	
////		new MavlinkSerialCommService();
////	}
////
////	public MavlinkSerialCommService() {
////		this.connectToAutopilote();
////	}
//	/** */
//
//	
//	
//
//	public void start(MavlinkCommunicationModel realWorldDroneModel) {
//		this.realWorldDroneModel = realWorldDroneModel;
//		this.connectToAutopilote();
//		//this.connectToSITL();
//	}
//
//	/**	
//	 * Find ardupilot controller (APM or Pixhawk or radio)	
//	 * Connect to it and add a listener to it	
//	 */	
//	private void connectToAutopilote() {
//
//		SerialPort autopilotSerialPort = null;
//		this.tryConnection_timer = System.currentTimeMillis();
//
//		while (true) {
//
//			//on try toutes les n sec
//			if(System.currentTimeMillis() - this.tryConnection_timer > this.tryConnection_delay) {
//				this.tryConnection_timer = System.currentTimeMillis();
//
//
//				Tools.writeLog( "Getting Com ports list..." );	
//				SerialPort[] serialPorts = SerialPort.getCommPorts();	
//				//Find an autopilot	
//				for(int i=0; i<serialPorts.length; i++) {	
//					Tools.writeLog("Serial Port:" + serialPorts[i].toString());	
//					if(serialPorts[i].toString().equals("PX4 FMU v2.x") || serialPorts[i].toString().equals("Arduino Mega 2560") ){	
//						Tools.writeLog("====> Found an autopilot Serial Port!");	
//						autopilotSerialPort = serialPorts[i];
//						Tools.writeLog("====> Set Serial Port Bauds to " + USB_BAUD_RATE);	
//						autopilotSerialPort.setBaudRate(USB_BAUD_RATE);	
//						break;	
//					}
//					if(serialPorts[i].toString().equals("CP2102 USB to UART Bridge Controller") ){
//						Tools.writeLog("====> Found an autopilot Serial Port!");	
//						autopilotSerialPort = serialPorts[i];
//						Tools.writeLog("====> Set Serial Port Bauds to " + RADIO_BAUD_RATE);	
//						autopilotSerialPort.setBaudRate(RADIO_BAUD_RATE);	
//						break;	
//					}
//				}	
//				//Set parameters et connect to the com port	
//				if (autopilotSerialPort != null) {
//					autopilotSerialPort.openPort();
//
//					this.connection = MavlinkConnection.builder(autopilotSerialPort.getInputStream(), autopilotSerialPort.getOutputStream())
//							.dialect(MavAutopilot.MAV_AUTOPILOT_GENERIC, new StandardDialect())
//							.dialect(MavAutopilot.MAV_AUTOPILOT_ARDUPILOTMEGA, new ArdupilotmegaDialect())
//							.build();
//
//					mainLoop();
//				}	
//			}
//		}	
//	}
//
//
//
//	private void connectToSITL() {
//
//		this.tryConnection_timer = System.currentTimeMillis();
//
//		while(true) {
//
//			//on try toutes les n sec
//			if(System.currentTimeMillis() - this.tryConnection_timer > this.tryConnection_delay) {
//				this.tryConnection_timer = System.currentTimeMillis();
//
//				Tools.writeLog("try SITL Connection");
//
//				try (Socket socket = new Socket("127.0.0.1", 5763)) {
//
//					this.connection = MavlinkConnection.builder(socket.getInputStream(), socket.getOutputStream())
//							.dialect(MavAutopilot.MAV_AUTOPILOT_GENERIC, new StandardDialect())
//							.dialect(MavAutopilot.MAV_AUTOPILOT_ARDUPILOTMEGA, new ArdupilotmegaDialect())
//							.build();
//
//					mainLoop();
//
//				} catch (IOException eof) {
//					Tools.writeLog("No SITL connection found...");
//					//eof.printStackTrace();
//				}
//			}
//		}
//	}
//
//
//
//	private void mainLoop() {
//
//
//		//droneModel = new RealWorldDroneModel();
//
//		//		myScenario = Scenario01.getScenerio(this.connection, this.realWorldDroneModel); //LED TEST
//		//		myScenario = Scenario01bis.getScenerio(this.connection, this.realWorldDroneModel); //LED TEST 2
//				myScenario = Scenario01ter.getScenerio(this.connection, this.realWorldDroneModel); //JUST ARM
//		//		myScenario = Scenario02.getScenerio(this.connection, this.realWorldDroneModel); //infinite loiter
//		//		myScenario = Scenario03.getScenerio(this.connection, this.realWorldDroneModel); //38 waypoint no led, 5min
//		//		myScenario = Scenario04.getScenerio(this.connection, this.realWorldDroneModel); //20 waypoint with led
//
//
//		@SuppressWarnings("rawtypes")
//		MavlinkMessage message;
//
//		this.sysout_timer = System.currentTimeMillis();
//		this.heartbeat_timer = System.currentTimeMillis();
//
//		this.deltaTime = System.currentTimeMillis()*1000.0f;
//
//		while (true) {
//
//			try {
//
//				/*****************
//				 * Reading & Update Model
//				 *****************/
//				if( (message = this.connection.next()) != null) {
//
////					if(GlobalManager.ISDEBUG) {
////						if (message instanceof Mavlink2Message) Tools.writeLog("This is a Mavlink2 message.");
////						else Tools.writeLog("This is a Mavlink1 message.");
////					}
//
//
//					if (message.getPayload() instanceof Heartbeat) {
//
//						Heartbeat heartbeatMessage = (Heartbeat)message.getPayload();
//
//						//Custom Mode
//						if(heartbeatMessage.customMode() == MavLinkToolKit.STABILIZE_CUSTOM_MODE) {
//							realWorldDroneModel.setMode(Mode.STABILIZE);
//						}
//						else if(heartbeatMessage.customMode() == MavLinkToolKit.GUIDED_CUSTOM_MODE) {
//							realWorldDroneModel.setMode(Mode.GUIDED);
//						}
//						else if(heartbeatMessage.customMode() == MavLinkToolKit.LOITER_CUSTOM_MODE) {
//							realWorldDroneModel.setMode(Mode.LOITER);
//						}
//
//
//						//Base Mode
//						if(heartbeatMessage.baseMode().flagsEnabled( MavModeFlag.MAV_MODE_FLAG_SAFETY_ARMED )) {
//							realWorldDroneModel.setArmed(true);
//						}
//						else {
//							realWorldDroneModel.setArmed(false);
//						}
//
//					}else if (message.getPayload() instanceof LocalPositionNed) {
//
//						LocalPositionNed localPositionNedMessage = (LocalPositionNed)message.getPayload();
//
//						//local Position Ned
//						realWorldDroneModel.setLocalPositionNed( new Vector3(localPositionNedMessage.x(), localPositionNedMessage.y(), -localPositionNedMessage.z()) );
//
//					}else if (message.getPayload() instanceof GpsRawInt) {
//
//						GpsRawInt gpsRawIntMessage = (GpsRawInt)message.getPayload();
//
//						//Number Of Satellite
//						realWorldDroneModel.setNumberOfSatellite(gpsRawIntMessage.satellitesVisible());
//
//						//GPS Fix Type
//						realWorldDroneModel.setGpsFixType(gpsRawIntMessage.fixType().entry());
//
//
//					}else if (message.getPayload() instanceof HomePosition) {
//
//						HomePosition homePosition = (HomePosition)message.getPayload();
//						Tools.writeLog("HomePosition: "+homePosition.latitude()+" "+homePosition.longitude()+" "+homePosition.altitude());
//					}
//
//					else if (message.getPayload() instanceof CommandAck) {
//
//						CommandAck commandAckMessage = (CommandAck)message.getPayload();
//						this.myScenario.receivedCommandAck(commandAckMessage);
//
//					}
//					else if (message.getPayload() instanceof Statustext) {
//						Statustext commandAckMessage = (Statustext)message.getPayload();
//						
//						realWorldDroneModel.setStatusText(commandAckMessage.text());
//
//					}
//					else if (message.getPayload() instanceof ParamValue) {
//
//						ParamValue paramValueMessage = (ParamValue)message.getPayload();
//
////						if(paramValueMessage.paramId().equals("CH6_OPT"))
////							realWorldDroneModel.setCh6_opt(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("CH7_OPT"))
////							realWorldDroneModel.setCh7_opt(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("CH8_OPT"))
////							realWorldDroneModel.setCh8_opt(paramValueMessage.paramValue());
////
////						if(paramValueMessage.paramId().equals("SERVO6_FUNCTION"))
////							realWorldDroneModel.setServo6_function(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO7_FUNCTION"))
////							realWorldDroneModel.setServo7_function(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO8_FUNCTION"))
////							realWorldDroneModel.setServo8_function(paramValueMessage.paramValue());
////
////						if(paramValueMessage.paramId().equals("SERVO6_MIN"))
////							realWorldDroneModel.setServo6_min(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO7_MIN"))
////							realWorldDroneModel.setServo7_min(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO8_MIN"))
////							realWorldDroneModel.setServo8_min(paramValueMessage.paramValue());
////
////						if(paramValueMessage.paramId().equals("SERVO6_MAX"))
////							realWorldDroneModel.setServo6_max(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO7_MAX"))
////							realWorldDroneModel.setServo7_max(paramValueMessage.paramValue());
////						if(paramValueMessage.paramId().equals("SERVO8_MAX"))
////							realWorldDroneModel.setServo8_max(paramValueMessage.paramValue());
//
//					}
//
//
//					Tools.writeLog("(received) "+message.getPayload().toString());
//					if(GlobalManager.ISDEBUG) {
//						Tools.writeLog("(received) "+message.getPayload().toString());
//					}
//
//
//				}
//
//
//				/*****************
//				 * Update Scenario
//				 *****************/
//				myScenario.update( System.currentTimeMillis()*1000.0f - this.deltaTime );
//				this.deltaTime = System.currentTimeMillis()*1000.0f;
//
//
//				/*****************
//				 * Drone Model SysOut
//				 *****************/
//				if(System.currentTimeMillis() - this.sysout_timer > this.sysout_delay) {
//					this.sysout_timer = System.currentTimeMillis();
//					//Tools.writeLog(this.realWorldDroneModel.toString());
//				}
//
//
//				/*****************
//				 * WRITING
//				 *****************/
//				if(System.currentTimeMillis() - this.heartbeat_timer > this.heartbeat_delay) {
//
//					this.heartbeat_timer = System.currentTimeMillis();
//					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.heartbeat());
////					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.requestAllStreamData(1));
//				}
//
//
//				//TEST
//				//				if (message.getPayload() instanceof CommandAck) {
//				//					Tools.writeLog("(received) "+message.getPayload().toString());
//				//				}
//				//				if (message.getPayload() instanceof RcChannels) {
//				//					Tools.writeLog("(received) "+message.getPayload().toString());
//				//				}
//				//				if (message.getPayload() instanceof ServoOutputRaw) {
//				//					Tools.writeLog("(received) "+message.getPayload().toString());
//				//				}
//
//
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}


}


