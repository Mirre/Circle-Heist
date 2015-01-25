package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.CircleHeist;

public class StartScreen extends GameScreen {
	
	private TextureRegion title;
	private SpriteBatch batch;
	//private float time = 0;

	public StartScreen(Game game) {
		super(game);
	}

	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(title, 0, 0);
		batch.end();
		
		
		//Gdx.app.log("XY", "" + Gdx.input.getX() + " " + Gdx.input.getY());
		//time += delta;
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && getLevelPressed() != 666){
			int i = getLevelPressed();
			Gdx.app.log("levels", "" + ((CircleHeist) getGame()).getCompletedLevels() + " " + i);
			if(levelExists(i) && ((CircleHeist) getGame()).getCompletedLevels() >= i)
				getGame().setScreen(new CircleScreen(getGame(), i));
		}
	}
	
	//70 125 195 220 - 96
	//195-70=125
	//220-125=95
	public int getLevelPressed(){
		
		int x1 = 70;
		int x2 = 195;
		int y1 = 125;
		int y2 = 220;
		
		int width = 125;
		int height = 95;
		
		int ladder = 0;
		for(int i = 0 ; i < 12 ; i++){
			if(Gdx.input.getX() >= (x1 + (width * i)) && Gdx.input.getX() <= (x2 + (width * i))
				&& Gdx.input.getY() >= (y1 - (height * ladder)) && Gdx.input.getY() <= (y2 -(height * ladder))){
				return i + 1;
			}if(i == 4 || i == 8)
				ladder++;
		} 
		return 666;
	}
	
	
	@Override
	public void show() {
		title = new TextureRegion(new Texture(Gdx.files.internal("data/title.png")), 0, 0, 480, 320);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
	}

	@Override
	public void hide() {
		Gdx.app.log("CircleRun", "dispose main menu");
		batch.dispose();
		title.getTexture().dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		title.getTexture().dispose();
	}

	@Override
	public void resize(int width, int height) {
		
	}

}
