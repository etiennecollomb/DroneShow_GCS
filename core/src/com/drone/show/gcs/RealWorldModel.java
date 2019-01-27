package com.drone.show.gcs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class RealWorldModel {
	
	
	public static final String IS_STREAM_DATA = "IS_STREAM_DATA";
	
	

	/** a t on des stream data en cours?
	 * pour tout message autre que heartbeat recu : oui
	 **/
	private boolean isStreamData;
	
	public HashMap<Integer, RealDroneModel> realDroneModels = new HashMap<Integer, RealDroneModel>();

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	
	
	public RealDroneModel getRealDroneModel(int origiSystemID) {
		return realDroneModels.get(origiSystemID);
	}
		
	public void addRealDroneModel(int droneId, RealDroneModel realDroneModel) {
		realDroneModels.put(droneId, realDroneModel);
	}

	
	
	
	public boolean isStreamData() {
		return this.isStreamData;
	}

	public void setStreamData(boolean isStreamData) {
		this.pcs.firePropertyChange(RealWorldModel.IS_STREAM_DATA, null, isStreamData);
		this.isStreamData = isStreamData;
	}
	
	
	/**************************************
	 * 
	 * Property Change Support/Listener
	 * 
	 **************************************/

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
