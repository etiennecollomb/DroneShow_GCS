package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.generic.Tools;



public abstract class Action implements PropertyChangeListener {

	boolean isAlive;
	private boolean isFinished;


	public Action()  {

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
			Tools.writeLog("  ***Action*** : Starting => " + this.getClass().getName() );
		}

	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}




	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;

		/** if isFinished :
		 * on se remove du removePropertyChangeListener
		 * pour ne pas saturer les envoie de message et eviter effet de bords */
		if(this.isFinished) {
			GlobalManager.realWorldDroneModel.removePropertyChangeListener(this);
			Tools.writeLog("  ***Action*** : Finished => " + this.getClass().getName() );
		}
	}




}
