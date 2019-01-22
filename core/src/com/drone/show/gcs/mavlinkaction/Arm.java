package com.drone.show.gcs.mavlinkaction;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;



public class Arm extends MavlinkAction {

	
	
	public Arm(MavlinkConnection connection) {
		super(connection);
		
		this.mavlinkMessage = MavLinkToolKit.arm();
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.ARM_STATUS)){

			/** Arm is true */
			if( (boolean)evt.getNewValue() ) {
				this.isFinished = true;
			}
			
		}
		
	}

}
