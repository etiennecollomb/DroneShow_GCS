package com.drone.show.generic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.drone.show.GlobalManager;


public class Tools {

	/** staff for text */
	public static GlyphLayout glyphLayout = new GlyphLayout();

	public static boolean isExternalStorageAvailable = Gdx.files.isExternalStorageAvailable();
	public static boolean isLocalStorageAvailable = Gdx.files.isLocalStorageAvailable();

	private static Logger logger = LogManager.getLogger(Tools.class);


	/**************************************
	 * 
	 * Constructors
	 * 
	 **************************************/

	/** return font given a height size pixel */
	public static BitmapFont getFontFromHeightSize(String fontPath, float heightSizeInPixel){

		BitmapFont bitmapFont;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int) heightSizeInPixel;
		bitmapFont = generator.generateFont(parameter);

		generator.dispose();

		return bitmapFont;
	}


	/**************************************
	 * 
	 * Methods
	 * 
	 **************************************/

	public static void listFilesFromLocalDir(String pathName) {

		FileHandle dirHandle;
		dirHandle = Gdx.files.local(pathName);

		for (FileHandle entry: dirHandle.list()) {
			System.out.println("file : " + entry.path());
		}

	}

	public static boolean isLocalFileExist(String pathName){
		return Gdx.files.local(pathName).exists();
	}

	public static boolean isLocalDir(String pathName){
		return Gdx.files.local(pathName).isDirectory();
	}


	public static void createLocalDir(String pathName){
		Gdx.files.local(pathName).mkdirs();
	}

	public static void deleteLocalFile(String pathName){
		Gdx.files.local(pathName).delete();
	}
	
	public static void emptyLocalDir(String dir) {
		
		FileHandle dirHandle = Gdx.files.local(dir);
		
		for (FileHandle entry: dirHandle.list()) {
			Tools.deleteLocalFile( entry.path() );	
		}
	}


	public static Texture getTexturefromFrameBuffer(int x, int y, int width, int height){

		int frameBufferWidth = Gdx.graphics.getBackBufferWidth();
		int frameBufferHeight = Gdx.graphics.getBackBufferHeight();

		if(x > frameBufferWidth) x = frameBufferWidth;
		if(x+width > frameBufferWidth) width = frameBufferWidth -x;

		if(y > frameBufferHeight) y = frameBufferHeight;
		if(y+height > frameBufferHeight) height = frameBufferHeight -y;


		byte[] pixels = ScreenUtils.getFrameBufferPixels(x, y, width, height, true);

		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);

		Texture texture = new Texture( pixmap );

		return texture;

	}

	public static Pixmap getPixmapFromTexture(Texture pTexture){
		TextureData textureData = pTexture.getTextureData();
		if(!textureData.isPrepared())
			textureData.prepare();
		return textureData.consumePixmap();
	}


