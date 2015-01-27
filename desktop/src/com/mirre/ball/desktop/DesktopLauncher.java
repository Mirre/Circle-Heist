package com.mirre.ball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mirre.ball.CircleHeist;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.height = 640;
		//config.width = 960;
		//config.fullscreen = true;
		new LwjglApplication(new CircleHeist(), config);
	}
}
