package com.drone.show.world.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.drone.show.GlobalManager;
import com.drone.show.generic.Tools;
import com.drone.show.world.model.DroneModel;
import com.drone.show.world.model.WorldModel;
import com.drone.show.world.ui.widget.AbstractWidget;
import com.drone.show.world.ui.widget.DroneWidget;
import com.drone.show.world.ui.widget.GroundWidget;
import com.drone.show.world.ui.widget.HumanWidget;
import com.drone.show.world.ui.widget.WorldSphereWidget;

public class WorldViewer implements PropertyChangeListener {

	public static final float CUBE_WIDTH_IN_M = 0.30f;
	/**
	 * Do not change BASIS_SCALE !!
	 * Permet de zoomer plus vite sur des grands terrains
	 * 10 est empirique , c est la largeur initiale qd on avait un terrain 1000x1000, l essentiel est qu on ait une constante
	 */
	public static final float BASIS_SCALE = 50f / 10f; 

	/** Batches */
	public static com.badlogic.gdx.graphics.g3d.decals.DecalBatch decalBatch;
	public static ModelBatch modelBatch;
	public static SpriteBatch spriteBatch;

	/** Camera */
	public static PerspectiveCamera cam;
	public ZAxisCameraInputController camController;
	public static boolean isCameraCenteredOnItself; //est ce que la camera tourne par rapport au centre de la scene ou sur elle meme....

	/** Screen infos */
	public static int frameNumber;
	Label labelFPS, labelFrameNumber;

	public static Environment environment;

	public static WorldModel worldModel;

	BitmapFont fontFrameNumber, fontFPS, fontDroneInfoLabel;

	Music ambientMusic;
	static final String AMBIENT_MUSIC = "data/music1.mp3";

	List<AbstractWidget> widgetList = new CopyOnWriteArrayList<AbstractWidget>();




	public WorldViewer () {

		worldModel = new WorldModel();
		worldModel.addPropertyChangeListener(this);

		/** Camera */
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setCameraToHumanHead();
		camController = new ZAxisCameraInputController(cam);

		decalBatch = new DecalBatch( new CameraGroupStrategy(cam) );
		modelBatch = new ModelBatch();
		spriteBatch = new SpriteBatch();
		environment = new Environment();



		frameNumber = 0;


		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1f));
//		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, 1f, -1f));


		/** Widgets **/
		widgetList.add(new WorldSphereWidget() );
		//widgetList.add(new OriginWidget());
		widgetList.add(new GroundWidget(50000f)); //en m
		widgetList.add(new HumanWidget() );
		
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {	
				String propertyName = evt.getPropertyName();

				if ( propertyName.equals(WorldModel.NEW_DRONE) ) {
					DroneModel droneModel = (DroneModel) evt.getNewValue();

					DroneWidget droneWidget = new DroneWidget(GlobalManager.assestsLoader.getAssetManager(), droneModel);
					droneModel.addPropertyChangeListener(droneWidget);
					widgetList.add(droneWidget);
					//
					//					DroneInfoLabel droneInfoLabel = new DroneInfoLabel(drone, fontDroneInfoLabel);
					//					drone.addPropertyChangeListener(droneInfoLabel);
					//					widgetList.add(droneInfoLabel);
					//
					//					DronePathWidget dronePathWidget = new DronePathWidget(assets, drone);
					//					drone.addPropertyChangeListener(dronePathWidget);
					//					widgetList.add(dronePathWidget);
					//
					//					DroneHomeWidget droneHomeWidget = new DroneHomeWidget(assets, drone);
					//					drone.addPropertyChangeListener(droneHomeWidget);
					//					widgetList.add(droneHomeWidget);
				}
			}
		});
	}



	public void render() {

		frameNumber = frameNumber+1;


		/** 
		 * DRAW
		 */
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//Draw 3D
		modelBatch.begin(cam);
		for (AbstractWidget widget : widgetList) {
			widget.draw();
		}
		modelBatch.end();

		//Draw 2D
		int size = widgetList.size();
		for(int i=0; i<size ; i++) {
			widgetList.get(i).addToDecalBatch(WorldViewer.decalBatch);
		}
		WorldViewer.decalBatch.flush();

	}

	public void dispose() {
		modelBatch.dispose();
		//instances.clear();
	}


	//Update
	public void onUpdate(float deltaTime){

		this.handleKeyPressed();
	}


	private void handleKeyPressed() {

		/** set camera to default view */
		if(Gdx.input.isKeyPressed(Input.Keys.O)){
			this.setCameraToDefault();
		}
		/** set camera to human head */
		else if(Gdx.input.isKeyPressed(Input.Keys.P)){
			this.setCameraToHumanHead();
		}



	}


	private void setCameraToDefault() {
		this.isCameraCenteredOnItself = false;
		cam.up.set(0,1,0);
		cam.position.set(0,12f,50f); //en m
		cam.lookAt(0,0,0);
		this.commonCameraSet();
	}


	private void setCameraToHumanHead() {
		this.isCameraCenteredOnItself = true;
		cam.up.set(0,1,0);
		cam.position.set(2+1.5f, HumanWidget.HUMAN_WANTED_HEIGHT -0.25f, HumanWidget.position.z +5.5f);
		cam.lookAt(0,18,0);
		this.commonCameraSet();
	}


	private void commonCameraSet() {
		cam.near = 0.01f;
		cam.far = 30000000f;
		cam.update();
	}


}
