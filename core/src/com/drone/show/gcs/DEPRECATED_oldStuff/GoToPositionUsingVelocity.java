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

public class GoToPositionUsingVelocity extends MavlinkAction {

	/** NE DOIT PAS ETRE EN DESSOUS DE 1HZ, sinon le drone annule la velocity **/
	private static final float HERTZ = 5f; //frequence d'envoie de la commande, le drone se mets a l'arret apres quelques secondes inactives (3s?)
	private static final float MINDISTANCE = 1; //distance en m du point qu on veut atteindre pour valider qu on est bien au point voulu
	
	float speed; //in m/s
	Vector3 targetPositionNed; //relative a la position de depart HOME du drone en m

	float decreaseSpeedDistance = 5f; //distance de la target a partir de laquelle on reduit la vitesse

	
	
	public GoToPositionUsingVelocity(MavlinkConnection connection, MavlinkCommunicationModel droneModel, Vector3 targetPosition, float speed_) {
//		super(MavlinkActionType.GOTO_POSITION_USING_VELOCITY, connection, droneModel);
		super(null, connection, droneModel);

		this.setSpeed(speed_);
		this.setTargetPosition(targetPosition);

		this.setSend_command_timer( System.currentTimeMillis() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));

	}



	@Override
	public void update(float deltaTime) {

//		//On fait une correction a chaque envoie de commande, en fonction de la distance
//		
//		//is finished or not?
//		float distanceX = targetPositionNed.x - this.getDroneModel().getLocalPositionNed().x;
//		float distanceY = targetPositionNed.y - this.getDroneModel().getLocalPositionNed().y;
//		float distanceZ = targetPositionNed.z - this.getDroneModel().getLocalPositionNed().z;
//		
//		float speedX = 0;
//		float speedY = 0;
//		float speedZ = 0;
//		
//		//X
//		if(distanceX > decreaseSpeedDistance)
//			speedX = 1;
//		else
//			speedX = distanceX/decreaseSpeedDistance;
//		
//		//Y
//		if(distanceY > decreaseSpeedDistance) speedY = 1;
//		else speedY = distanceY/decreaseSpeedDistance;
//		
//		//Z
//		if(distanceZ > decreaseSpeedDistance) speedZ = 1;
//		else speedZ = distanceZ/decreaseSpeedDistance;
//		
//
//		Vector3 velocity = new Vector3(speedX, speedY, speedZ);
//		velocity.scl(this.getSpeed());
//		
//		Tools.writeLog("x:"+velocity.x+" y:"+velocity.y+" z:"+velocity.z);
//		
//		//Is it finished?
//		float distance = targetPositionNed.dst(this.getDroneModel().getLocalPositionNed().x,
//				this.getDroneModel().getLocalPositionNed().y,
//				this.getDroneModel().getLocalPositionNed().z);
//		Tools.writeLog("***TimeLine*** : (GOTO_POSITION_USING_VELOCITY MOVE) distance " + distance + " (minDistance: " + MINDISTANCE + ")" );
//		if( distance < MINDISTANCE ) { //on est a moins de N metres du point voulu
//			this.setFinished(true);
//		}
//
//
//		
//		if(true) {
//
//			if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
//				this.setSend_command_timer( System.currentTimeMillis() );
//
//				MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setVelocityTargetLocalNed( velocity.x, velocity.y, velocity.z ) );
//
//			}
//		}


	}


	public Vector3 getTargetPosition() {
		return targetPositionNed;
	}

	public void setTargetPosition(Vector3 targetPosition) {
		this.targetPositionNed = targetPosition;
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
