package com.drone.show.gcs.DEPRECATED_oldStuff.scenarii;

import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.DEPRECATED_oldStuff.InitLed;
import com.drone.show.gcs.DEPRECATED_oldStuff.RedToGreenToBlueLedColor;
import com.drone.show.gcs.DEPRECATED_oldStuff.Scenario;
import com.drone.show.gcs.DEPRECATED_oldStuff.TimeLine;

import io.dronefleet.mavlink.MavlinkConnection;


/**
 * Led TEST
 *
 */
public class Scenario01 {

	public static Scenario getScenerio(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
		
		TimeLine myTimeLine = new TimeLine();
		
		//LED Init
		myTimeLine.add( new InitLed(connection, droneModel));
		myTimeLine.add( new RedToGreenToBlueLedColor(connection, droneModel));

//		myTimeLine.flush();
		return new Scenario(myTimeLine);

		
	}
	
}
