package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;

public class HumanWidget extends AbstractWidget {
	

	public static final String HUMAN_3D_NAME = "data/human.obj";
	public static final float HUMAN_REAL_WORLD_HEIGHT = 7.4f; //le model fait de base 7.4m de haut dans le worldviewer
	public static final float HUMAN_WANTED_HEIGHT = 1.80f; //taille en m du personnage
	static float scaleFactor = HUMAN_WANTED_HEIGHT/HUMAN_REAL_WORLD_HEIGHT;
	
	/** Position */
	public static final Vector3 position = new Vector3(0, HUMAN_WANTED_HEIGHT/2f, 50f);
	
	Model localModel;
	Quaternion orientation;
	
	
	public HumanWidget() {
		
		localModel = GlobalManager.assestsLoader.getAssetManager().get(HUMAN_3D_NAME, Model.class);
		
		ModelInstance instance = new ModelInstance(localModel);
		instance.transform.setToRotation(new Vector3(0,1,0), 180);
		instance.transform.scale(scaleFactor, scaleFactor, scaleFactor);
		instance.transform.translate(0, position.y /scaleFactor, -position.z /scaleFactor);
		
		modelInstances.add(instance);
		
	}

	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
	
	@Override
	public void beforeRender() {
		
	}


	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
		// TODO Auto-generated method stub
		
	}
}
