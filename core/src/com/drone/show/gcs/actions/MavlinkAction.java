package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.generic.Timer;
import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;



public abstract class MavlinkAction extends Action implements PropertyChangeListener {


	private static final float HERTZ = 5.0f; //frequence d'envoie de la commande
	Timer commandTimer;

	MavlinkConnection connection;
	MavlinkMessage mavlinkMessage;
	int streamDataID; //l'id de la stream data qui envoie les infos lie a l action pour permettre de savoir si l action est finie ou pas
	boolean isStopAllStreamData, isStreamData; //utiliser pour permettre de recevoir uniquement la stream data associee


	public MavlinkAction(MavlinkConnection connection)  {
		super();
		
		this.connection = connection;
		this.mavlinkMessage = null;
		this.streamDataID = -1;
		this.isStopAllStreamData = false;
		this.isStreamData = false;
		this.commandTimer = new Timer((long)(1000.0f/HERTZ));
	}


	/** 
	 * avant d envoyer la commande
	 * on demande le bon fux d information (si besoin)
	 * en disant a tous les autres de ce taire en premier lieu
	 */
	@Override
	public void update() {
		super.update();
		
		if(!this.isFinished()) {

			/** On stop toutes les stream Data */
			if(!this.isStopAllStreamData) {
				this.stopAllStreamData();
			}
			/** On demande la bonne stream data */
			else if(!this.isStreamData && this.streamDataID >= 0) {
				this.requestStreamData();
			}
			/** On peut envoyer la commande principale */
			else if(this.mavlinkMessage != null) {
				this.sendCommand();
			}
		}
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.IS_STREAM_DATA)){

			/** No stream Data */
			if( !(boolean)evt.getNewValue() ) {
				this.isStopAllStreamData = true;
			}
			/** There is Stream Data */
			else {
				/** Que si on a deja Stop les stream */
				if(this.isStopAllStreamData) {
					this.isStreamData = true;
				}
			}
		}

	}

	
	

	private void stopAllStreamData() {
		if(this.commandTimer.isFinished()) {
			this.commandTimer.reset();
			MavLinkToolKit.sendCommand(this.connection, MavLinkToolKit.stopAllStreamData());
		}
	}

	private void requestStreamData() {
		if(this.commandTimer.isFinished()) {
			this.commandTimer.reset();
			MavLinkToolKit.sendCommand(this.connection, MavLinkToolKit.requestStreamData(this.streamDataID));
		}
	}

	private void sendCommand() {
		if(this.commandTimer.isFinished()) {
			this.commandTimer.reset();
			MavLinkToolKit.sendCommand(this.connection, this.mavlinkMessage);
		}	

	}




}
