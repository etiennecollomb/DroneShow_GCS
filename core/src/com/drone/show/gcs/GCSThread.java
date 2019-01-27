package com.drone.show.gcs;

import java.io.IOException;
import java.net.Socket;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.actions.LoadChoreography;
import com.drone.show.gcs.scenarii.FlightManager;
import com.drone.show.generic.Timer;
import com.drone.show.generic.Tools;
import com.fazecast.jSerialComm.SerialPort;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.ardupilotmega.ArdupilotmegaDialect;
import io.dronefleet.mavlink.ardupilotmega.EkfStatusReport;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.GpsRawInt;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.HomePosition;
import io.dronefleet.mavlink.common.LocalPositionNed;
import io.dronefleet.mavlink.common.MavAutopilot;
import io.dronefleet.mavlink.common.MissionAck;
import io.dronefleet.mavlink.common.MissionRequest;
import io.dronefleet.mavlink.common.ParamValue;
import io.dronefleet.mavlink.common.RadioStatus;
import io.dronefleet.mavlink.common.Statustext;
import io.dronefleet.mavlink.common.Timesync;
import io.dronefleet.mavlink.standard.StandardDialect;



public class GCSThread implements Runnable {


	public final static int USB_BAUD_RATE = 115200; //115200 (USB) 57600 (telemetry/radio)
	public final static int RADIO_BAUD_RATE = 57600; //115200 (USB) 57600 (telemetry/radio)
	public final static long TIMEOUT = 1000;	

	private MavlinkConnection connection;
	private MavlinkCommunicationModel mavComModel;

	private Timer tryConnection_timer = new Timer(2000);
	private Timer heartbeat_timer = new Timer(1000);
	private Timer noStreamDataTimer = new Timer(1000); //delai a partir duquel on estime qu il n y a plus de stream data de recu


	
	//TEST 
	LoadChoreography choreographyManager;
	FlightManager flightManager;
	//END TEST


	public GCSThread(MavlinkCommunicationModel mavComModel) {
		this.mavComModel = mavComModel;
	}

