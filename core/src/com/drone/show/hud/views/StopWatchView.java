package com.drone.show.hud.views;

import com.drone.show.generic.Tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class StopWatchView extends TextView {


	private float time;
	private int hours, minutes, secondes;
	private boolean pauseWatch;

	
	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public StopWatchView(BitmapFont font, Color fontColor){
		super("00:00:00", font, fontColor);
	}



	/**************************************
	 * 
	 * Property Change Support/Listener
	 * 
	 **************************************/

	/**************************************
	 * 
	 * Methods
	 * 
	 **************************************/

	public void init() {
		this.init(0);
	}

	public void init(float initTime) {
		this.setTime( initTime );
		setPauseWatch(false);
	}



	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	public void act(float delta) {
		super.act(delta);

		/** update time */
		if(!isPauseWatch())
			this.setTime( this.getTime() + Gdx.graphics.getDeltaTime() );

		this.setText(Tools.convertFloatSecToStringTime( this.getTime() ));


	};



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public boolean isPauseWatch() {
		return pauseWatch;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSecondes() {
		return secondes;
	}

	public void setSecondes(int secondes) {
		this.secondes = secondes;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public void setPauseWatch(boolean pauseWatch) {
		this.pauseWatch = pauseWatch;
	}	

	public float getTime() {
		return time;
	}

}