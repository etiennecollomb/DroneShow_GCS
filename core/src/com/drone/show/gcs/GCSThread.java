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

	private Timer tryConnection_timer = new Timer(2000);
	private Timer heartbeat_timer = new Timer(1000);
	private Timer noStreamDataTimer = new Timer(1000); //delai a partir duquel on estime qu il n y a plus de stream data de recu



	//TEST 
	LoadChoreography choreographyManager;
	FlightManager flightManager;
	//END TEST


	public GCSThread() {
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
		GlobalManager.realWorldModel.addRealDroneModel(1, new RealDroneModel());
		GlobalManager.realWorldModel.addRealDroneModel(2, new RealDroneModel());

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


					if(GlobalManager.ISDEBUG) {
						Tools.writeLog("(received) "+message.toString());
					}


					/**
					 * Les radio SIK envoient elle meme une telemetrie sr leur etat (est ce qu on peut desactiver?)
					 * WARNING : originSystemId=51, originComponentId=68
					 * (received) MavlinkMessage{originSystemId=51, originComponentId=68, payload=RadioStatus{rssi=170, remrssi=170, txbuf=100, noise=94, remnoise=87, rxerrors=1942, fixed=4367}}
					 */
					//TODO: temporaire tant qu on est en dessosu de 51 drones....
					if(message.getOriginSystemId() <= GlobalManager.realWorldModel.realDroneModels.size()) {


						/** On popule le droneModel correspondant au systemID
						 * sytemId from 1 to N
						 **/
						RealDroneModel realDroneModel = GlobalManager.realWorldModel.getRealDroneModel( message.getOriginSystemId() );

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
							GlobalManager.realWorldModel.setStreamData(true);
						}
						else if(this.noStreamDataTimer.isFinished()){
							GlobalManager.realWorldModel.setStreamData(false);
						}



						if (message.getPayload() instanceof Heartbeat) {
							realDroneModel.setHeartbeat(message);

						}else if (message.getPayload() instanceof LocalPositionNed) {
							realDroneModel.setLocalPositionNed(message);

						}else if (message.getPayload() instanceof GpsRawInt) {
							realDroneModel.setGpsRawInt(message);

						}else if (message.getPayload() instanceof HomePosition) {
							realDroneModel.setHomePosition(message);
						}
						else if (message.getPayload() instanceof Statustext) {
							realDroneModel.setStatusText(message);

						}
						else if (message.getPayload() instanceof EkfStatusReport) {
							realDroneModel.setEkfStatusReport(message);

						}
						else if (message.getPayload() instanceof CommandAck) {
							realDroneModel.setCommandAck(message);

						}
						else if (message.getPayload() instanceof MissionAck) {
							realDroneModel.setMissionAck(message);

						}
						else if (message.getPayload() instanceof ParamValue) {
							realDroneModel.setParamValue(message);

						}
						else if (message.getPayload() instanceof MissionRequest) {
							realDroneModel.setMissionRequest(message);

						}
						
						
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

