package com.drone.show.gcs.DEPRECATED_oldStuff;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import io.dronefleet.mavlink.MavlinkConnection;


/** Must be sent to only ONE DRONE avec le bon NODE ID qui correspond au drone selectionne sur l interface */
public class RequestStreamData extends MavlinkAction {

	private static final float HERTZ = 5.0f; //frequence d'envoie de la commande

	int streamDataID;


	public RequestStreamData(MavlinkConnection connection, MavlinkCommunicationModel droneModel, int streamDataID_) {

		super(MavlinkActionType.REQUEST_DATA_STREAM, connection, droneModel);

		this.streamDataID = streamDataID_;
		
//		this.setMavlinkMessage( MavLinkToolKit.requestStreamData(this.streamDataID) );
		this.setSend_command_interval((long)(1000.0f/HERTZ));

	}

	@Override
	public void update(float deltaTime) {


		if(!this.isCommandAcknowledge()) {

			if(System.currentTimeMillis() - this.getSend_command_timer() > this.getSend_command_interval()) {
				this.setSend_command_timer( System.currentTimeMillis() );

				MavLinkToolKit.sendCommand(this.getConnection(), this.getMavlinkMessage());

			}	
		}
		
		//TODO : comment valider que c est fini? au bout de n retry?

	}


	@Override
	public void onCommandAckReceived() {
	}

}
