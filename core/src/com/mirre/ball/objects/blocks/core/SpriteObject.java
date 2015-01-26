package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SpriteObject extends TextureObject {
	
	private Sprite sprite;
	
	public SpriteObject(int x, int y) {
		super(x, y);
	}

	public SpriteObject(int x, int y, float width, float height) {
		super(x, y, width, height);
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

	@Override
	public boolean canCache() {
		return false;
	}
}
