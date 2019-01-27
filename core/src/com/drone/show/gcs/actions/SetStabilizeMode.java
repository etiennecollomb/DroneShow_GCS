package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.Heartbeat;



public class SetStabilizeMode extends MavlinkAction {



	public SetStabilizeMode(MavlinkConnection connection, int droneID) {
		super(connection, droneID);

		this.mavlinkMessage = MavLinkToolKit.stabilizeMode(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();
		
		if (propertyName.equals(RealDroneModel.HEARTBEAT)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());

			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				Heartbeat heartbeat = (Heartbeat)mavlinkMessage.getPayload();

				if( heartbeat.customMode() == MavLinkToolKit.STABILIZE_CUSTOM_MODE) {
					this.setFinished(true);
				}
			}
		}

	}

}
