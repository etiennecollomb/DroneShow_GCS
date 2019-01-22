package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.drone.show.world.ui.WorldViewer;

public abstract class AbstractWidget implements PropertyChangeListener {

	List<ModelInstance> modelInstances = new ArrayList<>();
	
	public List<ModelInstance> getModelInstances() {
		return modelInstances;
	}
	
	// This will be call before draw is rendered
	public abstract void beforeRender();

	/** 2D et 3D sont draw via des process different */
	
	//3D
	public void draw() {
		this.beforeRender();
		WorldViewer.modelBatch.render(this.getModelInstances(), WorldViewer.environment);
	}
	
	//2D
	public abstract void addToDecalBatch(DecalBatch decalBatch);

}
