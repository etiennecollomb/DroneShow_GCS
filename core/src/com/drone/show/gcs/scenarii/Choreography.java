package com.drone.show.gcs.scenarii;

import java.util.ArrayList;
import java.util.List;


/**
 * Une choreographie est un ensemble de missions joues en simultane
 * (une mission pour une ensemble de drone)
 * (une mission par drone)
 */
public class Choreography {

	public List<Mission> missions;

	public Choreography(){
		missions = new ArrayList<Mission>();
	}

	public void add(Mission mission) {
		this.missions.add(mission);
	}
	
	
}

