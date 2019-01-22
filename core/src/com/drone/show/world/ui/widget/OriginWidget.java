package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class OriginWidget extends AbstractWidget {
	
	public OriginWidget() {
		ModelBuilder modelBuilder = new ModelBuilder();
		// index de l'origine.
		int x = (int) 0f;
		int y = (int) 0;
		int z = (int) 0f;

		ModelInstance arrowX = new ModelInstance(modelBuilder.createArrow(x,y,z,x+5f,y,z, 0.2f, 0.1f, 5, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.RED)),  Usage.Position | Usage.Normal));
		ModelInstance arrowY = new ModelInstance(modelBuilder.createArrow(x,y,z,x,y+5f,z, 0.2f, 0.1f, 5, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)),  Usage.Position | Usage.Normal)); 
		ModelInstance arrowZ = new ModelInstance(modelBuilder.createArrow(x,y,z,x,y,z+5f, 0.1f, 0.2f, 5, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.BLUE)),  Usage.Position | Usage.Normal));

		modelInstances.add(arrowX);
		modelInstances.add(arrowY);
		modelInstances.add(arrowZ);
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
