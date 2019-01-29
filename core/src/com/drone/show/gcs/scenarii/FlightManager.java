package com.drone.show.gcs.scenarii;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.gcs.actions.Arm;
import com.drone.show.gcs.actions.LoadChoreography;
import com.drone.show.gcs.actions.PreArmCheck;
import com.drone.show.gcs.actions.SetAutoMode;
import com.drone.show.gcs.actions.SetLandMode;
import com.drone.show.gcs.actions.SetStabilizeMode;
import com.drone.show.gcs.actions.StartMission;
import com.drone.show.gcs.actions.Wait;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;

/** Gere un vol comprenant des missions
 * ie: pre-check, arm , launch mission , land...etc
 */

public class FlightManager  implements PropertyChangeListener {


	MavlinkConnection connection;
	TimeLine timeLine;


	public FlightManager(MavlinkConnection connection) {

		this.connection = connection;
		this.timeLine = new TimeLine();
	}



	public void update() {

		if(!this.timeLine.isFinished) {
			this.timeLine.update();
		}
		else {
			/** Flight Finished */
			Tools.writeLog("Flight Finished : OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  OKOKOK  ");
		}
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
	
	

	public void setTestArming(int droneID) {

		timeLine.reset();

		timeLine.add( new SetStabilizeMode(connection, droneID));
		timeLine.add( new PreArmCheck(connection, droneID));
		timeLine.add( new Arm(connection, droneID));
		timeLine.add( new Wait(3000f)); //3 sec
		timeLine.add( new SetLandMode(connection, droneID));
		
	}



	public void setMissionUpload() {
	
		/** 
		 * Origine Point
		 */
		float origLatitude = 22.275667f;
		float origLongitude = 114.173414f;
		/**
		 * Choreography
		 */
		LoadChoreography loadChoreography = new LoadChoreography(this.connection, "choreographies/1_Drone_001.json", origLatitude, origLongitude);
		
		timeLine.reset();
	
		/** to load choreagraphy
		 * fisrt set mode Stabilize
		 */
		timeLine.add( loadChoreography );
		
		
	}
		
	
	
	public void setStartMission() {
		
		/** To start a mission,
		 * Arm first
		 * then set mode Auto
		 * then start misssion
		 * cf condition in ardupilot code cui dessous (https://github.com/ArduPilot/ardupilot/blob/master/ArduCopter/GCS_Mavlink.cpp)
		 */

		/*
		 #if MODE_AUTO_ENABLED == ENABLED
	    	case MAV_CMD_MISSION_START:
	        if (copter.motors->armed() && copter.set_mode(AUTO, MODE_REASON_GCS_COMMAND)) {
           		copter.set_auto_armed(true);
	            if (copter.mode_auto.mission.state() != AP_Mission::MISSION_RUNNING) {
	                copter.mode_auto.mission.start_or_resume();
	            }
	            return MAV_RESULT_ACCEPTED;
        		}
        return MAV_RESULT_FAILED;
		#endif
		*/
		
		timeLine.reset();
		//To ALL ...
		timeLine.add( new SetStabilizeMode(this.connection, 0));
		timeLine.add( new Arm(this.connection, 0));
		timeLine.add( new SetAutoMode(this.connection, 0));
		timeLine.add( new StartMission(this.connection, 0));
		
	}
		
}
