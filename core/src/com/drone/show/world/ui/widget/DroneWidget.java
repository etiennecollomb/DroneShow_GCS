package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.generic.ColorsCatalog;
import com.drone.show.world.model.DroneModel;

public class DroneWidget extends AbstractWidget {

	public static final String MODEL_3D_DRONE = "data/drone.g3db";
	public static final String MODEL_2D_HALO = "data/lightDiffuse.png";
	public static final String MODEL_3D_BULB = "data/bulbShape01.obj";
	
	/** Rotation max par degre de liberte du model 3D par rapport a la velocity */
	public static final float MAX_ROTATION = 15f; // en degr�
	
	DroneModel droneModel;
	ModelInstance droneModelInstance;
	Decal lightDiffuseDecal;
	ModelInstance bulbModel;
	
	//3D drone size 0.05f = 20cm de rayon, 40cm de largeur
	public static final float DRONE_SIZE = 2.4f; //en m
	float widgetScale = DRONE_SIZE/0.2f * 0.05f; //0.3 = 2.4 m

	
	public DroneWidget(AssetManager assets_, DroneModel drone) {
		
		this.droneModel = drone;
		
		this.droneModelInstance = new ModelInstance( assets_.get(MODEL_3D_DRONE, Model.class) );
		this.droneModelInstance.materials.get(0).set(ColorAttribute.createDiffuse(ColorsCatalog.getDroneColor1()));
		this.droneModelInstance.materials.get(1).set(ColorAttribute.createDiffuse(ColorsCatalog.getDroneColor2()));
		this.droneModelInstance.materials.get(2).set(ColorAttribute.createDiffuse(ColorsCatalog.getDroneColor3()));
		this.droneModelInstance.materials.get(3).set(ColorAttribute.createDiffuse(ColorsCatalog.getDroneColor2()));
		this.droneModelInstance.materials.get(4).set(ColorAttribute.createDiffuse(ColorsCatalog.getDroneColor2()));
		this.modelInstances.add(droneModelInstance);
		
		this.bulbModel = new ModelInstance( assets_.get(MODEL_3D_BULB, Model.class) );
		this.modelInstances.add(bulbModel);
		
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
	
	@Override
	public void beforeRender() {
		
		//Set position and rotation
		Vector3 pos = droneModel.currentPosition;
		this.droneModelInstance.transform.idt();
		this.droneModelInstance.transform.translate(pos.x, pos.y, pos.z);
		this.droneModelInstance.transform.scale(widgetScale, widgetScale, widgetScale);
		this.droneModelInstance.transform.rotate(0,0,1, this.droneModel.rotation.x);
		this.droneModelInstance.transform.rotate(1,0,0, this.droneModel.rotation.z);

		this.bulbModel.transform.idt();
		this.bulbModel.transform.translate(pos.x, pos.y, pos.z);
		this.bulbModel.transform.scale(widgetScale, widgetScale, widgetScale);
		this.bulbModel.transform.rotate(0,0,1, this.droneModel.rotation.x);
		this.bulbModel.transform.rotate(1,0,0, this.droneModel.rotation.z);
		
		//Set Color
		Color color = droneModel.ledColor;
		this.bulbModel.materials.get(0).set(ColorAttribute.createDiffuse(color));
		
	}


	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
	}


	
}
