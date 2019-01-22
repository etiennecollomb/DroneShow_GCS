package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.badlogic.gdx.graphics.Color;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.MavlinkCommunicationModel.Mode;

import io.dronefleet.mavlink.MavlinkConnection;


public class LoiterMode extends MavlinkAction {

	private static final float HERTZ = 2.0f; //frequence d'envoie de la commande
	
	private long duration_in_ms;
	private long timer_in_ms;


	public LoiterMode(MavlinkConnection connection, MavlinkCommunicationModel droneModel, float duration_in_sec) {
		super(MavlinkActionType.LOITER_MODE, connection, droneModel);
		this.setMavlinkMessage( MavLinkToolKit.loiterMode() );
		this.setSend_command_timer( System.currentTimeMillis() );
		this.setSend_command_interval((long)(1000.0f/HERTZ));

		this.duration_in_ms = (long)(duration_in_sec*1000f);
		this.timer_in_ms = -1;
	}

	@Override
	public void update(float deltaTime) {

		if(this.timer_in_ms < 0) {
			this.timer_in_ms = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
			this.setSend_command_timer( System.currentTimeMillis() );

			//On ecrase les commande pour etre sur qu on soit toujours sur ce mode
			//"Recent versions of ArduCopter require you to set the channel overrides every seconds (or 3sec?)."
			//To do LOITER we need to perform ('rc 3 1500')
			
			MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.rc3ChannelOverride(1500));

			if(this.getMavComModel().getMode() != Mode.LOITER) {
				MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());
			}

		}

		//finished?
		if(System.currentTimeMillis() - this.timer_in_ms > this.duration_in_ms) {
			this.setFinished(true);
		}


	}

	@Override
	public void onCommandAckReceived() {
		// TODO Auto-generated method stub

	}






}
