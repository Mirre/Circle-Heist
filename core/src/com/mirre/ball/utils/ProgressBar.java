package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProgressBar extends Actor {

	private TextureRegion[] barTextures;
	private Rectangle barBounds;
	private float progress = 5;
	
	public ProgressBar(Rectangle r){
		setBarBounds(r);
		TextureRegion bar = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar.png")), 100, 50);
		addBarTextures(bar, bar, bar, bar, bar);
	}
	
	
	public void update(float x, float y){
		getBarBounds().setX(x);
		getBarBounds().setY(y);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(getBarTexture(getProgress() - 1), getBarBounds().x, getBarBounds().y, 5, 1);
		super.draw(batch, parentAlpha);
	}

	public int getProgress() {
		return (int) progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public Rectangle getBarBounds() {
		return barBounds;
	}

	public void setBarBounds(Rectangle barBounds) {
		this.barBounds = barBounds;
	}

	public TextureRegion getBarTexture(int i) {
		return barTextures[i];
	}

	public void addBarTextures(TextureRegion... barTextures) {
		this.barTextures = barTextures;
	}

}
