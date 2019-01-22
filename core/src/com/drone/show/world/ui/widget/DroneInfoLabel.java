package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.world.model.DroneModel;
import com.drone.show.world.ui.WorldViewer;

public class DroneInfoLabel extends AbstractWidget {

	String textID, textPosition, textBattery;
	Vector3 position;
	Matrix4 projectionMatrix;
	BitmapFont bitmapFont;

	Sprite batterySprite;
	Color batteryColor;

	float batteryLevelSpriteWidth = 2f*WorldViewer.BASIS_SCALE;
	float batteryLevelSpriteHeight = 40f*WorldViewer.BASIS_SCALE;

	
	public DroneInfoLabel(DroneModel drone_, BitmapFont bitmapFont_) {
//		super(drone_);
//		textID = ""+drone.getId();
//		updateTextPosition();
//		updateBattery();
//		projectionMatrix = new Matrix4();
//		bitmapFont = bitmapFont_;
//		batterySprite = new Sprite(new Texture("white.png"));
//		batteryColor = new Color();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		String propertyName = evt.getPropertyName();
//		if ( propertyName.equals( Drone.DRONE_POSITION ) ) {
//			updateTextPosition();
//		} else if ( propertyName.equals( Drone.DRONE_BATTERY ) ) {
//			updateBattery();
//		}
	}

	@Override
	public void beforeRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void draw(SpriteBatch spriteBatch, PerspectiveCamera cam) {
//		projectionMatrix.set(cam.combined);
//		projectionMatrix.rotate(new Vector3(1, 0, 0), 90);
//		spriteBatch.setProjectionMatrix( projectionMatrix );
//
//		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//
//		Vector3 translateVector = new Vector3(drone.getCurrentPosition().x, drone.getCurrentPosition().z, -drone.getCurrentPosition().y);
//		spriteBatch.getTransformMatrix().idt()
//		.translate(translateVector)
//		.rotate(Tools.lookAt(drone.getCurrentPosition(), (new Vector3(cam.position)).scl(1000f), cam.up));
//		
//		spriteBatch.getTransformMatrix().translate(0f, WorldViewer.CUBE_WIDTH_IN_M, 0f);
//
//		spriteBatch.begin();
//		bitmapFont.getData().setScale(0.1f*WorldViewer.BASIS_SCALE);
//		
//		//Text Infos
//		String TextInfo = textID +"\n"+ textPosition +"\n"+ textBattery;
//		Tools.glyphLayout.setText(bitmapFont, TextInfo);
//		bitmapFont.draw(spriteBatch, TextInfo, -WorldViewer.CUBE_WIDTH_IN_M/2f+batteryLevelSpriteWidth*2, Tools.glyphLayout.height);
//
//		bitmapFont.getData().setScale(1f);
//		spriteBatch.end();
//		
//		float currentSpriteHeight = batteryLevelSpriteHeight/Drone.MAX_BATTERY_LEVEL*drone.getBatteryLevel();
//		spriteBatch.getTransformMatrix().translate( -WorldViewer.CUBE_WIDTH_IN_M/2f, currentSpriteHeight/2f, 0);
//		spriteBatch.begin();
//		
//		//Battery Level Sprite
//		batterySprite.setScale(batteryLevelSpriteWidth, currentSpriteHeight);
//		batterySprite.setColor( Tools.setColorGreenToRed(batteryColor, 0f, Drone.MAX_BATTERY_LEVEL, drone.getBatteryLevel()) );
//		batterySprite.draw(spriteBatch);
//		
//		spriteBatch.end();
//		
//		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);		
//	}
//
//	private void updateTextPosition() {
//		float posX = Tools.roundFloat(drone.getCurrentPosition().x/100f, 2);
//		float posY = Tools.roundFloat(drone.getCurrentPosition().y/100f, 2);
//		float posZ = Tools.roundFloat(drone.getCurrentPosition().z/100f, 2);
//		textPosition = "x = "+posX+" m\ny = "+posY+" m\nz = "+posZ+" m";
//	}
//	
//	private void updateBattery() {
//		textBattery = "Bat: "+drone.getBatteryLevel()+"% ";
//	}
}
