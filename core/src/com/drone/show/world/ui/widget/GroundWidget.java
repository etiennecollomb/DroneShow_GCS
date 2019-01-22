package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class GroundWidget extends AbstractWidget {
	
	private float width;
	
	public GroundWidget(float width) {
		ModelBuilder modelBuilder = new ModelBuilder();
		Material material = new Material( ColorAttribute.createDiffuse(new Color(0.20f, 0.35f, 0.44f, 0.2f)) );
		material.set( new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA) );
		ModelInstance instance = new ModelInstance(modelBuilder.createBox(width, width, 0.01f, material, Usage.Position | Usage.Normal));
		instance.transform.setToRotation(new Vector3(1,0,0), 90);
		
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
