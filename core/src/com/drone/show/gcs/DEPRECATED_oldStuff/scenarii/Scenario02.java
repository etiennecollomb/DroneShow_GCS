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
public class Scenario02 {

//	public static Scenario getScenerio(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
//
//		float takeOffAlt = 2.0f; //en m
//		float loiterModeTimer = 999999999999999.0f; //en sec
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
//		myTimeLine.add( new GuidedMode(connection, droneModel)); //On ne peut pas armer en Guided Mode, il faut le setter une fois arme
//
//		//TakeOff
//		myTimeLine.add( new TakeOffMove(connection, droneModel, takeOffAlt));
//		
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
//		myTimeLine.add( new GuidedMode(connection, droneModel));
//
//		myTimeLine.add( new LandMode(connection, droneModel));
//
////		myTimeLine.flush();
//		return new Scenario(myTimeLine);
//
//		
//	}
	
}
