package com.drone.show.world.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.drone.show.GlobalManager;

public class ZAxisCameraInputController extends GestureDetector {

	/** The button for rotating the camera. */
	public int rotateButton = Buttons.LEFT; //Souris
	/** The button for translating the camera along the up/right plane */
	public int translateButton = Buttons.RIGHT; //Souris
	/** The angle to rotate when moved the full width or height of the screen. */
	public float rotateAngle = 360f;
	/** The units to translate the camera when moved the full width or height of the screen. */
	public float translateUnits = 10f; // FIXME auto calculate this based on the target

	/** The weight for each scrolled amount. */
	public float scrollFactor = -0.1f;
	
	/** The target to rotate around. */
	public Vector3 target = new Vector3();
	
	/** The camera. */
	public Camera camera;
	/** The current (first) button being pressed. */
	protected int button = -1;

	private float startX, startY;
	private final Vector3 tmpV1 = new Vector3();
	private final Vector3 tmpV2 = new Vector3();



	protected ZAxisCameraInputController (final Camera camera) {
		super(new GestureAdapter());
		this.camera = camera;
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		
		/** Cancel Scroll Focus from HUD Stage (ie: scrollpan keep it otherwise) */
		GlobalManager.HUDStage.setScrollFocus(null);
		
		startX = screenX;
		startY = screenY;
		this.button = button;
		return super.touchDown(screenX, screenY, pointer, button) ;
	}

	
	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		
		if (button == this.button) this.button = -1;
		return super.touchUp(screenX, screenY, pointer, button);
	}
	

	/** Rotate et Translate **/
	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {

		final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
		final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
		startX = screenX;
		startY = screenY;

		if (button == rotateButton) {
			tmpV1.set(camera.direction).crs(camera.up).y = 0f;

			/** Point de rotation de la scene */
			if(WorldViewer.isCameraCenteredOnItself) {
				//Rotation autour de l axe de la camera
				target.set(camera.position);
			}else {
				//Rotation par rapport au centre de la scene
				target.set(0,0,0);
			}

			camera.rotateAround(target, tmpV1.nor(), deltaY * rotateAngle);
			camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);

		}
		else if (button == translateButton) {
			if(WorldViewer.isCameraCenteredOnItself) {
				//On autorise la translation horizontal que si on est centre sur la camera
				camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits * WorldViewer.BASIS_SCALE));
			}
			camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits * WorldViewer.BASIS_SCALE));
		}
		
		camera.update();
		return true;
	}

	
	/** Zoom **/
	@Override
	public boolean scrolled (int amount_) {
		
		float amount = amount_ * scrollFactor * translateUnits;
		camera.translate(tmpV1.set(camera.direction).scl(amount*WorldViewer.BASIS_SCALE));

		camera.update();
		return true;
	}
	
	
	@Override
	public boolean keyDown (int keycode) {
		return false;
	}
	

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}
	

}
