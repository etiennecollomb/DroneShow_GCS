package com.drone.show.hud.presenters;

import java.beans.PropertyChangeEvent;

import com.drone.show.hud.views.TextView;

public class TextPresenter extends Presenter {


	private TextView textView;
	private String event;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public TextPresenter(TextView textView, String event) {

		this.getApplicationModel().addPropertyChangeListener(this);

		this.setTextView( textView );
		this.getTextView().setTextViewPresenter(this);

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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		
		if (propertyName.equals(this.getEvent())) {
			this.getTextView().setText( (String) evt.getNewValue());
		}
	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}


}
