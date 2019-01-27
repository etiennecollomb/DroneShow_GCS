package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.gcs.RealWorldModel;
import com.drone.show.generic.Tools;



public abstract class Action implements PropertyChangeListener {


	int droneID; //From 1 to N , 0 = All drones, -1 = No drones ie: action neutre comme WAIT
	boolean isAlive;
	private boolean isFinished;


	public Action()  {
		this(-1);
	}

	public Action(int droneID)  {

		this.droneID = droneID;
		this.isAlive = false;
		this.isFinished = false;
	}


	public void update() {

		/** permet de declencher le traitement des PropertyChangeEvent recus QUE si on est en cours d'execution de la mavlinkAction
		 * afin d eviter les effet de bords sur les mavlinkAction pas encore declenchés qui traiteraient l'information.
		 */
		if(!this.isAlive) {
			this.isAlive = true;

			this.startListeningEvents();
			Tools.writeLog("  ***Action*** : Starting (drone ID:" + this.droneID + ") => " + this.getClass().getName() );
		}

	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}


	protected void startListeningEvents() {
		/** All drone events */
		GlobalManager.realWorldModel.addPropertyChangeListener(this);
		
		/** Particular Drone Events */
		if(droneID > 0) {
			RealDroneModel realDroneModel = GlobalManager.realWorldModel.getRealDroneModel( droneID );
			realDroneModel.addPropertyChangeListener(this);
		}
	}

	protected void stopListeningEvents() {
		/** All drone events */
		GlobalManager.realWorldModel.removePropertyChangeListener(this);
		
		/** Particular Drone Events */
		if(droneID > 0) {
			RealDroneModel realDroneModel = GlobalManager.realWorldModel.getRealDroneModel( droneID );
			realDroneModel.removePropertyChangeListener(this);
		}
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

			this.stopListeningEvents();
			Tools.writeLog("  ***Action*** : Finished (drone ID:" + this.droneID + ") => " + this.getClass().getName() );
		}
	}




}
