package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mirre.ball.CircleHeist;

public abstract class GameScreen implements Screen {
	
	private Game game;

	public GameScreen(Game game){
		setGame(game);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public static boolean isCursorInside(int x1, int y1, int x2, int y2){
		if(Gdx.input.getX() >= x1 && Gdx.input.getX() <= x2 && Gdx.input.getY() >= y1 && Gdx.input.getY() <= y2){
			return true;
		}
		return false;
	}
	
	public void setScreen(Screen screen, int levelExists){
		if(levelExists(levelExists) && ((CircleHeist)getGame()).getCompletedLevels() >= levelExists)
			getGame().setScreen(screen);
	}
	
	public boolean levelExists(int i){
		return Gdx.files.internal("data/level" + i + ".png").exists();
	}

}
