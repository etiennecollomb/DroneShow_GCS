package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.gcs.scenarii.Choreography;
import com.drone.show.gcs.scenarii.Waypoint;
import com.drone.show.generic.Timer;
import com.drone.show.generic.Tools;
import com.drone.show.hud.models.ApplicationModel;
import com.drone.show.hud.popups.TelemetryPopup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
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
 * 
 * droneID = nodeID de la radio 3DR associee
 * Id current auquel on envoie les commandes
 * le droneID coorespond a la mission dans la choreography
 * 
 **/

public class LoadChoreography extends Action implements PropertyChangeListener{


	private MavlinkConnection connection;

	/** Pour etre sur qu on recoit pas des MissionAck de la commande precedent MissionClear
	 * alors qu on est sur la sequence REQUEST_ITEM qui retourne un MissionAck aussi
	 */
	private Timer missionClearTimer;




	int numberOfDrones;

	//Drone by Drone
	StopAllStreamData stopAllStreamData;
	SetStabilizeMode setStabilizeMode;
	MissionClearAll missionClearAll;
	MissionCount missionCount;
	int numberOfWaypointOfCurrentMission;
	boolean isMissionLoaded; //est ce que le drone courant a load sa mission?


	/** la choreography a executer */
	Choreography choreography;

	float origLatitude;
	float origLongitude;




	public LoadChoreography(MavlinkConnection connection, String filename, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);

		Choreography choreography = LoadChoreography.getChoreographyFromJson(filename);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();