//	/** disable all actors from a group */
//	public static void disableTouchGroup(Group group_){
//
//		for(Actor actor : group_.getChildren()){
//			actor.setTouchable(Touchable.disabled);
//		}
//	}
//
//	/** enable all actors from a group */
//	public static void enableTouchGroup(Group group_){
//
//		for(Actor actor : group_.getChildren()){
//			actor.setTouchable(Touchable.enabled);
//		}
//	}


	/** cancel touch focus recursively for all actors from a group
	 * false to exclude the parent group itself, true if we include the parent group */
	public static void cancelTouchFocusGroup(Stage stage, Group group_, boolean isExcludeParent){

		if(isExcludeParent)
			stage.cancelTouchFocus(group_);

		for(Actor actor : group_.getChildren()){
			if(actor instanceof Group){
				cancelTouchFocusGroup(stage, (Group) actor, true);
			}else{
				stage.cancelTouchFocus(actor);
			}
		}
	}
	
	
	
	/** reduce Size of all element of a group
	 * 2f = x2
	 */
	public static void resizeGroup(Stage stage, Group group_, float proportionalSizeCoeff){

		for(Actor actor : group_.getChildren()){
			if(actor instanceof Group){
				resizeGroup(stage, (Group) actor, proportionalSizeCoeff);
			}else{
				actor.setSize(actor.getWidth()*proportionalSizeCoeff, actor.getHeight()*proportionalSizeCoeff);
			}
		}
	}


	/** return the number of days in one month */
	public static int getNumberOfDayFromMonth(int month){

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(year, month, 01);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}


	/** return the localized month name */
	public static String getMonthName(int month){

		String monthName;

		switch(month){
		case 1:
			monthName = GlobalManager.i18NBundle.get("january");
			break;
		case 2:
			monthName = GlobalManager.i18NBundle.get("february");
			break;
		case 3:
			monthName = GlobalManager.i18NBundle.get("march");
			break;
		case 4:
			monthName = GlobalManager.i18NBundle.get("april");
			break;
		case 5:
			monthName = GlobalManager.i18NBundle.get("may");
			break;
		case 6:
			monthName = GlobalManager.i18NBundle.get("june");
			break;
		case 7:
			monthName = GlobalManager.i18NBundle.get("july");
			break;
		case 8:
			monthName = GlobalManager.i18NBundle.get("august");
			break;
		case 9:
			monthName = GlobalManager.i18NBundle.get("september");
			break;
		case 10:
			monthName = GlobalManager.i18NBundle.get("october");
			break;
		case 11:
			monthName = GlobalManager.i18NBundle.get("november");
			break;
		case 12:
			monthName = GlobalManager.i18NBundle.get("december");
			break;
		default: monthName = null;
		break;
		}

		return monthName;
	}


	/** return the time human readable */
	public static String convertFloatSecToStringTime(float seconds){

		String time = "";

		int hours = getHourFromTime(seconds);
		int minutes = getMinFromTime(seconds);
		int secondes = getSecFromTime(seconds);

		if(hours<10)
			time = time + "0" + hours;
		else
			time = time + hours;

		time = time + ":";

		if(minutes<10)
			time = time + "0" + minutes;
		else
			time = time + minutes;

		time = time + ":";

		if(secondes<10)
			time = time + "0" + secondes;
		else
			time = time + secondes;

		return time;

	}

	public static int getHourFromTime(float seconds){
		return  (int)seconds / (int)3600;
	}

	public static int getMinFromTime(float seconds){
		return (int)seconds%3600 / (int)60;
	}

	public static int getSecFromTime(float seconds){
		return (int)seconds%60;
	}


	/** return the time human readable */
	public static String convertDateToString(Date date){

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		return formatter.format(date);
	}

	
	public static int getCurrentMonth(){
		Calendar calendarNow = new GregorianCalendar();
		calendarNow.setTime(new Date());
		return calendarNow.get(Calendar.MONTH);
	}

	
	/** Get the current time in microseconds. */
	public static long getTimeMilliSeconds() {
		return new Date().getTime();
	}


