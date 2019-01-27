package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.MavResult;



public class StartMission extends MavlinkAction {

	public StartMission(MavlinkConnection connection, int droneID) {
		super(connection, droneID);

		this.mavlinkMessage = MavLinkToolKit.missionStart(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(RealDroneModel.COMMAND_ACK)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());

			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				CommandAck commandAck = (CommandAck)mavlinkMessage.getPayload();

				//example : MissionAck{targetSystem=255, targetComponent=0, type=EnumValue{value=0, entry=MAV_MISSION_ACCEPTED}, missionType=null}
				if(commandAck.result().equals( MavResult.MAV_RESULT_ACCEPTED )) {
					this.setFinished(true);
				}
			}
		}

	}

}

