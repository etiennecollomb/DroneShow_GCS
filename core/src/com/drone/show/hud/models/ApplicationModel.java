package com.drone.show.hud.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;
import com.drone.show.MyDroneShow;
import com.drone.show.gcs.GCSThread;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.actions.LoadChoreography;
import com.drone.show.hud.popups.ScenariosPopup;
import com.drone.show.hud.popups.TelemetryPopup;

import io.dronefleet.mavlink.common.GpsRawInt;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.MavModeFlag;

public class ApplicationModel implements PropertyChangeListener {

	

	/***********************
	 * Application parameters
	 ***********************/

	private boolean isSoundOption;


	/**************************************
	 *
	 * PROPERTY CHANGE SUPPORT/LISTENER
	 *
	 **************************************/

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		
		String propertyName = evt.getPropertyName();
		if(GlobalManager.ISDEBUG) System.out.println("ApplicationModel received propertyName : "+propertyName);

		/** Telemetry Popup */
		if (propertyName.equals(MavlinkCommunicationModel.HEARTBEAT)){
			Heartbeat heartbeat = (Heartbeat)evt.getNewValue();
			
			/** Mode Text */
			if(heartbeat.customMode() == MavLinkToolKit.STABILIZE_CUSTOM_MODE) {
				this.pcs.firePropertyChange(TelemetryPopup.MODE_TEXT, null, "Stabilized");
			}
			else if(heartbeat.customMode() == MavLinkToolKit.GUIDED_CUSTOM_MODE) {
				this.pcs.firePropertyChange(TelemetryPopup.MODE_TEXT, null, "Guided");
			}
			else if(heartbeat.customMode() == MavLinkToolKit.LOITER_CUSTOM_MODE) {
				this.pcs.firePropertyChange(TelemetryPopup.MODE_TEXT, null, "Loiter");
			}
			else if(heartbeat.customMode() == MavLinkToolKit.AUTO_CUSTOM_MODE) {
				this.pcs.firePropertyChange(TelemetryPopup.MODE_TEXT, null, "Auto");
			}
			
			/** Arm Text */
			if(heartbeat.baseMode().flagsEnabled( MavModeFlag.MAV_MODE_FLAG_SAFETY_ARMED )) {
				this.pcs.firePropertyChange(TelemetryPopup.ARM_STATUS_TEXT, null, "True");
			}
			else {
				this.pcs.firePropertyChange(TelemetryPopup.ARM_STATUS_TEXT, null, "False");
			}
			
		}
		else if (propertyName.equals(MavlinkCommunicationModel.GPS_RAW_INT)){
			
			GpsRawInt gpsRawInt = (GpsRawInt)evt.getNewValue();

			/** Number Of Satellite */
			this.pcs.firePropertyChange(TelemetryPopup.NUMBER_OF_SATTELITES_TEXT, null, ""+gpsRawInt.satellitesVisible() );

			/** GPS Fix Type */
			this.pcs.firePropertyChange(TelemetryPopup.GPS_FIX_TYPE_TEXT, null, ""+gpsRawInt.fixType().entry().name());
			
		}
		else if (propertyName.equals(MavlinkCommunicationModel.STATUS)){
			this.pcs.firePropertyChange(TelemetryPopup.STATUS_TEXT, ""+evt.getOldValue(), ""+evt.getNewValue());
		}
		else if (propertyName.equals(MavlinkCommunicationModel.EKF_STATUS)){
			this.pcs.firePropertyChange(TelemetryPopup.EKF_STATUS_TEXT, evt.getOldValue(), evt.getNewValue());
		}
		
		/** Scenario Popup */
		else if (propertyName.equals(ScenariosPopup.LAUNCH_SCENARIO)){

			
//			/**
//			 * 
//			 *  GCS TEMP ARCHI
//			 *  TEST of thread 
//			 * 
//			 * 
//			 */
//
//			
//			
//			//Get data from thread
//			System.out.println("START GCS");
//			try {
//				while(true) {
//					String info = queue.take();
//					System.out.println("Msg received: "+info);
////					if () {
////						continue; //Stop
////					}
//				}
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}
//			System.out.println("STOP GCS");

		}

	}

}