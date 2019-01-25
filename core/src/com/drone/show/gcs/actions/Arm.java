package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;



public class Arm extends MavlinkAction {

	
	
	public Arm(MavlinkConnection connection, int droneID) {
		super(connection, droneID);
		
		this.mavlinkMessage = MavLinkToolKit.arm(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.ARM_STATUS)){

			/** Arm is true */
			if( (boolean)evt.getNewValue() ) {
				this.setFinished(true);
			}
			
		}
		
	}

}
