package com.mirre.ball.objects.blocks.test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mirre.ball.objects.Level;

public abstract class SpriteObject extends TextureObject {
	
	private Sprite sprite;
	
	public SpriteObject(Level level, int x, int y) {
		super(level, x, y);
	}

	public SpriteObject(Level level, int x, int y, int width, int height) {
		super(level, x, y, width, height);
	}
	
	public Sprite getSprite() {
		if(sprite == null)
			sprite = new Sprite(getTexture().getTexture());
		sprite.setPosition(getBounds().getX(), getBounds().getY());
		return sprite;
	}
	
	public void draw(SpriteBatch b){
		b.draw(getSprite(), getSprite().getX(), getSprite().getY());
	}

}
