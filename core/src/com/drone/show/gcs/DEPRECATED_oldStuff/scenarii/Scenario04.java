package com.drone.show.gcs.DEPRECATED_oldStuff.scenarii;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.Arm;
import com.drone.show.gcs.DEPRECATED_oldStuff.GoToPosition;
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
 * 20 waypoints
 * avec led couleur
 *
 */
public class Scenario04 {

//	public static Scenario getScenerio(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
//
//		float takeOffAlt = 5.0f; //en m
//		float loiterModeTimer = 3.0f; //en sec
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
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
//		myTimeLine.add( new GuidedMode(connection, droneModel));
//
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-6f,0f,5f), 1f)); //1
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-6f,0f,10f), 1f)); //2
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(0f,0f,5f), 1f)); //3
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(6f,0f,10f), 1f)); //4
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(6f,0f,5f), 1f)); //5
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(0f,0f,5f), 1f)); //6
//		
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
//		myTimeLine.add( new GuidedMode(connection, droneModel));
//		
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(0f,0f,10f), 1f)); //7
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-6f,0f,10f), 1f)); //8
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-6f,0f,13f), 1f)); //9
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(6f,0f,13f), 1f)); //10
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(6f,0f,10f), 1f)); //11
//
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
//		myTimeLine.add( new GuidedMode(connection, droneModel));
//		
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(4f,0f,13f), 1f)); //12
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(3f,0f,10f), 1f)); //13
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(2f,0f,13f), 1f)); //14
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(0f,0f,10f), 1f)); //15
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-2f,0f,13f), 1f)); //16
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-3f,0f,10f), 1f)); //17
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-4f,0f,13f), 1f)); //18
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(-6f,0f,10f), 1f)); //19
//
//		myTimeLine.add( new LoiterMode(connection, droneModel, loiterModeTimer));
//		myTimeLine.add( new GuidedMode(connection, droneModel));
//		
//		myTimeLine.add( new GoToPosition(connection, droneModel, new Vector3(0f,0f,10f), 1f)); //20
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
