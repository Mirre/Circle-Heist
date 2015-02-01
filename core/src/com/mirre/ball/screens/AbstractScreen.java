package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	
	private Game game;

	public AbstractScreen(Game game){
		setGame(game);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	
	public boolean levelExists(int i){
		return Gdx.files.internal("data/level" + i + ".png").exists();
	}

	@Override
	public void show() {
		Gdx.app.log("Show", "Test");
	}


	@Override
	public void pause() {
		Gdx.app.log("Pause", "Test");
	}

	@Override
	public void resume() {
		Gdx.app.log("Resume", "Test");
	}

	@Override
	public void hide() {
		Gdx.app.log("Hide", "Test");
	}

	@Override
	public void dispose() {
		Gdx.app.log("Dispose", "Test");
	}

}
