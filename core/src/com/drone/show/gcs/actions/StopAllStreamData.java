package com.drone.show.gcs.actions;

import com.drone.show.gcs.MavLinkToolKit;
import io.dronefleet.mavlink.MavlinkConnection;



public class StopAllStreamData extends MavlinkAction {

	public StopAllStreamData(MavlinkConnection connection) {
		super(connection, MavLinkToolKit.ALL_SYSTEM_ID);
	}

	
	@Override
	public void update() {
		super.update();
		
		if(this.isStopAllStreamData) {
			this.setFinished(true);
		}
	}
		
		
}
