package com.drone.show.hud.presenters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.drone.show.GlobalManager;
import com.drone.show.hud.models.ApplicationModel;


public abstract class Presenter implements PropertyChangeListener {


	private ApplicationModel applicationModel;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public Presenter(){
		this.applicationModel = GlobalManager.applicationModel;
		this.addPropertyChangeListener(applicationModel);
	}



	/**************************************
	 * 
	 * Property Change Support/Listener
	 * 
	 **************************************/

	/**************************************
	 * 
	 * Methods
	 * 
	 **************************************/

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	
	
	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public ApplicationModel getApplicationModel() {
		return applicationModel;
	}

	public void setApplicationModel(ApplicationModel applicationModel) {
		this.applicationModel = applicationModel;
	}

	public PropertyChangeSupport getPcs() {
		return pcs;
	}

}
