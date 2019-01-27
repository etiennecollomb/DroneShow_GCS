package com.drone.show;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.drone.show.gcs.GCSThread;
import com.drone.show.hud.popups.ScenariosPopup;
import com.drone.show.hud.popups.TelemetryPopup;

public class MyDroneShow extends ApplicationAdapter {



	//TODO : a mettre au propre a part
	public static final int DRONE_ID = 1; //doit etre definit plus tard par l id de la radio ?



	@Override
	public void create () {
		
		GlobalManager.init();

		TelemetryPopup telemetryPopup = new TelemetryPopup(10, 400, 1.5f);
		ScenariosPopup scenarioPopup = new ScenariosPopup(10, 100, 1f);

		GlobalManager.HUDStage.addActor(scenarioPopup);
		GlobalManager.HUDStage.addActor(telemetryPopup);

		
		/** Start GCS thread **/
		new Thread( new GCSThread() ).start();
		
		

	}

	@Override
	public void render () {

		float deltaTime = Gdx.graphics.getDeltaTime();

		GlobalManager.tweenManager.update(deltaTime);

		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/** WorldViewer */
		GlobalManager.worldViewer.onUpdate(deltaTime);
		GlobalManager.worldViewer.render();

		/** HUD */
		GlobalManager.HUDStage.act(deltaTime);
		GlobalManager.HUDStage.draw();

	}

	@Override
	public void dispose () {
		GlobalManager.HUDStage.dispose();
	}

	public void resize (int width, int height) {
		GlobalManager.HUDStage.getViewport().update(width, height, true);
	}

}
