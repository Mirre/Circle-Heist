package com.mirre.ball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mirre.ball.CircleHeist;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	
		//config.fullscreen = true;
		config.width = 1080;
		config.height = 720;
		new LwjglApplication(new CircleHeist(), config);
	}
}
