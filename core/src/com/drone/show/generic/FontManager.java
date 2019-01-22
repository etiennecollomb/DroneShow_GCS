package com.drone.show.generic;

import com.drone.show.GlobalManager;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.IntArray;

public class FontManager {

	public static String FONT_NAME_PREFIX = "font";

	private IntArray bitmapFontsSize;
	private ArrayList<BitmapFont> bitmapFonts;



	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	public FontManager() {

		if (GlobalManager.ISDEBUG) System.out.println("Load FontManager...");
		
		this.setBitmapFontsSize( new IntArray() );
		this.setBitmapFonts( new ArrayList<BitmapFont>() );

		this.createAllBitmapFonts();
	}



	/**************************************
	 * 
	 * Methods
	 * 
	 **************************************/

	
	public void createAllBitmapFonts() {

		/** get all Font Sizes */
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal(AssetsLoader.FONTS_DIR);

		for (FileHandle entry: dirHandle.list()) {
			int size = Integer.parseInt( entry.name().substring(FontManager.FONT_NAME_PREFIX.length()) );
			this.getBitmapFontsSize().add(size);
		}

		this.getBitmapFontsSize().sort();

		/** create all bitmapFont */
		for(int i=0; i<this.getBitmapFontsSize().size; i++) {
			String fontPath = AssetsLoader.FONTS_DIR+"/"+FontManager.FONT_NAME_PREFIX+this.getBitmapFontsSize().get(i)+"/font.fnt";
			this.getBitmapFonts().add( GlobalManager.assestsLoader.getAssetManager().get(fontPath, BitmapFont.class) );
			
		}
	}


	public BitmapFont getBitmapFonts(float wantedSize) {

		BitmapFont bitmapFont = null;

		/** get the nearest bitmapFont regarding the wanted size */
		for(int i=0; i<this.getBitmapFontsSize().size; i++) {
			if(wantedSize < this.getBitmapFontsSize().get(i)){
				break;
			}
			bitmapFont = this.getBitmapFonts().get(i);
		}

		if(bitmapFont == null)
			bitmapFont = this.getBitmapFonts().get(0);

		if(GlobalManager.ISDEBUG) System.out.println("Font size used : "+ bitmapFont.getXHeight());

		return bitmapFont;

	}



	/**************************************
	 * 
	 * Getter / Setter
	 * 
	 **************************************/

	public IntArray getBitmapFontsSize() {
		return bitmapFontsSize;
	}

	public void setBitmapFontsSize(IntArray bitmapFontsSize) {
		this.bitmapFontsSize = bitmapFontsSize;
	}

	public ArrayList<BitmapFont> getBitmapFonts() {
		return bitmapFonts;
	}

	public void setBitmapFonts(ArrayList<BitmapFont> bitmapFonts) {
		this.bitmapFonts = bitmapFonts;
	}

}