	public void run() {
		try {
			this.runGCS();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**************************
	 * 
	 * MAIN GCS CODE 
	 * 
	 **************************/
	private void runGCS() throws InterruptedException {

		this.connectToAutopilot();
		//this.connectToSITL();

		this.mainLoop();
	}



	/**	
	 * Find ardupilot controller (APM or Pixhawk or radio)	
	 * Connect to it and add a listener to it	
	 */	
	private void connectToAutopilot() {

		SerialPort autopilotSerialPort = null;

		while (true) {

			//on try toutes les n sec
			if(this.tryConnection_timer.isFinished()) {
				this.tryConnection_timer.reset();


				Tools.writeLog( "Getting Com ports list..." );	
				SerialPort[] serialPorts = SerialPort.getCommPorts();	
				//Find an autopilot	
				for(int i=0; i<serialPorts.length; i++) {	
					Tools.writeLog("Serial Port:" + serialPorts[i].toString());	
					if(serialPorts[i].toString().equals("PX4 FMU v2.x") || serialPorts[i].toString().equals("Arduino Mega 2560") ){	
						Tools.writeLog("====> Found an autopilot Serial Port!");	
						autopilotSerialPort = serialPorts[i];
						Tools.writeLog("====> Set Serial Port Bauds to " + USB_BAUD_RATE);	
						autopilotSerialPort.setBaudRate(USB_BAUD_RATE);	
						break;	
					}
					if(serialPorts[i].toString().equals("CP2102 USB to UART Bridge Controller") ){
						Tools.writeLog("====> Found an autopilot Serial Port!");	
						autopilotSerialPort = serialPorts[i];
						Tools.writeLog("====> Set Serial Port Bauds to " + RADIO_BAUD_RATE);	
						autopilotSerialPort.setBaudRate(RADIO_BAUD_RATE);	
						break;	
					}
				}	
				//Set parameters and connect to the com port	
				if (autopilotSerialPort != null) {
					autopilotSerialPort.openPort();

					this.connection = MavlinkConnection.builder(autopilotSerialPort.getInputStream(), autopilotSerialPort.getOutputStream())
							.dialect(MavAutopilot.MAV_AUTOPILOT_GENERIC, new StandardDialect())
							.dialect(MavAutopilot.MAV_AUTOPILOT_ARDUPILOTMEGA, new ArdupilotmegaDialect())
							.build();

					break;
				}	
			}
		}	
	}



	/**
	 * If we want to run with SITL
	 * (for one drone, port = 5763) : OK
	 * (for multi drone...max 4-5? : port must be 5763 + n*10) : NOT DONE
	 * 
	 */
	private void connectToSITL() {

		while(true) {

			//on try toutes les n sec
			if(this.tryConnection_timer.isFinished()) {
				this.tryConnection_timer.reset();

				Tools.writeLog("try SITL Connection");

				try (Socket socket = new Socket("127.0.0.1", 5763)) {

					this.connection = MavlinkConnection.builder(socket.getInputStream(), socket.getOutputStream())
							.dialect(MavAutopilot.MAV_AUTOPILOT_GENERIC, new StandardDialect())
							.dialect(MavAutopilot.MAV_AUTOPILOT_ARDUPILOTMEGA, new ArdupilotmegaDialect())
							.build();

					break;

				} catch (IOException eof) {
					Tools.writeLog("No SITL connection found...");
					//eof.printStackTrace();
				}
			}
		}
	}




	private void mainLoop() {

		//TEST
		flightManager = new FlightManager(this.connection);
		//flightManager.setTestMissionUploadAndArming(1);  //tester avec 2..etc
		flightManager.setTestMissionUploadAndLaunch();
		//FIN TEST
		

		@SuppressWarnings("rawtypes")
		MavlinkMessage message;


		
		while (true) {

			try {

				/*****************
				 * Reading & Update Model
				 *****************/
				if( (message = this.connection.next()) != null) {

					
					/** 
					 * Check si on recoit de la stream data ou pas
					 **/
					if(!(message.getPayload() instanceof Heartbeat)
							&& !(message.getPayload() instanceof RadioStatus)
							&& !(message.getPayload() instanceof ParamValue)
							&& !(message.getPayload() instanceof Timesync)
							&& !(message.getPayload() instanceof Statustext)
							) {
						this.noStreamDataTimer.reset(); //on remet la compteur ON, pendant n sec on estime qu on a de la stream Data
						this.mavComModel.setStreamData(true);
					}
					else if(this.noStreamDataTimer.isFinished()){
						this.mavComModel.setStreamData(false);
					}
					
					

					if (message.getPayload() instanceof Heartbeat) {

						Heartbeat heartbeat = (Heartbeat)message.getPayload();
						this.mavComModel.setHeartbeat(heartbeat);

					}else if (message.getPayload() instanceof LocalPositionNed) {

						LocalPositionNed localPositionNed = (LocalPositionNed)message.getPayload();
						mavComModel.setLocalPositionNed(localPositionNed);

					}else if (message.getPayload() instanceof GpsRawInt) {

						GpsRawInt gpsRawInt = (GpsRawInt)message.getPayload();
						this.mavComModel.setGpsRawInt(gpsRawInt);

					}else if (message.getPayload() instanceof HomePosition) {

						HomePosition homePosition = (HomePosition)message.getPayload();
						mavComModel.setHomePosition(homePosition);
					}
					else if (message.getPayload() instanceof Statustext) {
						
						Statustext statusText = (Statustext)message.getPayload();
						mavComModel.setStatusText(statusText.text());

					}
					else if (message.getPayload() instanceof EkfStatusReport) {
						
						EkfStatusReport ekfStatusReport = (EkfStatusReport)message.getPayload();
						mavComModel.setEkfStatusReport(ekfStatusReport);
						
					}
					else if (message.getPayload() instanceof CommandAck) {

						CommandAck commandAck = (CommandAck)message.getPayload();
						this.mavComModel.setCommandAck(commandAck);

					}
					else if (message.getPayload() instanceof MissionAck) {

						MissionAck missionAck = (MissionAck)message.getPayload();
						this.mavComModel.setMissionAck(missionAck);

					}
					else if (message.getPayload() instanceof ParamValue) {

						ParamValue paramValue = (ParamValue)message.getPayload();
						this.mavComModel.setParamValue(paramValue);

					}
					else if (message.getPayload() instanceof MissionRequest) {
						
						MissionRequest missionRequest = (MissionRequest)message.getPayload();
						this.mavComModel.setMissionRequest(missionRequest);

					}

					if(GlobalManager.ISDEBUG) {
						Tools.writeLog("(received) "+message.getPayload().toString());
					}


				}



				/*****************
				 * HEARTBEAT
				 *****************/
				if(this.heartbeat_timer.isFinished()) {
					this.heartbeat_timer.reset();
					
					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.heartbeat()); //Must be sent to all DRONEs every time (!!! OBLIGATOIRE !!!)

				}
				
				
				//TEST
				//MavLinkToolKit.sendCommand(connection, MavLinkToolKit.requestStreamData(MavLinkToolKit.MAVLINK_STREAM_DATA_ID_EkfStatusReport));
				//MavLinkToolKit.sendCommand(connection, MavLinkToolKit.stopAllStreamData());
				//choreographyManager.update();
				flightManager.update();
				//END TEST
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}



}	

