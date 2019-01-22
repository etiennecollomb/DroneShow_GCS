package com.drone.show.hud.presenters;

import java.beans.PropertyChangeEvent;

import com.drone.show.hud.views.StopWatchView;

public class StopWatchPresenter extends Presenter {


	private StopWatchView stopWatchView;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public StopWatchPresenter(StopWatchView stopWatchView) {

		this.getApplicationModel().addPropertyChangeListener(this);
		this.setStopWatchView( stopWatchView );
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

//		if (EventsCatalog.PAUSE_STOPWATCH.equals(propertyName)) {
//			this.getStopWatchView().setPauseWatch(true);
//		}
//		if (EventsCatalog.START_STOPWATCH.equals(propertyName)) {
//			this.getStopWatchView().setPauseWatch(false);
//		}
//		else if (EventsCatalog.SCREEN_STOPWATCH_TIME_INIT.equals(propertyName)) {
//			this.getStopWatchView().init( (float) evt.getNewValue() );
//		}
//		else if (EventsCatalog.SCREEN_STOPWATCH_TIME_REQUEST.equals(propertyName)) {
//			this.getPcs().firePropertyChange(EventsCatalog.SCREEN_STOPWATCH_TIME, "", this.getStopWatchView().getTime());
//		}

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public StopWatchView getStopWatchView() {
		return stopWatchView;
	}

	public void setStopWatchView(StopWatchView stopWatchView) {
		this.stopWatchView = stopWatchView;
	}

}
