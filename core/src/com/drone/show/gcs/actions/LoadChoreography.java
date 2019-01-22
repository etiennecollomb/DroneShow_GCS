package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.gcs.scenarii.Choreography;
import com.drone.show.gcs.scenarii.Waypoint;
import com.drone.show.generic.Timer;
import com.drone.show.generic.Tools;
import com.drone.show.hud.models.ApplicationModel;
import com.drone.show.hud.popups.TelemetryPopup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.CommandAck;
import io.dronefleet.mavlink.common.HomePosition;
import io.dronefleet.mavlink.common.MavCmd;
import io.dronefleet.mavlink.common.MavMissionResult;
import io.dronefleet.mavlink.common.MavResult;
import io.dronefleet.mavlink.common.MissionAck;
import io.dronefleet.mavlink.common.MissionRequest;
import io.dronefleet.mavlink.common.ParamValue;

/**
 * Load choreogrphy to N Drones
 */

public class LoadChoreography extends Action implements PropertyChangeListener{

	
	private MavlinkConnection connection;

	private static final float HERTZ = 5.0f; //frequence d'envoie de la commande
	private Timer commandTimer;
	/** Pour etre sur qu on recoit pas des MissionAck de la commande precedent MissionClear
	 * alors qu on est sur la sequence REQUEST_ITEM qui retourne un MissionAck aussi
	 */
	private Timer missionClearTimer;


	/** droneID = nodeID de la radio 3DR associee
	 * Id current auquel on envoie les commandes
	 * -1 = aucun drone n est encore contacte 
	 * le currentDroneID coorespond a la mission dans la choreography
	 **/
	int currentDroneID;

	int numberOfDrones;

	//Drone by Drone
	boolean isMissionClearDone;
	boolean isMissionCountDone;
	boolean isMissionLoaded; //est ce que le drone courant a load sa mission?

	//Choreography
	boolean ischoregraphyLoaded; //est ce que le drone courant a load son scenario


	/** la choreography a executer */
	Choreography choreography;

	float origLatitude;
	float origLongitude;




	public LoadChoreography(MavlinkConnection connection, String filename, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);

		Choreography choreography = LoadChoreography.getChoreographyFromJson(filename);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();
	}

	public LoadChoreography(MavlinkConnection connection, Choreography choreography, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();
	}


	public LoadChoreography(MavlinkConnection connection, float origLatitude, float origLongitude){

		GlobalManager.realWorldDroneModel.addPropertyChangeListener(this);

		this.connection = connection;
		this.origLatitude = origLatitude;
		this.origLongitude = origLongitude;
		
		this.choreography = null;

		this.currentDroneID = 0;
		this.numberOfDrones = 0;

		this.ischoregraphyLoaded = false;

		this.commandTimer = new Timer( (long)(1000.0f/HERTZ) );
		this.missionClearTimer = new Timer( 2000f ); //2s

		this.prepareNewMission();

	}



	public static Choreography getChoreographyFromJson(String filename) {

		FileHandle handle = Gdx.files.internal(filename);
		String json = handle.readString();

		ObjectMapper objectMapper = new ObjectMapper();
		Choreography choreo = null;

		try {
			choreo = objectMapper.readValue(json, new TypeReference<Choreography>(){});
		} catch (IOException e) {
			System.out.println("Choerography: Cannot load " + filename);
			e.printStackTrace();
		}

		return choreo;
	}



	@Override
	public void update() {
		super.update();

		if(!this.ischoregraphyLoaded) {

			List<Waypoint> waypoints = this.choreography.missions.get(this.currentDroneID).waypoints;

			
			/** next msg a envoyer : Mission Clear */
			if(!this.isMissionClearDone) {

				if(commandTimer.isFinished()) {
					commandTimer.reset();

					int numberOfWaypoint = waypoints.size();
					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionClearAll());
				}

			}
			/** next msg a envoyer : Mission Count */
			else if(!this.isMissionCountDone) {

				//on a bien fini la pause avec la commande precedente?
				if(missionClearTimer.isFinished()) {

					if(commandTimer.isFinished()) {
						commandTimer.reset();

						int numberOfWaypoint = waypoints.size();
						MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionCount(numberOfWaypoint));
					}
				}

			}

			/** les msgs suivants : les waypoints, sont traites par notification des PropertiesChangeListener*/



			/** a t on fini de charger un scenario sur un drone */
			if(this.isMissionLoaded) {

				this.currentDroneID = this.currentDroneID + 1;

				/** a t on fini de tout charger sur tous les drones? */
				if(currentDroneID > this.numberOfDrones -1) {
					this.ischoregraphyLoaded = true;
					Tools.writeLog("     #### END : Choreography loaded. ####     ");
				}
				/** sinon on enchaine sur la mission suivante */
				else {
					this.prepareNewMission();
				}

			}



		}

	}


	public void prepareNewMission() {
		
		this.isMissionClearDone = false;
		this.isMissionCountDone = false;
		this.isMissionLoaded = false;

		Tools.writeLog("     #### START : new Mission upload ####     ");
	}




	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.PARAM_VALUE)){
			ParamValue paramValue = (ParamValue) evt.getNewValue();

			/** Permet de connaitre le nombre de waypoint deja load dans l'ardupilot */
			if(paramValue.paramId().equals("MIS_TOTAL")) {

				/** Permet de verifier qu on a bien tous les points clear */
				if(paramValue.paramValue() == 0f) {
					this.isMissionClearDone = true;
					Tools.writeLog("     #### Mission is Cleared (DroneId: "+ this.currentDroneID +") ####     ");
					this.missionClearTimer.reset(); //on initailise le timer pour la prochaine commande
				}
			}

		}
		else if (propertyName.equals(MavlinkCommunicationModel.MISSION_REQUEST)){
			MissionRequest missionRequest = (MissionRequest) evt.getNewValue();

			/** Send a Waypoint */
			isMissionCountDone = true;
			Waypoint wp = this.choreography.missions.get(currentDroneID).waypoints.get( missionRequest.seq() );

			//Conversion local vers lat
			float wpLatitude = Tools.add_distance_to_Latitude(this.origLatitude, wp.X_in_meter);
			float wpLongitude = Tools.add_distance_to_Longitude(this.origLongitude, wp.Y_in_meter);
			MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionItem(missionRequest.seq(), wpLatitude, wpLongitude, wp.Z_in_meter));//seq, latitude, longitude, altitude));

		}
		else if (propertyName.equals(MavlinkCommunicationModel.MISSION_ACK)){
			MissionAck missionAck = (MissionAck) evt.getNewValue();


			//example : MissionAck{targetSystem=255, targetComponent=0, type=EnumValue{value=0, entry=MAV_MISSION_ACCEPTED}, missionType=null}
			if(missionAck.type().entry() == MavMissionResult.MAV_MISSION_ACCEPTED) {

				if(!missionClearTimer.isFinished()) {
					Tools.writeLog("     #### Clear Mission Accepted (DroneId: "+ this.currentDroneID +") ####     ");
				}
				else {
					/** fin de load de mission */
					this.isMissionLoaded = true;
					Tools.writeLog("     #### Mission Loaded (DroneId: "+ this.currentDroneID +") ####     ");
				}

			}
		}

	}

}

