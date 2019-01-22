package com.drone.show.generic;

public class Timer {

	private long duration; //en ms
	private long initTime;
	
	/** duration in second */
	public Timer(float duration) {
		
		this.duration = (long)(duration);
		this.initTime = System.currentTimeMillis();
		
	}
	
	public boolean isFinished() {
		if( System.currentTimeMillis() - initTime > duration)
			return true;
		return false;
	}
	
	public void reset() {
		initTime = System.currentTimeMillis();
	}
	
}
