package com.drone.show.gcs.scenarii;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.actions.Arm;
import com.drone.show.gcs.actions.LoadChoreography;
import com.drone.show.gcs.actions.PreArmCheck;
import com.drone.show.gcs.actions.SetAutoMode;
import com.drone.show.gcs.actions.SetLandMode;
import com.drone.show.gcs.actions.SetStabilizeMode;
import com.drone.show.gcs.actions.StartMission;
import com.drone.show.gcs.actions.StopAllStreamData;
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
	
	

	public void setTestMissionUploadAndArming(int droneID) {

		/** For testing Mission Upload
		 * Hong Kong, Appart Matthieu (2001)
		 */
		float origLatitude = 22.275667f;
		float origLongitude = 114.173414f;
		LoadChoreography loadChoreography = new LoadChoreography(this.connection, "choreographies/choreoPattern_1_Drone_001.json", origLatitude, origLongitude);
		
		timeLine.reset();

		timeLine.add( new SetStabilizeMode(connection, droneID));
		timeLine.add( loadChoreography );
		timeLine.add( new PreArmCheck(connection, droneID));
		timeLine.add( new Arm(connection, droneID));
		timeLine.add( new Wait(3000f)); //3 sec
		timeLine.add( new SetLandMode(connection, droneID));
		
	}



	public void setTestMissionUploadAndLaunch() {
	
		/** For testing Mission Upload
		 * Hong Kong, Appart Matthieu (2001)
		 */
		float origLatitude = 22.275667f;
		float origLongitude = 114.173414f;
		LoadChoreography loadChoreography = new LoadChoreography(this.connection, "choreographies/choreoPattern_2_Drones_001.json", origLatitude, origLongitude);
		
		timeLine.reset();
		
		//TEST
//		timeLine.add( new SetStabilizeMode(connection, 2));
//		timeLine.add( new SetLandMode(connection, 2));
		//FIN TEST

		/** to load choreagraphy
		 * fisrt set mode Stabilize
		 */
		//timeLine.add( new SetStabilizeMode(connection, MavLinkToolKit.ALL_SYSTEM_ID));
		timeLine.add( loadChoreography );
		
		/** To start a mission,
		 * first set mode Auto
		 * second start misssion
		 */
//		timeLine.add( new SetAutoMode(this.connection, droneID));
//		timeLine.add( new StartMission(this.connection, droneID));
	}
		
		
}
