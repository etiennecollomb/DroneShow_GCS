package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.MavCmd;
import io.dronefleet.mavlink.common.MavResult;


public class TakeOffMove {

//	private static final float HERTZ = 2.0f; //frequence d'envoie de la commande
//	static final float MINDISTANCE = 1.0f; //distance en m du point qu on veut atteindre pour valider qu on est bien au point voulu
//
//	Vector3 targetPosition; //relative a la position du drone en centimetre
//
//
//	public TakeOffMove(MavlinkConnection connection, MavlinkCommunicationModel droneModel, float altitude_) {
//		super( MavlinkActionType.TAKEOFF, connection, droneModel);
////		this.setMavlinkMessage( MavLinkToolKit.takeOff(altitude_) );
//		this.setSend_command_timer( System.currentTimeMillis() );
//		this.setSend_command_interval((long)(1000.0f/HERTZ));
//
//		this.setTargetPosition( new Vector3( 0, 0, altitude_ ) );
//	}
//
//	@Override
//	public void update(float deltaTime) {
//
//		if(!this.isCommandAcknowledge()) {
//
//			if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
//				this.setSend_command_timer( System.currentTimeMillis() );
//
//				MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());
//
//			}	
//		}
//
//		//is finished or not?
//		float distance = Math.abs( this.getMavComModel().getLocalPositionNed().z - this.getTargetPosition().z );
//		if( distance < MINDISTANCE ) { //on est a mons de N m du point voulu
//			Tools.writeLog("***TimeLine*** : (TAKEOFF MOVE) distance " + distance + " (minDistance: " + MINDISTANCE + ")" );
//			this.setFinished(true);
//		}
//	}
//
//
//	public Vector3 getTargetPosition() {
//		return targetPosition;
//	}
//
//	public void setTargetPosition(Vector3 targetPosition) {
//		this.targetPosition = targetPosition;
//	}
//
//	@Override
//	public void onCommandAckReceived() {
//
//		//correspond bien a notre type de msg?
//		if( this.getCommandAck() != null && this.getCommandAck().command().entry() == MavCmd.MAV_CMD_NAV_TAKEOFF ){
//
//			//Cmd acceptee par ardupilot?
//			if(this.getCommandAck().result().entry() == MavResult.MAV_RESULT_ACCEPTED ) {
//				this.setCommandAcknowledge( true );
//			}
//		}
//	}
//

}
