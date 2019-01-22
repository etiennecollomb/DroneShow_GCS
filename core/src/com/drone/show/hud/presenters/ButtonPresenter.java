package com.drone.show.hud.presenters;

import java.beans.PropertyChangeEvent;

import com.drone.show.hud.views.ButtonView;


public class ButtonPresenter extends Presenter {


	private ButtonView buttonView;
	private String event;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public ButtonPresenter(ButtonView buttonView, String event) {
		super();

		this.setButtonView( buttonView );
		this.getButtonView().setButtonPresenter(this);

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
		this.getPcs().firePropertyChange(this.getEvent(), false, value);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public ButtonView getButtonView() {
		return buttonView;
	}

	public void setButtonView(ButtonView buttonView) {
		this.buttonView = buttonView;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
