package com.drone.show.gcs.DEPRECATED_oldStuff;


import com.drone.show.generic.Tools;
import io.dronefleet.mavlink.common.CommandAck;

/**
 * A scenario is a set of mouvement pre-defined attached to a specific drone 
 * 
 * 
 *
 */
public class Scenario {


	TimeLine timeLine;


	public Scenario() {
		this(new TimeLine());
	}


	public Scenario(TimeLine timeLine_) {

		this.setTimeLine( timeLine_ );
	}



	//Delta Time en seconde
	public void update(float deltaTime) {

//		if(this.getTimeLine().isStarted()) {

			boolean isNewMove = this.getTimeLine().update(deltaTime); //currentPosition doit etre en cm !

//		}

	};



	/**
	 * Reponse de l ardupilot transmise a la commande en cours du timeline
	 */
	public void receivedCommandAck(CommandAck commandAck) {

		if(!this.getTimeLine().isFinished) {
			this.getTimeLine().getCurrentMavlinkAction().setCommandAck(commandAck);
		}
	}


	public boolean isFinished() {

		boolean isFinished = true;
		if(!this.getTimeLine().isFinished())
			isFinished = false;
		return isFinished;
	}






	public TimeLine getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;	
		Tools.writeLog("***Scenario*** : New timeline added with " + this.getTimeLine().getMavlinkActions().size() + " moves.");
	}

}
