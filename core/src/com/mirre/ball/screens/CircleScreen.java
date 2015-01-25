package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.mirre.ball.managers.LevelRenderer;
import com.mirre.ball.objects.Level;

public class CircleScreen extends GameScreen {

	private Level level;
	private LevelRenderer renderer;
	private int levelID;

	public CircleScreen(Game game, int level) {
		super(game);
		setLevelID(level);
	}

	@Override
	public void show() {
		setLevel(new Level(getGame(), getLevelID()));
		setRenderer(new LevelRenderer(getLevel()));
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			getGame().setScreen(new StartScreen(getGame()));
			return;
		}
		
		getLevel().update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getRenderer().render(delta);
	}

	@Override
	public void hide () {
		Gdx.app.log("CircleRun", "reset game screen");
		getRenderer().reset();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		Gdx.app.log("CircleRun", "end game screen");
		getRenderer().dispose();
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public LevelRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(LevelRenderer renderer) {
		this.renderer = renderer;
	}
	
}
