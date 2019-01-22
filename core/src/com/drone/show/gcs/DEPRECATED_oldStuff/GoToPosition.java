package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.MavlinkAction.MavlinkActionType;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.MavCmd;
import io.dronefleet.mavlink.common.MavResult;

/**
 * Tous est decrit en m et en position relative par rapporta la home position
 */

public class GoToPosition extends MavlinkAction {

	private static final float HERTZ = 1f; //frequence d'envoie de la commande
	private static final float MINDISTANCE = 1; //distance en m du point qu on veut atteindre pour valider qu on est bien au point voulu

	float speed; //in centimeter/sec
	Vector3 targetPosition; //relative a la position de depart HOME du drone en m


	public GoToPosition(MavlinkConnection connection, MavlinkCommunicationModel droneModel, Vector3 targetPosition, float speed_) {
//		super(MavlinkActionType.GOTO_POSITION, connection, droneModel);
		super(null, connection, droneModel);

		this.setSpeed(speed_);
		this.setTargetPosition(targetPosition);

		this.setMavlinkMessage( MavLinkToolKit.setPositionTargetLocalNed( targetPosition.x, targetPosition.y, targetPosition.z ) );
		this.setSend_command_timer( System.currentTimeMillis() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));

	}




	@Override
	public void update(float deltaTime) {
		
		//On ne sait pas comment avoir l assurance que le msg mavlink a ete recu
		if(true) {

			if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
				this.setSend_command_timer( System.currentTimeMillis() );

				MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());

			}
		}

		//is finished or not?
//		float distance = this.getTargetPosition().dst(this.getDroneModel().getLocalPositionNed().x,
//				this.getDroneModel().getLocalPositionNed().y,
//				this.getDroneModel().getLocalPositionNed().z);
//		Tools.writeLog("***TimeLine*** : (GOTO_POSITION MOVE) distance " + distance + " (minDistance: " + MINDISTANCE + ")" );
//		if( distance < MINDISTANCE ) { //on est a moins de N metres du point voulu
//			this.setFinished(true);
//		}
	}


	public Vector3 getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector3 targetPosition) {
		this.targetPosition = targetPosition;
	}

	@Override
	public void onCommandAckReceived() {

	}


	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
