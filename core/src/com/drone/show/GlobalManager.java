package com.drone.show;

import java.util.Locale;
import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.drone.show.generic.Tools;
import com.drone.show.hud.models.ApplicationModel;
import com.drone.show.hud.tween.ActorTween;
import com.drone.show.hud.views.ButtonView;
import com.drone.show.hud.views.PopupView;
import com.drone.show.hud.views.TextView;
import com.drone.show.world.ui.WorldViewer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.drone.show.gcs.RealDroneModel;
import com.drone.show.gcs.RealWorldModel;
import com.drone.show.generic.AssetsLoader;
import com.drone.show.generic.FontManager;
import com.drone.show.generic.SoundManager;


public class GlobalManager {

	/** debug mode */
	public static boolean ISDEBUG = true; //true or false
	
	/** taille DEVICE SCREEN */
	public static final int DEVICE_SCREEN_WIDTH = Gdx.graphics.getWidth();
	public static final int DEVICE_SCREEN_HEIGHT = Gdx.graphics.getHeight();

	public static OrthographicCamera camera;

	/** Stages and Viewer */
	public static Stage HUDStage;
	public static WorldViewer worldViewer;

	/** SpriteBatch */
	public static SpriteBatch spriteBatch;
	public static SpriteBatch fontSpriteBatch;
	public static ShapeRenderer shapeRenderer;

	/** Tween */
	public static TweenManager tweenManager;

	/** Localization */
	public static I18NBundle i18NBundle;

	/** Assets Loader **/
	public static AssetsLoader assestsLoader;

	/** Random */
	public static Random random;

	/** Font Manager */
	public static FontManager fontManager;

	/** Sound Manager */
	public static SoundManager soundManager;

	/** Models **/
	public static ApplicationModel applicationModel;
	
	/** Shared data with Thread GCS
	 * for the current selected drone
	 **/
	public static RealWorldModel realWorldModel;



	/**************************************
	 *
	 * Methods
	 *
	 **************************************/

	public static void init(){

		if(GlobalManager.ISDEBUG) System.out.println("Screen Width: " +GlobalManager.DEVICE_SCREEN_WIDTH);
		if(GlobalManager.ISDEBUG) System.out.println("Screen Height: " +GlobalManager.DEVICE_SCREEN_HEIGHT);

		/** Data Loader **/
		assestsLoader = new AssetsLoader();
		
		GlobalManager.random = new Random();

		GlobalManager.camera = new OrthographicCamera();

		GlobalManager.HUDStage = new Stage(new ScreenViewport());
		GlobalManager.worldViewer = new WorldViewer();

		/** Input Processor */
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(GlobalManager.HUDStage);
		inputMultiplexer.addProcessor(worldViewer.camController);
		Gdx.input.setInputProcessor(inputMultiplexer);


		GlobalManager.spriteBatch = new SpriteBatch();
		GlobalManager.fontSpriteBatch = new SpriteBatch();
		GlobalManager.shapeRenderer = new ShapeRenderer();

		GlobalManager.tweenManager = new TweenManager();

		/** Tween Accessor */
		Tween.registerAccessor(ButtonView.class, new ActorTween());
		Tween.registerAccessor(TextView.class, new ActorTween());
		Tween.registerAccessor(PopupView.class, new ActorTween());

		/** localization */
		//https://www.science.co.il/language/Locale-codes.php
		FileHandle baseFileHandle = Gdx.files.internal("i18n/localization");
		Locale locale = new Locale("fr");
		i18NBundle = I18NBundle.createBundle(baseFileHandle, locale);

		/** Font Manager */
		fontManager = new FontManager();

		/** Sound Manager */
		soundManager = new SoundManager();
		
		GlobalManager.applicationModel = new ApplicationModel();
		
		GlobalManager.realWorldModel = new RealWorldModel();
	}


	public static void drawSprite(Batch batch, Sprite sprite, float screenX, float screenY, float screenWidth, float screenHeight, float scaleX, float scaleY){

		batch.begin();
		//draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
		batch.draw(sprite, screenX, screenY, screenWidth/2f , screenHeight/2f, screenWidth, screenHeight, scaleX, scaleY, 0.0f);
		batch.end();

	}


	public static void drawRectangle(Batch batch, float screenX, float screenY, float screenWidth, float screenHeight, Color color, ShapeType shapeType, float scaleX, float scaleY){

		/** these three lines are needed in order to keep shaperenderer drawn within the parent
		 * this is not the case from scratch because it does not use the same batch as actor...etc
		 */
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		//shapeRenderer.translate(screenX, screenY, 0);

		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(color);

		GlobalManager.shapeRenderer.begin(shapeType); //ex: ShapeType.Filled
		//rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees)
		GlobalManager.shapeRenderer.rect(screenX, screenY, screenWidth/2f , screenHeight/2f, screenWidth, screenHeight, scaleX, scaleY, 0.0f);
		GlobalManager.shapeRenderer.end();
		Gdx.gl.glDisable(GL30.GL_BLEND);

	}


	public static void drawCircle(Batch batch, float screenX, float screenY, float screenRadius, Color color, ShapeType shapeType){

		/** these three lines are needed in order to keep shaperenderer drawn within the parent
		 * this is not the case from scratch because it does not use the same batch as actor...etc
		 */
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		//shapeRenderer.translate(screenX, screenY, 0);

		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(color);

		GlobalManager.shapeRenderer.begin(shapeType); //ex: ShapeType.Filled
		//rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees)
		GlobalManager.shapeRenderer.circle(screenX, screenY, screenRadius);
		GlobalManager.shapeRenderer.end();
		Gdx.gl.glDisable(GL30.GL_BLEND);

	}


	public static void drawFont(Batch batch, BitmapFont font, float screenX, float screenY, String stringValue, float scaleX, float scaleY, float targetWidth, int halign){

		/** these three lines are needed in order to keep shaperenderer drawn within the parent
		 * this is not the case from scratch because it does not use the same batch as actor...etc
		 */
		fontSpriteBatch.setProjectionMatrix(batch.getProjectionMatrix());
		fontSpriteBatch.setTransformMatrix(batch.getTransformMatrix());
		//fontSpriteBatch.translate(screenX, screenY, 0);

		GlobalManager.fontSpriteBatch.begin();
		font.getData().setScale(scaleX, scaleY);

		//get size of text (used for centering)
		//Tools.glyphLayout.setText(font, stringValue); //WRAP Version (false dans notre cas, on fait du center justified)
		Tools.glyphLayout.setText(font, stringValue, Color.WHITE, targetWidth, halign, false); //WRAP Version
		//offset to center the text on (x,y)
		float offsetForCenteringTextX = 0;//- Tools.glyphLayout.width/2;
		float offsetForCenteringTextY = + Tools.glyphLayout.height/2;

		//font.draw(GlobalManager.fontSpriteBatch, stringValue, screenX +offsetForCenteringTextX, screenY +offsetForCenteringTextY); //NO WRAP version
		font.draw(GlobalManager.fontSpriteBatch, stringValue,
				screenX +offsetForCenteringTextX, screenY +offsetForCenteringTextY,
				targetWidth, halign, false);  //WRAP Version (false dans notre cas, on fait du center justified)
		GlobalManager.fontSpriteBatch.end();

	}


	public static void resizeBatch(Batch batch, int width, int height) {
		GlobalManager.camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(GlobalManager.camera.combined);
	}


}


