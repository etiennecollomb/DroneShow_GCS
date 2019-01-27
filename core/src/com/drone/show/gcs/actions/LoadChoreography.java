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
	StopAllStreamData stopAllStreamData;
	MissionClearAll missionClearAll;
	MissionCount missionCount;
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

		GlobalManager.realWorldDroneModel.addPropertyChangeListener(this);

		this.connection = connection;
		this.origLatitude = origLatitude;
		this.origLongitude = origLongitude;

		this.choreography = null;

		this.currentDroneID = 1; //Demarrer sur le premier drone, puis on itere
		this.numberOfDrones = 0;

		this.missionClearTimer = new Timer( 2000f ); //2s

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

				/** next msg a envoyer : Mission Clear */
				if(!this.missionClearAll.isFinished()) {
					this.missionClearAll.update();
					
					/** Si mission clear est fini */
					if(this.missionClearAll.isFinished()) {
						Tools.writeLog("     #### Mission is Cleared (DroneId: "+ this.currentDroneID +") ####     ");
						this.missionClearTimer.reset(); //on initailise le timer pour la prochaine commande
					}
				}
				
				/** next msg a envoyer : Mission Count */
				else if(!this.missionCount.isFinished()) {

					//on a bien fini la pause avec la commande Clear precedente?
					if(missionClearTimer.isFinished()) {
						this.missionCount.update();
					}

				}

				/** les msgs suivants : les waypoints, sont traites par notification des PropertiesChangeListener*/


				/** a t on fini de charger un scenario sur un drone */
				if(this.isMissionLoaded) {

					this.currentDroneID = this.currentDroneID + 1;

					/** a t on fini de tout charger sur tous les drones? */
					if(currentDroneID > this.numberOfDrones -1) {
						this.setFinished(true);
						Tools.writeLog("     #### Choerography END : Choreography loaded. ####     ");
					}
					/** sinon on enchaine sur la mission suivante */
					else {
						this.prepareNewMission();
					}

				}

			}

		}

	}


	public void prepareNewMission() {

		this.stopAllStreamData = new StopAllStreamData(this.connection);
		this.missionClearAll = new MissionClearAll(this.connection, this.currentDroneID);
		
		/** on rajoute 1 waypoint , car le premier est la Home Position
		 * Toujours overrided par ardupilot quand le drone Arm
		 **/
		int missionID = this.currentDroneID - 1;
		List<Waypoint> waypoints = this.choreography.missions.get( missionID ).waypoints;
		int numberOfWaypoint = waypoints.size();
		numberOfWaypoint = numberOfWaypoint + 1;
		this.missionCount = new MissionCount(connection, this.currentDroneID, numberOfWaypoint);
		
		this.isMissionLoaded = false;

		Tools.writeLog("     #### Choerography START : new Mission upload ####     ");
	}




	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);

		String propertyName = evt.getPropertyName();

		if (propertyName.equals(MavlinkCommunicationModel.MISSION_REQUEST)){
			MissionRequest missionRequest = (MissionRequest) evt.getNewValue();

			//First one : The first mission sequence number (seq==0) is populated with the home position of the vehicle instead of the first mission item.
			//Will be overrided by the home position
			if(missionRequest.seq() == 0) {
				MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionItem(this.currentDroneID, missionRequest.seq(), 0,0,0));
			}
			else {

				//missionRequest.seq() part bien de 0 et non de 1 pour le premier waypoint
				//mais le premier waypoint est la home position, overrided by ardupilot qd le drone ARM
				int missionID = this.currentDroneID - 1;
				int waypointID = missionRequest.seq()-1;
				Waypoint wp = this.choreography.missions.get( missionID ).waypoints.get( waypointID );

				//Second one : takeoff
				//Takeoff (either from ground or by hand-launch). It should be the first command of nearly all Plane and Copter missions.
				if(missionRequest.seq() == 1) {
					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionTakeOff(this.currentDroneID, missionRequest.seq(), wp.Z_in_meter));
				}
				else {
					float wpLatitude = Tools.add_distance_to_Latitude(this.origLatitude, wp.X_in_meter);
					float wpLongitude = Tools.add_distance_to_Longitude(this.origLongitude, wp.Y_in_meter);
					MavLinkToolKit.sendCommand(connection, MavLinkToolKit.missionItem(this.currentDroneID, missionRequest.seq(), wpLatitude, wpLongitude, wp.Z_in_meter));//seq, latitude, longitude, altitude));
				}
			}

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

