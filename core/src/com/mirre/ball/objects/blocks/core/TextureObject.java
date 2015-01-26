package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class TextureObject extends PixelObject {

	
	public TextureObject(int x, int y) {
		super(x, y);
	}	
	
	public TextureObject(int x, int y, float width, float height) {
		super(x, y, width, height);
	}

	public abstract TextureRegion getTexture();

	@Override
	public boolean hasTexture() {
		return true;
	}
	
}
