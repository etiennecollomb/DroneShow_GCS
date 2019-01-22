package com.drone.show.world.ui.widget;

import java.beans.PropertyChangeEvent;
import java.util.Iterator;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.drone.show.world.model.DroneModel;
import com.drone.show.world.ui.WorldViewer;

public class DronePathWidget extends AbstractWidget {
	
	int nbTailElements = 250;
	
	CircularFifoQueue<Vector3> droneLastPositions = new CircularFifoQueue<Vector3>(nbTailElements);

	Model localModel;
	
	public DronePathWidget(AssetManager assets_, DroneModel drone_) {
//		super(assets_, drone_);
		localModel = getModel();
	}
	
	long lastLocalModelUpdate = System.currentTimeMillis();
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		String propertyName = evt.getPropertyName();
//		if ( propertyName.equals( Drone.DRONE_POSITION ) ) {
//			if ((System.currentTimeMillis() - lastLocalModelUpdate) > interval) {
//				droneLastPositions.add(drone.getCurrentPosition());
//				lastLocalModelUpdate = System.currentTimeMillis();
//			}
//		}
	}

	int ratio = 5;
	
	private Model getModel() {
		ModelBuilder modelBuilder = new ModelBuilder();
		return modelBuilder.createBox(WorldViewer.CUBE_WIDTH_IN_M/ratio, WorldViewer.CUBE_WIDTH_IN_M/ratio, WorldViewer.CUBE_WIDTH_IN_M/ratio, new Material(ColorAttribute.createDiffuse(Color.GOLD)), Usage.Position | Usage.Normal);
	}

	long lastScreenUpdate = System.currentTimeMillis();
	int interval = 100; // en millisecondes
	
	@Override
	public void beforeRender() {
		if ( (System.currentTimeMillis() - lastScreenUpdate) < interval )
			return;
		
		modelInstances.clear();

		for (Iterator<Vector3> it = droneLastPositions.iterator(); it.hasNext();) {
			Vector3 position = it.next();
			ModelInstance instance = new ModelInstance(localModel);
			instance.transform.setToTranslation(position);
			modelInstances.add(instance);			
		}

		lastScreenUpdate = System.currentTimeMillis();
	}

	@Override
	public void addToDecalBatch(DecalBatch decalBatch) {
		// TODO Auto-generated method stub
		
	}
}
