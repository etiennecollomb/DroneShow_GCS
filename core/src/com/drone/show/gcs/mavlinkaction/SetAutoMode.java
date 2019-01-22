package com.drone.show.gcs.mavlinkaction;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;



public class SetAutoMode extends MavlinkAction {

	
	
	public SetAutoMode(MavlinkConnection connection) {
		super(connection);
		
		this.mavlinkMessage = MavLinkToolKit.guidedMode();
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.MODE)){

			if( (Mode)evt.getNewValue() == Mode.AUTO) {
				this.isFinished = true;
			}
			
		}
		
	}

}
