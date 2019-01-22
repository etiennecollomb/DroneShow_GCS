package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;



public class SetStabilizeMode extends MavlinkAction {

	
	
	public SetStabilizeMode(MavlinkConnection connection) {
		super(connection);
		
		this.mavlinkMessage = MavLinkToolKit.stabilizeMode();
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.MODE)){

			if( (Mode)evt.getNewValue() == Mode.STABILIZE) {
				this.setFinished(true);
			}
			
		}
		
	}

}
