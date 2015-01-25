package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class TextureObject extends PixelObject {

	public TextureObject(int x, int y) {
		super(x, y);
	}
	
	public TextureObject(int x, int y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public boolean hasTexture(){
		return true;
	}
	
	public abstract TextureRegion getTexture();
	
}
