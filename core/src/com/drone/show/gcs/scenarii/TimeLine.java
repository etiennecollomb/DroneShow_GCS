package com.drone.show.gcs.scenarii;

import java.util.ArrayList;

import com.drone.show.gcs.actions.Action;
import com.drone.show.generic.Tools;


/** TimeLine of actions */
public class TimeLine {


	Boolean isFinished; //TimeLine finished?
	ArrayList<Action> actions;
	int currentStep;
	boolean isCurrentStepFinished;


	public TimeLine() {
		
		this.actions = new ArrayList<Action>();
		this.reset();
	}
	
	
	public void reset() {
		
		this.isFinished = false;
		this.currentStep = 0;
		this.actions.clear();
		this.isCurrentStepFinished = false;
	}


	
	public TimeLine add(Action action) {

		this.actions.add(action);
		return this;
	}


	public boolean update() {

		boolean isNewMove=false;

		/** Check if currentStep is finished */
		if(this.isCurrentStepFinished) {
			this.currentStep = this.currentStep+1;
			isNewMove = true;
		}

		/** Timeline Finished? */
		if( this.currentStep >= this.actions.size() ) {
			this.isFinished = true;
			Tools.writeLog("  ***TimeLine*** : TimeLine Finished");

		}

		/** If not, then Update current move */
		else {

			this.isCurrentStepFinished = false;

			/** On update le current move */
			Action currentAction = this.getCurrentAction();
			currentAction.update();

			if( currentAction.isFinished() ) {
				this.isCurrentStepFinished = true;
				}

		}

		return isNewMove;
	}


	
	
	
	public Action getCurrentAction(){
		return this.actions.get( this.currentStep );
	}



}
