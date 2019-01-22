package com.drone.show.gcs.actions;

import com.drone.show.generic.Timer;


public class Wait extends Action {

	Timer waitTimer;
	boolean isStarted;

	
	public Wait(float duration_in_ms) {
		super();

		this.isStarted = false;
		this.waitTimer = new Timer(duration_in_ms);
	}

	@Override
	public void update() {
		super.update();
		
		if(!this.isStarted) {
			this.isStarted = true;
			this.waitTimer.reset();
		}
		
		if(this.waitTimer.isFinished()) {
			this.setFinished(true);
		}


	}


}
