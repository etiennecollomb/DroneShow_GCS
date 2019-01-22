package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.drone.show.world.model.DroneModel;

public class DroneHomeWidget extends AbstractWidget {

	public DroneHomeWidget(AssetManager assets_, DroneModel drone_) {
		ModelInstance instance = new ModelInstance(getModel());
		//instance.transform.setToTranslation(drone.getCurrentPosition());
		modelInstances.add(instance);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// DO NOTHING
	}

	@Override
	public void beforeRender() {
		// DO NOTHING
	}

	private Model getModel() {
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder meshBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.WHITE)));
		meshBuilder.ellipse(250f, 250f, 50, 0.f, 0.f, 0.f, 0.f, 0.f, 100.f);
		return modelBuilder.end();
	}

	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
		// TODO Auto-generated method stub
		
	}
	
}
