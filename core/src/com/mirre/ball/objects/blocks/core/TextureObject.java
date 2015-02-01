package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;

public abstract class TextureObject extends PixelObject {

	
	public TextureObject(int x, int y, ObjectColor color) {
		super(x, y, color);
	}	
	
	public TextureObject(int x, int y, float width, float height, ObjectColor color) {
		super(x, y, width, height, color);
	}

	public abstract TextureRegion getTexture();
	
	public void draw(Batch batch){
		batch.draw(getTexture().getTexture(), getBounds().getX(), getBounds().getY(), getTextureWidth(), getTextureHeight());
	}

	@Override
	public boolean hasTexture() {
		return true;
	}
	
	public float getTextureHeight(){
		return getBounds().getHeight();
	}
	public float getTextureWidth(){
		return getBounds().getWidth();
	}
	
}
