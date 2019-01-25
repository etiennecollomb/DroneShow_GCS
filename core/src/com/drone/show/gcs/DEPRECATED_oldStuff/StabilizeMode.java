package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;


public class StabilizeMode extends MavlinkAction {

	private static final float HERTZ = 2.0f; //frequence d'envoie de la commande

	
	public StabilizeMode(MavlinkConnection connection, MavlinkCommunicationModel droneModel) {
		super(MavlinkActionType.STABILIZE_MODE, connection, droneModel);
//		this.setMavlinkMessage( MavLinkToolKit.stabilizeMode() );
		this.setSend_command_timer( System.currentTimeMillis() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));
	}

	@Override
	public void update(float deltaTime) {

		if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
			this.setSend_command_timer( System.currentTimeMillis() );
			
			MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());
		}
		
		if(this.getMavComModel().getMode() == Mode.STABILIZE) {
			this.setFinished( true );
		}
	}

	@Override
	public void onCommandAckReceived() {
		// TODO Auto-generated method stub
	}

}
