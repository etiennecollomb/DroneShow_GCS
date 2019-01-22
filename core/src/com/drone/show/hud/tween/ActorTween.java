package com.drone.show.hud.tween;


import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;



public class ActorTween implements TweenAccessor<Actor> {

	
	public static final int POSITION = 1;
	public static final int SCALE = 2;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

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

	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case SCALE:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case SCALE:
			target.setScaleX(newValues[0]);
			target.setScaleY(newValues[1]);
			break;
		default:
			assert false;
			break;
		}
	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

}
