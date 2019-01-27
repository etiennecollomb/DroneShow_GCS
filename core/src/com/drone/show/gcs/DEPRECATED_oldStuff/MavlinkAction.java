package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandAck;

/**
 * Tous est decrit en CENTIMETRE et en position relative
 * 
 * Les commandes doivent etre atomiques
 */

public abstract class MavlinkAction {

	public enum MavlinkActionType {
		REQUEST_DATA_STREAM,
		INIT_LED,
		PRE_ARM_CHECK,
		STABILIZE_MODE,
		GUIDED_MODE,
		SET_HOME_TO_CURRENT_LOCATION,
		ARM,
		TAKEOFF,
		LOITER_MODE,
		VELOCITY,
		LAND_MODE,
		WAIT
	}



	private MavlinkActionType mavlinkActionType;
	private boolean isFinished;
	private MavlinkMessage mavlinkMessage;
	private MavlinkConnection connection;
	private CommandAck commandAck; //Commande bien recu par le drone, le current move a demarré
	private boolean isCommandAcknowledge;
	private RealDroneModel mavComModel;

	private long send_command_interval; //in ms
	private long send_command_timer;

	
	
	public MavlinkAction(MavlinkActionType mavlinkActionType, MavlinkConnection connection, RealDroneModel mavComModel) {

		this.setMavlinkActionType(mavlinkActionType);
		this.setFinished(false);
		this.setMavlinkMessage(null);
		this.setConnection(connection);
		this.setCommandAck(null);
		this.setMavComModel(mavComModel);
	}

	
	public void sendCommand() {

		if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
			this.setSend_command_timer( System.currentTimeMillis() );

			Tools.writeLog(this.toString());
			MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());

		}	

	}


	public abstract void onCommandAckReceived();

	public abstract void update(float deltaTime);


	public MavlinkActionType getMavlinkActionType() {
		return mavlinkActionType;
	}

	public void setMavlinkActionType(MavlinkActionType moveType) {
		this.mavlinkActionType = moveType;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public MavlinkMessage getMavlinkMessage() {
		return mavlinkMessage;
	}

	public void setMavlinkMessage(MavlinkMessage mavlinkMessage) {
		this.mavlinkMessage = mavlinkMessage;
	}

	public MavlinkConnection getConnection() {
		return connection;
	}

	public void setConnection(MavlinkConnection connection) {
		this.connection = connection;
	}

	public CommandAck getCommandAck() {
		return commandAck;
	}

	public void setCommandAck(CommandAck commandAck) {
		this.commandAck = commandAck;

		if(this.commandAck != null)
			this.onCommandAckReceived();
	}

	public boolean isCommandAcknowledge() {
		return isCommandAcknowledge;
	}

	public void setCommandAcknowledge(boolean isCommandAcknowledge) {
		this.isCommandAcknowledge = isCommandAcknowledge;
	}

	public long getSend_command_interval() {
		return send_command_interval;
	}

	public void setSend_command_interval(long send_command_interval) {
		this.send_command_interval = send_command_interval;
	}

	public long getSend_command_timer() {
		return send_command_timer;
	}

	public void setSend_command_timer(long send_command_timer) {
		this.send_command_timer = send_command_timer;
	}

	public RealDroneModel getMavComModel() {
		return mavComModel;
	}

	public void setMavComModel(RealDroneModel mavComModel) {
		this.mavComModel = mavComModel;
	}

}
