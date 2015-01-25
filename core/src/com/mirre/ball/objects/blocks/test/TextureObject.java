package com.mirre.ball.objects.blocks.test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Level;

public abstract class TextureObject extends PixelObject {

	
	public TextureObject(Level level, int x, int y) {
		super(level, x, y);
	}	
	
	public TextureObject(Level level, int x, int y, int width, int height) {
		super(level, x, y, width, height);
	}

	public abstract TextureRegion getTexture();
	
}