		this.prepareNewMission();
	}

	public LoadChoreography(MavlinkConnection connection, Choreography choreography, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();

		this.prepareNewMission();
	}


	public LoadChoreography(MavlinkConnection connection, float origLatitude, float origLongitude){
		super();

		this.connection = connection;
		this.origLatitude = origLatitude;
		this.origLongitude = origLongitude;

		this.choreography = null;

		this.droneID = 1; //Demarrer sur le premier drone, puis on itere
		this.numberOfDrones = 0;

		this.missionClearTimer = new Timer( 2000f ); //2s

	}



	public void prepareNewMission() {

		this.isAlive = false; //will trigger the startListeningEvents()	on the right RealDroneModel

		this.setStabilizeMode = new SetStabilizeMode(this.connection, this.droneID);
		this.stopAllStreamData = new StopAllStreamData(this.connection);
		this.missionClearAll = new MissionClearAll(this.connection, this.droneID);

		/** on rajoute 1 waypoint , car le premier est la Home Position
		 * Toujours overrided par ardupilot quand le drone Arm
		 **/
		int missionID = this.droneID - 1;
		List<Waypoint> waypoints = this.choreography.missions.get( missionID ).waypoints;
		int numberOfWaypoint = waypoints.size();
		this.numberOfWaypointOfCurrentMission = numberOfWaypoint + 1;
		
		this.missionCount = new MissionCount(connection, this.droneID, this.numberOfWaypointOfCurrentMission);

		this.isMissionLoaded = false;

		Tools.writeLog("     #### Choerography START : new Mission upload ####     ");
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

		if(!this.isFinished()) {

			/** Stop all stream first */ 
			if(!this.stopAllStreamData.isFinished()) {
				this.stopAllStreamData.update();
			}
			else {

				/** next msg a envoyer : Stabilize Mode */
				if(!this.setStabilizeMode.isFinished()) {
					this.setStabilizeMode.update();
				}

				/** next msg a envoyer : Mission Clear */
				else if(!this.missionClearAll.isFinished()) {
					this.missionClearAll.update();
					this.missionClearTimer.reset(); //on initailise le timer pour la prochaine commande

					/** Si mission clear est fini */
					if(this.missionClearAll.isFinished()) {
						Tools.writeLog("     #### Mission is Cleared (DroneId: "+ this.droneID +") ####     ");
					}
				}

				/** next msg a envoyer : Mission Count */
				else if(!this.missionCount.isFinished()) {
					this.missionCount.update();
				}

				/** les msgs suivants : les waypoints, sont traites par notification des PropertiesChangeListener*/


				/** a t on fini de charger un scenario sur un drone */
				if(this.isMissionLoaded) {

					this.stopListeningEvents(); //on se desengage du property listenner du RealDroneModel qui n est plus utilise

					/** a t on fini de tout charger sur tous les drones? */
					if(this.droneID + 1 > this.numberOfDrones) {
						this.setFinished(true);
						Tools.writeLog("     #### Choerography END : Choreography loaded. ####     ");
					}
					/** sinon on enchaine sur la mission suivante */
					else {
						this.droneID = this.droneID + 1;
						this.prepareNewMission();
					}

				}

			}

		}

	}





	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(RealDroneModel.MISSION_REQUEST)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());

			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				MissionRequest missionRequest = (MissionRequest)mavlinkMessage.getPayload();

				//First one : The first mission sequence number (seq==0) is populated with the home position of the vehicle instead of the first mission item.
				//Will be overrided by the home position
				if(missionRequest.seq() == 0) {
					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionItem(this.droneID, missionRequest.seq(), 0,0,0));
				}
				else {

					//missionRequest.seq() part bien de 0 et non de 1 pour le premier waypoint
					//mais le premier waypoint est la home position, overrided by ardupilot qd le drone ARM
					int missionID = this.droneID - 1;
					int waypointID = missionRequest.seq()-1;
					Waypoint wp = this.choreography.missions.get( missionID ).waypoints.get( waypointID );

					//Second one : takeoff
					//Takeoff (either from ground or by hand-launch). It should be the first command of nearly all Plane and Copter missions.
					if(missionRequest.seq() == 1) {
						MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionTakeOff(this.droneID, missionRequest.seq(), wp.Z_in_meter));
					}
					else {
						float wpLatitude = Tools.add_distance_to_Latitude(this.origLatitude, wp.X_in_meter);
						float wpLongitude = Tools.add_distance_to_Longitude(this.origLongitude, wp.Y_in_meter);
						MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionItem(this.droneID, missionRequest.seq(), wpLatitude, wpLongitude, wp.Z_in_meter));//seq, latitude, longitude, altitude));
					}
				}
			}

		}
		else if (propertyName.equals(RealDroneModel.MISSION_ACK)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());

			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				MissionAck missionAck = (MissionAck)mavlinkMessage.getPayload();

				//example : MissionAck{targetSystem=255, targetComponent=0, type=EnumValue{value=0, entry=MAV_MISSION_ACCEPTED}, missionType=null}
				if(missionAck.type().entry() == MavMissionResult.MAV_MISSION_ACCEPTED) {

					//on a bien fini le mission clear depuis un moment qui retourne aussi cette valeur
					if(missionClearTimer.isFinished()) {
						/** fin de load de mission */
						this.isMissionLoaded = true;
						Tools.writeLog("     #### Mission Loaded (DroneId: "+ this.droneID +") ####     ");
					}

				}
			}
		}
		/** pour savoir si la mission est bien upload on peut aussi checker le ParamValue 
		 * Parfois le mission ack n ets pas renvoye par le ardupilot ?
		 * TODO: checker pourquoi quand on a 2 drones le second ne renvoie pas de mission ack alors que la mission est bien chargee....
		 */
		else if (propertyName.equals(RealDroneModel.PARAM_VALUE)){
			MavlinkMessage mavlinkMessage = ((MavlinkMessage)evt.getNewValue());
			
			if(mavlinkMessage.getOriginSystemId() == this.droneID) {
				ParamValue paramValue = (ParamValue)mavlinkMessage.getPayload();

				/** Permet de connaitre le nombre de waypoint deja load dans l'ardupilot */
				if(paramValue.paramId().equals("MIS_TOTAL")) {

					/** Permet de verifier qu on a bien tous les points clear */
					if(paramValue.paramValue() == this.numberOfWaypointOfCurrentMission) {
						this.isMissionLoaded = true;
						Tools.writeLog("     #### Mission Loaded (DroneId: "+ this.droneID +") ####     ");
					}
				}

			}
		}

	}

}