//	/** Set cell size from percentage */
//	public static void addCellToLayoutInPercentage(Table tableLayout, Actor actor, float percentageWidth, float percentageHeight, int colspan){
//
//		tableLayout.add( actor )
//				.prefWidth(Value.percentWidth(percentageWidth/100f, tableLayout ))
//				.prefHeight(Value.percentWidth(percentageHeight/100f, tableLayout ))
//				.colspan(colspan);
//
//	}

	
	static Vector3 getSumVectors(ArrayList<Vector3> vectors) {

		Vector3 vector = new Vector3(0,0,0);
		
		int size = vectors.size();
		for(int i=0; i<size; i++) {
			vector.add(vectors.get(i));
		}

		return vector;

	}


	public static Vector3 averageVector(ArrayList<Vector3> vectors) {

		Vector3 vector = Tools.getSumVectors(vectors);
		vector.scl(1f/(float)vectors.size());
		
		return vector;

	}

	
	public static Quaternion lookAt(Vector3 this_position, Vector3 position, Vector3 up) {
		
		Vector3 dir = new Vector3();
		Quaternion rotation = new Quaternion();
		Vector3 tmp = new Vector3();
		Vector3 tmp2 = new Vector3();
		dir.set(position).sub(this_position).nor();
		tmp.set(up).crs(dir).nor();
		tmp2.set(dir).crs(tmp).nor();
		rotation.setFromAxes(tmp.x, tmp2.x, dir.x, tmp.z, tmp2.z, dir.z, -tmp.y, -tmp2.y, -dir.y);
		return rotation;
		
	}
	
	
	public static float roundFloat(float number, int numberOfDecimal) {
		float tempPow = (float)Math.pow(10, numberOfDecimal);
		int roundedFloat = ((int)(number * tempPow ));
		if( (number - roundedFloat) > 0.5f)
			roundedFloat = roundedFloat+1;
		return  (float)roundedFloat / tempPow;
	}
	
	/** Conversion Latitude , Longitude to meter */
	//http://python.dronekit.io/examples/mission_basic.html
	//chercher get_location_metres
	// et get_distance_metres
	
	public static float earth_radius=6378137.0f; //Radius of "spherical" earth
	
	public static float add_distance_to_Latitude(float origLatitude, float distance_in_meter_to_North) {
		float dLat = distance_in_meter_to_North/earth_radius;
		return (float) (origLatitude + (dLat * 180f/Math.PI));
	}
	
	public static float add_distance_to_Longitude(float origLatitude, float distance_in_meter_to_East) {
		float dLon = (float) (distance_in_meter_to_East/(earth_radius*Math.cos(Math.PI*origLatitude/180f)));
		return (float) (origLatitude + (dLon * 180f/Math.PI));
	}
	
	
	//http://www.csgnetwork.com/gpsdistcalc.html
//	static float LONGITUDE_TO_METER_COEFF = 0.0000054f; //each 0.0000054 of longitude = 0.9995512270189973 meter
//	static float LATITUDE_TO_METER_COEFF = 0.0000054f; //each 0.0000054 of latitude = 0.9995512270189973 meter
//	
//	public static float convertLatitudeToCentiMeter(float latitude) {
//		return (float) (100f*latitude/LATITUDE_TO_METER_COEFF); //in cm
//	}
//	
//	public static float convertLongitudeToCentiMeter(float longitude) {
//		return (float) (100f*longitude/LONGITUDE_TO_METER_COEFF); //in cm
//	}
//	
//	public static float convertCentiMeterToLatitude(float cm) {
//		return (float) (cm*LATITUDE_TO_METER_COEFF/100f); //in cm
//	}
//	
//	public static float convertCentiMeterToLongitude(float cm) {
//		return (float) (cm*LONGITUDE_TO_METER_COEFF/100f); //in cm
//	}
	
	
	
	
	
	
	public static Color setColorGreenToRed(Color color, float minValue, float maxValue, float currentValue) {
		
		if(currentValue>maxValue) currentValue = maxValue;
		if(currentValue<minValue) currentValue = minValue;
				
		float value = (currentValue-minValue)/(maxValue-minValue);
		if(value>0.5)
			color.set( (1f-value)*2f, 1f, 0f, 1f);
		else
			color.set( 1f, value*2f, 0f, 1f);
		
		return color;
	}

	public static synchronized void writeLog(String log) {
		logger.info(log);
	}
	

	/**
	 * currentValue [minValue, maxValue]
	 */
	public static Color setColorRedToGreenToBlue(Color color, float minValue, float maxValue, float currentValue) {

		if(currentValue>maxValue) currentValue = maxValue;
		if(currentValue<minValue) currentValue = minValue;

		float value = (currentValue-minValue)/(maxValue-minValue);
		
		if(value<0.25f)
			color.set( 1f, value*4f, 0f, 1f);
		
		else if(value<0.50f)
			color.set( 1-((value-0.25f)*4f), 1f, 0f, 1f);
		
		else if(value<0.75f)
			color.set( 0f, 1f, (value-0.5f)*4f, 1f);
		
		else if(value<1.0f)
			color.set( 0f, 1f-((value-0.75f)*4f), 1f, 1f);

		return color;
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




}


