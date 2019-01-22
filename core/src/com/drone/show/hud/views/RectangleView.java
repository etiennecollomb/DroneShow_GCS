package com.drone.show.hud.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.drone.show.GlobalManager;


public class RectangleView extends Actor {

	
	private Color color;


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public RectangleView(float width, float height, Color color){

		this(0, 0, width, height, color);

	}

	public RectangleView(float x, float y, float width, float height, Color color){

		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setColor( color );

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

	/**************************************
	 * 
	 * Override
	 * 
	 **************************************/

	@Override
	public void draw(Batch batch, float alpha){
		
		GlobalManager.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), color, ShapeType.Filled, this.getScaleX(), this.getScaleY());

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}