package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelEndScreen extends GameScreen {

	private boolean won;
	private TextureRegion screenTexture;
	private SpriteBatch batch;
	private int levelID;
	
	public LevelEndScreen(Game game, boolean win, int levelID) {
		super(game);
		setWon(win);
		setLevelID(levelID);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(getScreenTexture(), 0, 0);
		batch.end();
		
		//Gdx.app.log("XY", "" + Gdx.input.getX() + " " + Gdx.input.getY());
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && getButtonPressed() != 666){
			int i = getButtonPressed();
			if(i == 0){
				setScreen(new CircleScreen(getGame(), getLevelID() + 1), getLevelID() + 1);
			}if(i == 1){
				getGame().setScreen(new StartScreen(getGame()));
			}
				
		}
	}

	public int getButtonPressed(){
		int x1 = 265;
		int y1 = 283;
		
		int width = 88;
		int height = 28;
		
		int dist = 27;
		
		if(isCursorInside(x1, y1, x1 + width, y1 + height))
			return 0;
		else if(isCursorInside(x1, (y1 + height + dist), x1 + width, (y1 + (height * 2) + dist)))
			return 1;
		return 666;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		if(isWon())
			setScreenTexture(new TextureRegion(new Texture(Gdx.files.internal("data/Victory.png")), 0, 0, 480, 320));
		else
			setScreenTexture(new TextureRegion(new Texture(Gdx.files.internal("data/Failed.png")), 0, 0, 480, 320));
		setBatch(new SpriteBatch());
		getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
	}

	@Override
	public void hide() {
		getBatch().dispose();
		getScreenTexture().getTexture().dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public TextureRegion getScreenTexture() {
		return screenTexture;
	}

	public void setScreenTexture(TextureRegion screenTexture) {
		this.screenTexture = screenTexture;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

}
