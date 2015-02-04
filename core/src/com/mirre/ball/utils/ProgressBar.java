package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class ProgressBar {

	private Rectangle barBounds;
	private Rectangle knobBounds;
	private float progress = 10;
	
	public ProgressBar(Rectangle r){
		setBarBounds(r);
	}
	
	public void update(float x, float y){
		getBarBounds().setX(x);
		getBarBounds().setY(y);
		getKnobBounds().setX(x - (int) progress);
	}
	
	public void draw(Batch batch){
		TextureRegion bar = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar.png")), 100, 50);
		TextureRegion knob = new TextureRegion(new Texture(Gdx.files.internal("data/knob.png")), 20, 20);
		batch.begin();
		batch.draw(bar.getTexture(), getBarBounds().x, getBarBounds().y, 10, 1);
		batch.draw(knob.getTexture(), getKnobBounds().x, getKnobBounds().y, 1, 1);
		batch.begin();
	}

	public float getProgress() {
		return progress;
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

	public Rectangle getKnobBounds() {
		return knobBounds;
	}

	public void setKnobBounds(Rectangle knobBounds) {
		this.knobBounds = knobBounds;
	}
}
