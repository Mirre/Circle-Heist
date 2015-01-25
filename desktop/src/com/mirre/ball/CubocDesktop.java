
package com.mirre.ball;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class CubocDesktop {
	public static void main (String[] argv) {
		new LwjglApplication(new CircleHeist(), "CircleRun", 480, 320);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}
