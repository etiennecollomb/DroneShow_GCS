package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.MavResult;
import io.dronefleet.mavlink.common.ParamValue;



public class MissionClearAll extends MavlinkAction {

	public MissionClearAll(MavlinkConnection connection, int droneID) {
		super(connection, droneID);

		this.mavlinkMessage = MavLinkToolKit.missionClearAll(this.droneID);
	}


	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.PARAM_VALUE)){
			ParamValue paramValue = (ParamValue) evt.getNewValue();

			/** Permet de connaitre le nombre de waypoint deja load dans l'ardupilot */
			if(paramValue.paramId().equals("MIS_TOTAL")) {

				/** Permet de verifier qu on a bien tous les points clear */
				if(paramValue.paramValue() == 0f) {
					this.setFinished(true);
				}
			}

		}

	}
	
}
	
