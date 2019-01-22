package com.drone.show.gcs.scenarii;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.actions.Arm;
import com.drone.show.gcs.actions.PreArmCheck;
import com.drone.show.gcs.actions.SetAutoMode;
import com.drone.show.gcs.actions.SetLandMode;
import com.drone.show.gcs.actions.SetStabilizeMode;
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
	
	

	public void setTestArming() {

		timeLine.reset();
		
		timeLine.add( new PreArmCheck(connection));
		timeLine.add( new SetStabilizeMode(connection));
		timeLine.add( new Arm(connection));
		timeLine.add( new Wait(3000f));
		timeLine.add( new SetLandMode(connection));
	}



}
