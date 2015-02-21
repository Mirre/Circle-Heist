package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mirre.ball.utils.Assets;

public class LoadingScreen extends AbstractScreen {

	
	public LoadingScreen(Game game) {
		super(game);
		Assets.getInstance().load();
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Assets.getInstance().getAssetManager().update()) {
			Gdx.app.log("Loading", "Complete");
			getGame().setScreen(new StartScreen(getGame()));
		}

		float progress = Assets.getInstance().getAssetManager().getProgress();
		Gdx.app.log("Loading", "at " + progress);
	
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

	
}
