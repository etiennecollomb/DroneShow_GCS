package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.MavCmdAck;
import io.dronefleet.mavlink.common.MavMissionResult;
import io.dronefleet.mavlink.common.MavResult;
import io.dronefleet.mavlink.common.MissionAck;



public class StartMission extends MavlinkAction {



	public StartMission(MavlinkConnection connection, int droneID) {
		super(connection, droneID);

		this.mavlinkMessage = MavLinkToolKit.missionStart(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.COMMAND_ACK)){

			CommandAck commandAck = (CommandAck) evt.getNewValue();

			//example : MissionAck{targetSystem=255, targetComponent=0, type=EnumValue{value=0, entry=MAV_MISSION_ACCEPTED}, missionType=null}
			if(commandAck.result().equals( MavResult.MAV_RESULT_ACCEPTED )) {
				this.setFinished(true);
			}

		}

	}
	
}
	
