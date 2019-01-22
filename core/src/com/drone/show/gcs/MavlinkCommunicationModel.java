package com.drone.show.gcs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;
import io.dronefleet.mavlink.ardupilotmega.EkfStatusReport;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.GlobalPositionInt;
import io.dronefleet.mavlink.common.GpsFixType;
import io.dronefleet.mavlink.common.HomePosition;
import io.dronefleet.mavlink.common.MissionAck;
import io.dronefleet.mavlink.common.MissionRequest;
import io.dronefleet.mavlink.common.ParamValue;

/**
 * 
 * Temporaire pour tester
 * Ce model doit etre en fait celui des drones dans le GCS
 */

public class MavlinkCommunicationModel {


	public static final String IS_STREAM_DATA = "IS_STREAM_DATA";
	public static final String MODE = "MODE";
	public static final String NUMBER_OF_SATTELITES = "NUMBER_OF_SATTELITES";
	public static final String GPS_FIX_TYPE = "GPS_FIX_TYPE";
	public static final String ARM_STATUS = "ARM_STATUS";
	public static final String STATUS = "STATUS";
	public static final String POSITION = "POSITION";
	public static final String EKF_STATUS = "EKF_STATUS";
	public static final String COMMAND_ACK = "COMMAND_ACK";
	public static final String MISSION_ACK = "MISSION_ACK";
	public static final String MISSION_REQUEST = "MISSION_REQUEST";
	public static final String PARAM_VALUE = "PARAM_VALUE";
	public static final String GLOBAL_POSITION_INT = "GLOBAL_POSITION_INT";
	public static final String HOME_POSITION = "HOME_POSITION";

	


	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public static enum Mode {
		STABILIZE,
		GUIDED,
		LOITER,
		AUTO
	}

	
	//a t on des stream data en cours?
	//pour tout message autre que heartbeat recu : oui
	boolean isStreamData; 

	private boolean isArmed;
	private Mode mode;
	//The filtered local position (e.g. fused computer vision and accelerometers). Coordinate frame is right-handed, Z-axis up (aeronautical frame, NED / north-east-up convention)
	private Vector3 localPositionNed = new Vector3(0,0,0); 
	private int numberOfSatellite = 0;
	private GpsFixType gpsFixType;

	//Status about PreArm failure...etc
	private String statusText;

	//EKF status
	private EkfStatusReport ekfStatusReport;

	//Ack
	private CommandAck commandAck;
	private MissionAck missionAck;
	
	//Param
	private ParamValue paramValue;

	//Mission msg
	private MissionRequest missionRequest;
	
	//Position
	private GlobalPositionInt globalPositionInt;
	private HomePosition homePosition;
	
	

	public MavlinkCommunicationModel(){
		this.addPropertyChangeListener(GlobalManager.applicationModel);
		this.resetValue();
	}


	public synchronized void resetValue() {

		this.isStreamData = false;
		this.isArmed = false;
		this.mode = null;
		this.localPositionNed.set(0,0,0);
		this.numberOfSatellite = 0;
		this.gpsFixType = null;
		this.statusText = null;

		/** 
		 * Absolute = above sea level (ASL)
		 * Relative = above home altitude - the APM sets a reference altitude when it is turned on
		 * Terrain = above ground level (AGL) - the ground station references google terrain information
		 **/
		this.ekfStatusReport = null;
		this.commandAck = null;
		this.missionAck = null;
		this.paramValue = null;
		this.globalPositionInt = null;
		this.homePosition = null;
	}


	
	public boolean isStreamData() {
		return isStreamData;
	}


	public void setStreamData(boolean isStreamData) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.IS_STREAM_DATA, null, isStreamData);
		this.isStreamData = isStreamData;
	}
	
	
	public  boolean isArmed() {
		return isArmed;
	}


	public synchronized void setArmed(boolean isArmed) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.ARM_STATUS, null, isArmed);
		this.isArmed = isArmed;
	}


	public Mode getMode() {
		return mode;
	}


	public synchronized void setMode(Mode mode) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.MODE, null, mode);
		this.mode = mode;
	}


	public Vector3 getLocalPositionNed() {
		return localPositionNed;
	}


	public synchronized void setLocalPositionNed(Vector3 localPositionNed) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.POSITION, null, localPositionNed);
		this.localPositionNed = localPositionNed;
	}


	public int getNumberOfSatellite() {
		return numberOfSatellite;
	}


	public synchronized void setNumberOfSatellite(int numberOfSatellite) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.NUMBER_OF_SATTELITES, null, numberOfSatellite);
		this.numberOfSatellite = numberOfSatellite;
	}


	public GpsFixType getGpsFixType() {
		return gpsFixType;
	}


	public synchronized void setGpsFixType(GpsFixType gpsFixType) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.GPS_FIX_TYPE, null, gpsFixType);
		this.gpsFixType = gpsFixType;
	}


	public String getStatusText() {
		return statusText;
	}


	public synchronized void setStatusText(String statusText) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.STATUS, null, statusText);
		this.statusText = statusText;
	}


	public EkfStatusReport getEkfStatusReport() {
		return ekfStatusReport;
	}


	public void setEkfStatusReport(EkfStatusReport ekfStatusReport) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.EKF_STATUS, null, ekfStatusReport);
		this.ekfStatusReport = ekfStatusReport;
	}


	public CommandAck getCommandAck() {
		return commandAck;
	}


	public synchronized void setCommandAck(CommandAck commandAck) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.COMMAND_ACK, null, commandAck);
		this.commandAck = commandAck;
	}

	
	public MissionAck getMissionAck() {
		return missionAck;
	}


	public synchronized void setMissionAck(MissionAck missionAck) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.MISSION_ACK, null, missionAck);
		this.missionAck = missionAck;
	}

	public MissionRequest getMissionRequest() {
		return missionRequest;
	}


	public synchronized void setMissionRequest(MissionRequest missionRequest) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.MISSION_REQUEST, null, missionRequest);
		this.missionRequest = missionRequest;
	}


	public ParamValue getParamValue() {
		return paramValue;
	}


	public synchronized void setParamValue(ParamValue paramValue) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.PARAM_VALUE, null, paramValue);
		this.paramValue = paramValue;
	}


	public GlobalPositionInt getGlobalPositionInt() {
		return globalPositionInt;
	}


	public void setGlobalPositionInt(GlobalPositionInt globalPositionInt) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.GLOBAL_POSITION_INT, null, globalPositionInt);
		this.globalPositionInt = globalPositionInt;
	}


	public HomePosition getHomePosition() {
		return homePosition;
	}


	public void setHomePosition(HomePosition homePosition) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.HOME_POSITION, null, homePosition);
		this.homePosition = homePosition;
	}


	/**************************************
	 * 
	 * Property Change Support/Listener
	 * 
	 **************************************/

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


}
