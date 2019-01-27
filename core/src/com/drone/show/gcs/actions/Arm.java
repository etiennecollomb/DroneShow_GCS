package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.MavModeFlag;



public class Arm extends MavlinkAction {

	
	
	public Arm(MavlinkConnection connection, int droneID) {
		super(connection, droneID);
		
		this.mavlinkMessage = MavLinkToolKit.arm(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.HEARTBEAT)){
			
			Heartbeat heartbeat = (Heartbeat)evt.getNewValue();
			boolean isArmed = ( heartbeat.baseMode().flagsEnabled( MavModeFlag.MAV_MODE_FLAG_SAFETY_ARMED ) );
			
			/** Arm is true */
			if( isArmed ) {
				this.setFinished(true);
			}
			
		}
		
	}

}
