package com.drone.show.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.drone.show.MyDroneShow;

public class DesktopLauncher {

	public static final int WINDOW_WIDTH = 1400;
	public static final int WINDOW_HEIGHT = 800;
	
	public static void main (String[] arg) {
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.x = 100;
		config.y = 100;
		config.width = WINDOW_WIDTH;
		config.height = WINDOW_HEIGHT;
		
		new LwjglApplication(new MyDroneShow(), config);
	}
}
