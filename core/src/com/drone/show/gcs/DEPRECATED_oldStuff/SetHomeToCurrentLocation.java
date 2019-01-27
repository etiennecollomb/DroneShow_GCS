package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.MavCmd;
import io.dronefleet.mavlink.common.MavResult;


public class SetHomeToCurrentLocation extends MavlinkAction {

	private static final float HERTZ = 2.0f; //frequence d'envoie de la commande

	public SetHomeToCurrentLocation(MavlinkConnection connection, RealDroneModel droneModel) {
		super(MavlinkActionType.SET_HOME_TO_CURRENT_LOCATION, connection, droneModel);
//		this.setMavlinkMessage( MavLinkToolKit.setHomeToCurrentLocation() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));
	}


	@Override
	public void update(float deltaTime) {

		if(this.isCommandAcknowledge()) {
			//finished?
			this.setFinished(true);
		}
		//sinon on envoie la command
		else if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
			this.setSend_command_timer( System.currentTimeMillis() );

			MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());
			//TEST
			//MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.requestHomePosition());
		}
		
	}

	@Override
	public void onCommandAckReceived() {
		
		//on recoit un CommandAck du bon type
		if(this.getCommandAck().command().entry() == MavCmd.MAV_CMD_DO_SET_HOME) {
			//Command accepted
			if(this.getCommandAck().result().entry() == MavResult.MAV_RESULT_ACCEPTED) {
				this.setCommandAcknowledge(true);
			}
		}
		
		
		
		
		
	}

}
