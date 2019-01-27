package com.drone.show.gcs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;
import io.dronefleet.mavlink.ardupilotmega.EkfStatusReport;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.GlobalPositionInt;
import io.dronefleet.mavlink.common.GpsFixType;
import io.dronefleet.mavlink.common.GpsRawInt;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.HomePosition;
import io.dronefleet.mavlink.common.LocalPositionNed;
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

	public static final String HEARTBEAT = "HEARTBEAT";
	public static final String LOCAL_POS_NED = "LOCAL_POS_NED";
	public static final String GPS_RAW_INT = "GPS_RAW_INT";
	public static final String STATUS = "STATUS";
	public static final String EKF_STATUS = "EKF_STATUS";
	public static final String COMMAND_ACK = "COMMAND_ACK";
	public static final String MISSION_ACK = "MISSION_ACK";
	public static final String MISSION_REQUEST = "MISSION_REQUEST";
	public static final String PARAM_VALUE = "PARAM_VALUE";
	public static final String GLOBAL_POSITION_INT = "GLOBAL_POSITION_INT";
	public static final String HOME_POSITION = "HOME_POSITION";



	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	//a t on des stream data en cours?
	//pour tout message autre que heartbeat recu : oui
	boolean isStreamData; 

	private Heartbeat heartbeat; //mode , arm
	private LocalPositionNed localPositionNed;
	private GpsRawInt gpsRawInt; //nb of Sat , Gps fix
	private String statusText; //Status about PreArm failure...etc
	private EkfStatusReport ekfStatusReport;
	private CommandAck commandAck;
	private MissionAck missionAck;
	private ParamValue paramValue;
	private MissionRequest missionRequest;
	private GlobalPositionInt globalPositionInt;
	private HomePosition homePosition;



	public MavlinkCommunicationModel(){
		this.addPropertyChangeListener(GlobalManager.applicationModel);
		this.resetValue();
	}


	public synchronized void resetValue() {

		/** 
		 * Position :
		 * Absolute = above sea level (ASL)
		 * Relative = above home altitude - the APM sets a reference altitude when it is turned on
		 * Terrain = above ground level (AGL) - the ground station references google terrain information
		 **/
		this.heartbeat = null;
		this.localPositionNed = null;
		this.gpsRawInt = null;
		this.statusText = null;
		this.ekfStatusReport = null;
		this.commandAck = null;
		this.missionAck = null;
		this.paramValue = null;
		this.missionRequest = null;
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
	

	public Heartbeat getHeartbeat() {
		return heartbeat;
	}


	public void setHeartbeat(Heartbeat heartbeat) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.HEARTBEAT, null, heartbeat);
		this.heartbeat = heartbeat;
	}


	public LocalPositionNed getLocalPositionNed() {
		return localPositionNed;
	}


	public void setLocalPositionNed(LocalPositionNed localPositionNed) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.LOCAL_POS_NED, null, localPositionNed);
		this.localPositionNed = localPositionNed;
	}


	public GpsRawInt getGpsRawInt() {
		return gpsRawInt;
	}


	public void setGpsRawInt(GpsRawInt gpsRawInt) {
		this.pcs.firePropertyChange(MavlinkCommunicationModel.GPS_RAW_INT, null, gpsRawInt);
		this.gpsRawInt = gpsRawInt;
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
