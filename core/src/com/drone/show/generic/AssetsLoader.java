package com.drone.show.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.drone.show.GlobalManager;
import com.drone.show.world.ui.widget.DroneWidget;
import com.drone.show.world.ui.widget.HumanWidget;
import com.drone.show.world.ui.widget.WorldSphereWidget;

public class AssetsLoader {

	public static String UI_DIR = "ui";
	public static String FONTS_DIR = "fonts/bitmapfonts";
	public static String MUSICS_DIR = "musics";
	public static String SOUNDS_DIR = "sounds";

	/** Should not be static ! **/
	AssetManager assetManager;
	TextureAtlas textureAtlas;


	/**************************************
	 *
	 * Constructors
	 *
	 **************************************/

	public AssetsLoader(){

		this.setAssetManager( new AssetManager() );

		/** load 3D */
		assetManager.load(WorldSphereWidget.WORLD_SPHERE_3D_NAME, Model.class);
		assetManager.load(HumanWidget.HUMAN_3D_NAME, Model.class);
		assetManager.load(DroneWidget.MODEL_3D_DRONE, Model.class);
		assetManager.load(DroneWidget.MODEL_2D_HALO, Texture.class);
		assetManager.load(DroneWidget.MODEL_3D_BULB, Model.class);

		/** load all UI stuff */
		this.loadAllUI();
		
		/** load all fonts */
		this.loadAllFont();

		/** load all sounds */
		this.loadAllSounds();

		/** load all musics */
		this.loadAllMusics();

		/** Show load progress */
		this.showLoadProgress();
		
	}




	public void showLoadProgress(){
		Timer timer = new Timer(500f);
		while (!assetManager.update()) {
			if(timer.isFinished()) {
				System.out.println("Loading assets: " + Tools.roundFloat( assetManager.getProgress()*100f, 1) + "%");
				timer.reset();
			}
		}
		System.out.println("Loading assets: " + assetManager.getProgress()*100f + "%");
	}


	/**************************************
	 *
	 * Methods
	 *
	 **************************************/

	public void loadAllUI(){

		//get all font from directory's font
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal(AssetsLoader.UI_DIR);

		dirHandle.isDirectory();

		for (FileHandle entry: dirHandle.list()) {
			if(GlobalManager.ISDEBUG) System.out.println("add to load ui: " + entry.path());
			this.getAssetManager().load(entry.path(), Texture.class);
		}

	}
	
	
	public void loadAllFont(){

		//get all font from directory's font
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal(AssetsLoader.FONTS_DIR);

		dirHandle.isDirectory();

		for (FileHandle entry: dirHandle.list()) {
			if(GlobalManager.ISDEBUG) System.out.println("add to load font: " + entry.path());
			this.getAssetManager().load(AssetsLoader.FONTS_DIR+"/"+entry.name()+"/font.fnt", BitmapFont.class);
		}

	}

	public void loadAllSounds(){

		//get all font from directory's font
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal(AssetsLoader.SOUNDS_DIR);

		for (FileHandle entry: dirHandle.list()) {

			if(entry.extension().equals( SoundManager.SOUND_EXTENSION )) {
				if (GlobalManager.ISDEBUG) System.out.println("add to load sound: " + entry.path());
				this.getAssetManager().load(entry.path(), Sound.class);
			}
		}
	}


	public void loadAllMusics(){

		//get all font from directory's font
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal(AssetsLoader.MUSICS_DIR);

		for (FileHandle entry: dirHandle.list()) {

			if(entry.extension().equals( SoundManager.SOUND_EXTENSION )) {
				if (GlobalManager.ISDEBUG) System.out.println("add to load music: " + entry.path());
				this.getAssetManager().load(entry.path(), Music.class);
			}
		}
	}



	/**************************************
	 *
	 * Override
	 *
	 **************************************/

	/**************************************
	 *
	 * Getter / Setter
	 *
	 **************************************/

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}







}





