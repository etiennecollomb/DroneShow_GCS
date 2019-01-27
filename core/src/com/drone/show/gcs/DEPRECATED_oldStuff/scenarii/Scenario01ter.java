package com.drone.show.gcs.DEPRECATED_oldStuff.scenarii;

import com.drone.show.gcs.RealDroneModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.Arm;
import com.drone.show.gcs.DEPRECATED_oldStuff.GuidedMode;
import com.drone.show.gcs.DEPRECATED_oldStuff.InitLed;
import com.drone.show.gcs.DEPRECATED_oldStuff.LandMode;
import com.drone.show.gcs.DEPRECATED_oldStuff.LoiterMode;
import com.drone.show.gcs.DEPRECATED_oldStuff.PreArmCheck;
import com.drone.show.gcs.DEPRECATED_oldStuff.Scenario;
import com.drone.show.gcs.DEPRECATED_oldStuff.SetHomeToCurrentLocation;
import com.drone.show.gcs.DEPRECATED_oldStuff.StabilizeMode;
import com.drone.show.gcs.DEPRECATED_oldStuff.TakeOffMove;
import com.drone.show.gcs.DEPRECATED_oldStuff.TimeLine;
import com.drone.show.generic.ColorsCatalog;

import io.dronefleet.mavlink.MavlinkConnection;


/**
 * Infinite loiter mode after takeoff
 *
 */
public class Scenario01ter {

//	public static Scenario getScenerio(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
//		
//		TimeLine myTimeLine = new TimeLine();
//		
//		//LED Init
//		myTimeLine.add( new InitLed(connection, droneModel));
//		
//		//Arming
//		myTimeLine.add( new PreArmCheck(connection, droneModel));
//		myTimeLine.add( new SetHomeToCurrentLocation(connection, droneModel));
//		myTimeLine.add( new StabilizeMode(connection, droneModel));
//		myTimeLine.add( new Arm(connection, droneModel));
//
////		myTimeLine.flush();
//		return new Scenario(myTimeLine);
//
//		
//	}
	
}
