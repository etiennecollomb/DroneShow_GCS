package com.drone.show.hud.presenters;

import java.beans.PropertyChangeEvent;

import com.drone.show.hud.views.CheckboxView;

public class CheckboxPresenter extends Presenter {


	private CheckboxView checkboxView;
	private String event;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public CheckboxPresenter(CheckboxView buttonView, String event) {

		this.getApplicationModel().addPropertyChangeListener(this);

		this.setCheckboxView( buttonView );
		this.getCheckboxView().setButtonPresenter(this);

		this.setEvent( event );
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

	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	public void onClick(Object value) {
		this.getPcs().firePropertyChange(this.getEvent(), "", value);
	}


	public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();

		if (this.getEvent().equals(propertyName)) {
			this.getCheckboxView().switchState((Boolean) evt.getNewValue());
		}
	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public CheckboxView getCheckboxView() {
		return checkboxView;
	}

	public void setCheckboxView(CheckboxView checkboxView) {
		this.checkboxView = checkboxView;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
