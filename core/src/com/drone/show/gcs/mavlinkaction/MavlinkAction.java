package com.drone.show.gcs.mavlinkaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.generic.Timer;
import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;



public abstract class MavlinkAction implements PropertyChangeListener {


	private static final float HERTZ = 5.0f; //frequence d'envoie de la commande
	Timer commandTimer;

	MavlinkConnection connection;
	MavlinkMessage mavlinkMessage;
	int streamDataID; //l'id de la stream data qui envoie les infos lie a l action pour permettre de savoir si l action est finie ou pas
	boolean isStopAllStreamData, isStreamData; //utiliser pour permettre de recevoir uniquement la stream data associee
	boolean isAlive;

	public boolean isFinished;




	public MavlinkAction(MavlinkConnection connection)  {

		this.connection = connection;
		this.mavlinkMessage = null;
		this.streamDataID = -1;
		this.isStopAllStreamData = false;
		this.isStreamData = false;
		this.commandTimer = new Timer((long)(1000.0f/HERTZ));
		this.isAlive = false;
		this.isFinished = false;
	}


	public void update() {

		/** permet de declencher le traitement des PropertyChangeEvent recus QUE si on est en cours d'execution de la mavlinkAction
		 * afin d eviter les effet de bords sur les mavlinkAction pas encore declenchés qui traiteraient l'information.
		 */
		if(!this.isAlive) {
			this.isAlive = true;
			GlobalManager.realWorldDroneModel.addPropertyChangeListener(this);
		}
		
		if(!this.isFinished) {

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
		else {
			/** if isfinished :
			 * on se remove du removePropertyChangeListener
			 * pour ne pas saturer les envoie de message et eviter effet de bords */
			GlobalManager.realWorldDroneModel.removePropertyChangeListener(this);
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


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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



}
