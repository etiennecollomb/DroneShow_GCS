package com.drone.show.gcs.scenarii;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.drone.show.GlobalManager;
import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
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

public class ChoreographyManager implements PropertyChangeListener{

	private MavlinkConnection connection;

	private static final float HERTZ = 5.0f; //frequence d'envoie de la commande
	private Timer commandTimer;
	//Pour etre sur qu on recoit pas des missionack de la commande precedent MissionClear
	//alors qu on est sur la sequence REQUEST_ITEM qui retourne un mission ack aussi
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




	public ChoreographyManager(MavlinkConnection connection, String filename, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);

		Choreography choreography = getChoreographyFromJson(filename);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();
	}

	public ChoreographyManager(MavlinkConnection connection, Choreography choreography, float origLatitude, float origLongitude){
		this(connection, origLatitude, origLongitude);
		this.choreography = choreography;
		this.numberOfDrones = this.choreography.missions.size();
	}


	public ChoreographyManager(MavlinkConnection connection, float origLatitude, float origLongitude){

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



	public Choreography getChoreographyFromJson(String filename) {

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




	public void update() {


		if(!this.ischoregraphyLoaded) {

			List<Waypoint> waypoints = this.choreography.missions.get(this.currentDroneID).waypoints;

			/** Action to do : Set home position */
			//TODO: Doit etre envoye a TOUS les drone en meme temps pour qu on est la meme "erreur GPS"
			//Verifier en suite qu on a bien tous les drones qui ont leur position de setter
			//comment faire ? : 
			// 1) set les position en lat : 0, long :0
			// 2) envoyer UNE seule fois la commande setHomeToCurrentLocation a tous
			// 3) recuperer les home position de chacun un par un
			//		si lat long != 0 et long != 0 (ou 0+10km? on est en pleine mera cet endroit) le drone a bien set sa position
			//Si tous les drone on t set leur position => Continue
			//sinon reprendre en 1)
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// !!!! Ci dessus ne fonctionne pas car la home position est overwrite quand on arm....  !!!!
			
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


			//MissionAck{targetSystem=255, targetComponent=0, type=EnumValue{value=0, entry=MAV_MISSION_ACCEPTED}, missionType=null}
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

