package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.GlobalManager;

public class WorldSphereWidget extends AbstractWidget {
	

	public static final String WORLD_SPHERE_3D_NAME = "data/spaceSphere.obj";
	
	public WorldSphereWidget() {
		
		ModelInstance instance = new ModelInstance( GlobalManager.assestsLoader.getAssetManager().get(WORLD_SPHERE_3D_NAME, Model.class) );
		instance.transform.setToRotation(new Vector3(0,0,1), 180);
		float scaleFactor = 350f;
		instance.transform.scale(scaleFactor, scaleFactor, scaleFactor);
		modelInstances.add(instance);
		
	}

	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
	
	@Override
	public void beforeRender() {
		// do nothing
	}


	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
		// TODO Auto-generated method stub
		
	}
}
