package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.Heartbeat;



public class SetGuidedMode extends MavlinkAction {

	
	
	public SetGuidedMode(MavlinkConnection connection, int droneID) {
		super(connection, droneID);
		
		this.mavlinkMessage = MavLinkToolKit.guidedMode(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.HEARTBEAT)){

			Heartbeat heartbeat = (Heartbeat)evt.getNewValue();
					
			if( heartbeat.customMode() == MavLinkToolKit.GUIDED_CUSTOM_MODE) {
				this.setFinished(true);
			}
			
		}
		
	}

}
