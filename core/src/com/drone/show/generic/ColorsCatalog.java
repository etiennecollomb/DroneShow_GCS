package com.drone.show.generic;

import com.badlogic.gdx.graphics.Color;

public class ColorsCatalog {


	/** Static colors */
	public static final Color MAIN_BLUE = new Color(47f/256f, 85f/256f, 151f/256f, 1f);
	public static final Color BACKGROUND_GRID_BLUE = new Color(47f/256f, 85f/256f, 151f/256f, 0.8f);
	public static final Color SELECTED_CELL_WHITE = new Color(256f/256f, 256f/256f, 256f/256f, 0.3f);

	public static final Color SELECTED_VALUE_YELLOW = new Color(255f/256f, 217f/256f, 102f/256f, 1f);
	public static final Color SAME_VALUE_YELLOW = new Color(215f/256f, 183f/256f, 86f/256f, 1f);

	public static final Color CONFLICTED_VALUE_RED = new Color(255f/256f, 102f/256f, 102f/256f, 1f);
	
	public static final Color GREY_01 = new Color(200f/256f, 200f/256f, 200f/256f, 1f);
	public static final Color GREY_02 = new Color(175f/256f, 175f/256f, 175f/256f, 1f);
	public static final Color GREY_03 = new Color(150f/256f, 150f/256f, 150f/256f, 1f);
	
	
	
	/** For colors that might change */
	
	public static Color getWhiteColor() {
		return new Color(1f,1f,1f, 1f);
	}
	
	public static Color getRedColor() {
		return new Color(1f,0f,0f, 1f);
	}
	
	public static Color getGreenColor() {
		return new Color(0f,1f,0f, 1f);
	}
	
	public static Color getBlueColor() {
		return new Color(0f,0f,1f, 1f);
	}
	
	
	private static float k = 1f/255f;
	
	public static Color getDroneColor1() {
		return new Color(k *31f, k *43f, k *51, 1f);
	}
	
	public static Color getDroneColor2() {
		return new Color(k *25f, k *25f, k *25f, 1f);
	}
	
	public static Color getDroneColor3() {
		return new Color(k *80f, k *80f, k *80f, 1f);
	}
	
}
