package com.drone.show.world.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.math.Vector3;


public class WorldModel implements PropertyChangeListener {

	Vector3 realWorldOrigin;
	boolean isRealWorldOriginInitialized;

	Map<Integer, DroneModel> drones = new ConcurrentHashMap<Integer, DroneModel>();

	final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public WorldModel() {
		this.setRealWorldOriginInitialized(false);
	}

	public static final String NEW_DRONE = "newDrone";

	private DroneModel addDrone(int id, float speed) {
		return this.addDrone(id, speed, new Vector3(0,0,0), new Vector3(0,0,0));
	}

	public DroneModel addDrone(int id, float speed, Vector3 position, Vector3 velocity) {
		DroneModel drone = new DroneModel(id, speed);
		drone.addPropertyChangeListener(this);
		this.getDrones().put(drone.id, drone);
		drone.currentPosition = position;
		drone.currentVelocity = velocity;
		this.pcs.firePropertyChange(WorldModel.NEW_DRONE, "", drone);
		
		return drone;
	}


	public void updateDroneCurrentPosition(int droneId, Vector3 position) {

		DroneModel drone = getDroneById(droneId);
		drone.currentPosition = position;
	}


	public DroneModel getDroneById(int droneId) {
		return drones.get(droneId);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
//		String propertyName = evt.getPropertyName();
//		if ( propertyName.equals(RealWorldDroneModel.DRONE_POSITION) ) {
//			this.pcs.firePropertyChange(evt);
//		}
		
	}

	public Map<Integer, DroneModel> getDrones() {
		return drones;
	}


	public Vector3 getRealWorldOrigin() {
		return realWorldOrigin;
	}

	public void setRealWorldOrigin(Vector3 realWorldOrigin) {
		this.realWorldOrigin = realWorldOrigin;
	}

	public boolean isRealWorldOriginInitialized() {
		return isRealWorldOriginInitialized;
	}

	public void setRealWorldOriginInitialized(boolean isRealWorldOriginInitialized) {
		this.isRealWorldOriginInitialized = isRealWorldOriginInitialized;
	}



}
