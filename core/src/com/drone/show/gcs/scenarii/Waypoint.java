package com.drone.show.gcs.scenarii;


/**
 * Un Waypoint est l element de base pour positionner un drone dans l espace
 */
public class Waypoint {
	
	public float X_in_meter;
	public float Y_in_meter;
	public float Z_in_meter;
	
	public Waypoint() {}
		
	public Waypoint(float X_in_meter, float Y_in_meter, float Z_in_meter) {
		this.X_in_meter = X_in_meter;
		this.Y_in_meter = Y_in_meter;
		this.Z_in_meter = Z_in_meter;
	}
		
}
