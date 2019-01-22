package com.drone.show.gcs.DEPRECATED_oldStuff;

import java.util.ArrayList;

import com.drone.show.gcs.DEPRECATED_oldStuff.MavlinkAction.MavlinkActionType;
import com.drone.show.generic.Tools;


/**
 * Tous est decris en CENTIMETRE et en position relative
 * Example :
 * new TimeLine()
 * .add(Move move1)
 * .add(Move move2)
 * .add(Move move3)
 * .add(Move move4)
 */


//====================================================================
/**
mettre la notion de retry dans les actions mavlinken option
soit 0 = infini, soit n fois
si retry echoue : return l id du system id qui etait concern�
		ou est sctoke cet id? utile si on fait une commande global avec confirmation : pourrait etre un sous timeline
		
		un objet de type "TO_ALL_*command*" qui renvoie un tableau de id de drone qui ont pas recu la command
		NOTION DE RETRY SUR CET OBJET
*/			
				
/**
N drones avec confirmation  : 
	un drone model qui recupere les infos par drone (reset all value a chaque debut d action ) depuis le process GCSthread
	un id a checker lorsqu on recoit les msg mavlink dans le GCStread, id du drone en cours
	A chaque action , dire a tous les drones de se taire et demander le bon message stream a etre recu du drone vis�
	
All Drone sans confirmation:
	lancer la commande avec le system id a 0
	
All drone avec confirmation:
	avoir un drone temoin? ... :/
	
	
MAX waypoint  pixhawk 
On Pixhawk this gives 718 waypoints, 46 rally points and 70 fence points


SI on charge tous les waypont au tout debut : pour eviter les temps de chargement trop long :
On peut charger les 700 waypoints puis mettre en loiter pour faire pause (ou brake mode?!) et resume la mission en auto mode
Il faut setter le param MIS_RESTART (Mission Restart when entering Auto mode) a 1
http://ardupilot.org/copter/docs/parameters.html#mis-restart-mission-restart-when-entering-auto-mode

current active waypoint ? MISSION_CURRENT ( #42 )

resume at a specific waypoint? MISSION_SET_CURRENT ( #41 )



http://ardupilot.org/copter/docs/parameters.html#mis-restart-mission-restart-when-entering-auto-mode
MIS_TOTAL: Total mission commands
Note: This parameter is for advanced users
The number of mission mission items that has been loaded by the ground station. Do not change this manually.


exemplae upload mission
https://github.com/dronekit/dronekit-python/blob/master/dronekit/__init__.py
Vehicle L970
self._commands = CommandSequence(self)
class CommandSequence(object):
	-> clear , add , upload
	Principe pour envoyer les commandes waypooint
	https://www.colorado.edu/recuv/2015/05/25/mavlink-protocol-waypoints

*/
//====================================================================


				

public class TimeLine {


	Boolean isFinished;
	ArrayList<MavlinkAction> mavlinkActions;
	int currentStep;
	boolean isCurrentStepFinished;


	public TimeLine() {
		
		this.setMavlinkActions(new ArrayList<MavlinkAction>());

		this.setFinished(false);
		this.setCurrentStep(0);
		this.getMavlinkActions().clear();
		this.setCurrentStepFinished(false);
	}


	
	public TimeLine add(MavlinkAction mavlinkAction) {

		this.getMavlinkActions().add(mavlinkAction);
		return this;
	}


	public boolean update(float deltaTime) {

		boolean isNewMove=false;

		/** Check if currentStep is finished */
		if(this.isCurrentStepFinished()) {
			this.setCurrentStep( this.getCurrentStep() + 1 );
			isNewMove = true;
		}

		/** Timeline Finished? */
		if( this.getCurrentStep() >= this.getMavlinkActions().size() ) {
			this.setFinished(true);
			Tools.writeLog("***TimeLine*** : TimeLine Finished");

		}

		/** If not, then Update current move */
		else {

//			if(isNewMove) {
//				Tools.writeLog("***TimeLine*** : Starting next Move => " + this.getCurrentMavlinkActionType().toString() );
//			}

			this.setCurrentStepFinished(false);

			/** On update le current move */
			MavlinkAction currentMove = this.getCurrentMavlinkAction();
			currentMove.update(deltaTime);

			if( currentMove.isFinished() ) {
				this.setCurrentStepFinished(true);
				Tools.writeLog("***Timeline*** : " + currentMove.getMavlinkActionType().name() + " finished");
			}

		}

		return isNewMove;
	}




	public String getCurrentStepID() {
		return ""+this.getCurrentStep();
	}

	public MavlinkAction getCurrentMavlinkAction(){
		return this.getMavlinkActions().get( this.getCurrentStep() );
	}

	public MavlinkActionType getCurrentMavlinkActionType(){
		return this.getMavlinkActions().get( this.getCurrentStep() ).getMavlinkActionType();
	}

	public Boolean isFinished() {
		return isFinished;
	}

	public void setFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public ArrayList<MavlinkAction> getMavlinkActions() {
		return mavlinkActions;
	}

	public void setMavlinkActions(ArrayList<MavlinkAction> mavlinkActions) {
		this.mavlinkActions = mavlinkActions;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public boolean isCurrentStepFinished() {
		return isCurrentStepFinished;
	}

	public void setCurrentStepFinished(boolean isCurrentStepFinished) {
		this.isCurrentStepFinished = isCurrentStepFinished;
	}



}
