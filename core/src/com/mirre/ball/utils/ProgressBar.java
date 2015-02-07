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
	private float progress = 10;
	
	public ProgressBar(Rectangle r){
		setBarBounds(r);
		
		TextureRegion bar1 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar10.png")), 100, 50);
		TextureRegion bar2 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar9.png")), 100, 50);
		TextureRegion bar3 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar8.png")), 100, 50);
		TextureRegion bar4 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar7.png")), 100, 50);
		TextureRegion bar5 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar6.png")), 100, 50);
		TextureRegion bar6 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar5.png")), 100, 50);
		TextureRegion bar7 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar4.png")), 100, 50);
		TextureRegion bar8 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar3.png")), 100, 50);
		TextureRegion bar9 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar2.png")), 100, 50);
		TextureRegion bar10 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar1.png")), 100, 50);
		TextureRegion bar11 = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar0.png")), 100, 50);
	
		addBarTextures(bar11, bar1, bar2, bar3, bar4, bar5, bar6, bar7, bar8, bar9, bar10);
	}
	
	
	public void update(float x, float y){
		getBarBounds().setX(x);
		getBarBounds().setY(y);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(getBarTexture(getProgress()), getBarBounds().x, getBarBounds().y, 5, 1);
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

	public void dispose(){
		for(TextureRegion region : barTextures){
			region.getTexture().dispose();
		}
	}
}
