package com.mirre.ball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mirre.ball.screens.StartScreen;

public class CircleHeist extends Game {
	
	private int completedLevels;

	
	@Override
	public void create(){
		setScreen(new StartScreen(this));
		Preferences pref = Gdx.app.getPreferences("LevelInfo");
		setCompletedLevels(pref.getInteger("LevelsCompleted") != 0 ? pref.getInteger("LevelsCompleted") : 1);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		
		Preferences pref = Gdx.app.getPreferences("LevelInfo");
		pref.putInteger("LevelsCompleted", getCompletedLevels());
		pref.flush();
		
	}

	public int getCompletedLevels() {
		return completedLevels;
	}

	public void setCompletedLevels(int completedLevels) {
		this.completedLevels = completedLevels;
	}

}
