package com.drone.show.world.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class DroneModel {

	public static final float MAX_VELOCITY = 10f; // en m/s
	
	final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
 
	public int id;
	public Color ledColor;

	public Vector3 homePosition;

	public Vector3 currentPosition;
	
	public float speed; //en m/s
	public Vector3 currentVelocity;

	public Vector3 rotation; //du drone dans l espace

	

	public DroneModel(int id, float speed) {
		this(id, speed, new Vector3(0,0,0),  new Vector3(0,0,0));
	}
	
	public DroneModel(int id, float speed, Vector3 startPosition) {
		this(id, speed, startPosition,  new Vector3(0,0,0));
	}

	public DroneModel(int id, float speed, Vector3 startPosition, Vector3 startVelocity) {
		this.id = id;
		this.speed = speed;
		this.ledColor = new Color(Color.ORANGE);
		this.homePosition = startPosition;
		this.currentPosition = startPosition;
		this.currentVelocity = startVelocity;
		this.rotation = new Vector3();
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


	
}
