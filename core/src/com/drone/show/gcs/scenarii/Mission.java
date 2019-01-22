package com.drone.show.gcs.scenarii;

import java.util.ArrayList;
import java.util.List;

/** Une mission est une liste finie de Waypoints
 * (une succession de points pour arriver au dernier point,
 * qui fait partie d une forme)
 */
public class Mission {

	public List<Waypoint> waypoints;

	public Mission() {
		waypoints = new ArrayList<Waypoint>();
	}
	
	public void add(Waypoint waypoint) {
		this.waypoints.add(waypoint);
	}
}
