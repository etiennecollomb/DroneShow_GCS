package com.drone.show.gcs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;

import io.dronefleet.mavlink.MavlinkMessage;
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

public class RealDroneModel {


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

	private MavlinkMessage<?> heartbeat; //mode , arm
	private MavlinkMessage<?> localPositionNed;
	private MavlinkMessage<?> gpsRawInt; //nb of Sat , Gps fix
	private MavlinkMessage<?> statusText; //Status about PreArm failure...etc
	private MavlinkMessage<?> ekfStatusReport;
	private MavlinkMessage<?> commandAck;
	private MavlinkMessage<?> missionAck;
	private MavlinkMessage<?> paramValue;
	private MavlinkMessage<?> missionRequest;
	private MavlinkMessage<?> globalPositionInt;
	private MavlinkMessage<?> homePosition;



	public RealDroneModel(){
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


	

	public MavlinkMessage<?> getHeartbeat() {
		return heartbeat;
	}


	public void setHeartbeat(MavlinkMessage<?> heartbeat) {
		this.pcs.firePropertyChange(RealDroneModel.HEARTBEAT, null, heartbeat);
		this.heartbeat = heartbeat;
	}


	public MavlinkMessage<?> getLocalPositionNed() {
		return localPositionNed;
	}


	public void setLocalPositionNed(MavlinkMessage<?> localPositionNed) {
		this.pcs.firePropertyChange(RealDroneModel.LOCAL_POS_NED, null, localPositionNed);
		this.localPositionNed = localPositionNed;
	}


	public MavlinkMessage<?> getGpsRawInt() {
		return gpsRawInt;
	}


	public void setGpsRawInt(MavlinkMessage<?> gpsRawInt) {
		this.pcs.firePropertyChange(RealDroneModel.GPS_RAW_INT, null, gpsRawInt);
		this.gpsRawInt = gpsRawInt;
	}


	public MavlinkMessage<?> getStatusText() {
		return statusText;
	}


	public synchronized void setStatusText(MavlinkMessage<?> statusText) {
		this.pcs.firePropertyChange(RealDroneModel.STATUS, null, statusText);
		this.statusText = statusText;
	}


	public MavlinkMessage<?> getEkfStatusReport() {
		return ekfStatusReport;
	}


	public void setEkfStatusReport(MavlinkMessage<?> ekfStatusReport) {
		this.pcs.firePropertyChange(RealDroneModel.EKF_STATUS, null, ekfStatusReport);
		this.ekfStatusReport = ekfStatusReport;
	}


	public MavlinkMessage<?> getCommandAck() {
		return commandAck;
	}


	public synchronized void setCommandAck(MavlinkMessage<?> commandAck) {
		this.pcs.firePropertyChange(RealDroneModel.COMMAND_ACK, null, commandAck);
		this.commandAck = commandAck;
	}


	public MavlinkMessage<?> getMissionAck() {
		return missionAck;
	}


	public synchronized void setMissionAck(MavlinkMessage<?> missionAck) {
		this.pcs.firePropertyChange(RealDroneModel.MISSION_ACK, null, missionAck);
		this.missionAck = missionAck;
	}

	public MavlinkMessage<?> getMissionRequest() {
		return missionRequest;
	}


	public synchronized void setMissionRequest(MavlinkMessage<?> missionRequest) {
		this.pcs.firePropertyChange(RealDroneModel.MISSION_REQUEST, null, missionRequest);
		this.missionRequest = missionRequest;
	}


	public MavlinkMessage<?> getParamValue() {
		return paramValue;
	}


	public synchronized void setParamValue(MavlinkMessage<?> paramValue) {
		this.pcs.firePropertyChange(RealDroneModel.PARAM_VALUE, null, paramValue);
		this.paramValue = paramValue;
	}


	public MavlinkMessage<?> getGlobalPositionInt() {
		return globalPositionInt;
	}


	public void setGlobalPositionInt(MavlinkMessage<?> globalPositionInt) {
		this.pcs.firePropertyChange(RealDroneModel.GLOBAL_POSITION_INT, null, globalPositionInt);
		this.globalPositionInt = globalPositionInt;
	}


	public MavlinkMessage<?> getHomePosition() {
		return homePosition;
	}


	public void setHomePosition(MavlinkMessage<?> homePosition) {
		this.pcs.firePropertyChange(RealDroneModel.HOME_POSITION, null, homePosition);
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
