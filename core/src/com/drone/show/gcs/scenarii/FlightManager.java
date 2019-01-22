package com.drone.show.gcs.scenarii;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.mavlinkaction.Arm;
import com.drone.show.gcs.mavlinkaction.PreArmCheck;
import com.drone.show.gcs.mavlinkaction.SetAutoMode;
import com.drone.show.gcs.mavlinkaction.SetStabilizeMode;

import io.dronefleet.mavlink.MavlinkConnection;

/** Gere un vol comprennat des missions
 * ie: pre check, arm , launch mission , land...etc
 */

public class FlightManager  implements PropertyChangeListener {

	
	MavlinkConnection connection;
	
	
	/** Les Mavlink Action associees */
	PreArmCheck preArmCheck;
	SetStabilizeMode setStabilizeMode;
	SetAutoMode setAutoMode;
	Arm arm;
	
	
	
	
	public FlightManager(MavlinkConnection connection) {
		
		GlobalManager.realWorldDroneModel.addPropertyChangeListener(this);
		
		this.connection = connection;
		
		this.preArmCheck = new PreArmCheck(this.connection);
		this.setStabilizeMode = new SetStabilizeMode(this.connection);
		this.setAutoMode = new SetAutoMode(this.connection);
		this.arm = new Arm(this.connection);
	}
	
	
	
	private void testArming() {
		
	}
	
	
	public void update() {
		
		
		if(!this.preArmCheck.isFinished) {
			this.preArmCheck.update();
		}
		else if(!this.setStabilizeMode.isFinished) {
			this.setStabilizeMode.update();
		}
		else if(!this.arm.isFinished) {
			this.arm.update();
		}
		else {
			/** Flight Finished */
		
		}
		
		
//		myTimeLine.add( new PreArmCheck(connection, droneModel));
//		myTimeLine.add( new SetHomeToCurrentLocation(connection, droneModel));
//		myTimeLine.add( new StabilizeMode(connection, droneModel));
//		myTimeLine.add( new Arm(connection, droneModel));
//		myTimeLine.add( new GuidedMode(connection, droneModel)); //On ne peut pas armer en Guided Mode, il faut le setter une fois arme
//
//		//TakeOff
//		myTimeLine.add( new TakeOffMove(connection, droneModel, takeOffAlt));
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
		
		
		/** http://python.dronekit.io/guide/auto_mode.html
		 * If the vehicle is in the air, then changing the mode to AUTO is all that is required to start the mission.
		 * Copter 3.3 release and later: If the vehicle is on the ground (only), 
		 * you will additionally need to send the MAV_CMD_MISSION_START command.
		 * 
		 * At the end of the mission the vehicle will enter LOITER mode 
		 */
		
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
		
	}
	
	
	
}
