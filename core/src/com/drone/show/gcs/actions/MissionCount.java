package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.MavResult;
import io.dronefleet.mavlink.common.MissionRequest;
import io.dronefleet.mavlink.common.ParamValue;



public class MissionCount extends MavlinkAction {

	public MissionCount(MavlinkConnection connection, int droneID, int count) {
		super(connection, droneID);

		this.mavlinkMessage = MavLinkToolKit.missionCount(this.droneID, count);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(RealDroneModel.MISSION_REQUEST)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());

			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				MissionRequest missionRequest = (MissionRequest)mavlinkMessage.getPayload();

				/** Si on recoit une mission request c'est que le missionCount a ete accepté */
				this.setFinished(true);
			}
		}
	}

}

