package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;



public class SetLandMode extends MavlinkAction {

	
	
	public SetLandMode(MavlinkConnection connection) {
		super(connection);
		
		this.mavlinkMessage = MavLinkToolKit.landMode();
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.ARM_STATUS)){

			/** If disarmed, land is finished
			 * Arm is false
			 **/
			if( !(boolean)evt.getNewValue() ) {
				this.setFinished(true);
			}
			
		}
		
	}

}
