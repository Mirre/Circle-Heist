package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;

public class CollideableTile extends TextureObject implements Collideable {

	
	public static TextureRegion texture = null;
	
	public CollideableTile(int x, int y, ObjectColor color) {
		super(x, y, color);
	}
	
	@Override
	public void onObjectCreation(Level level){
		level.addCollideTile(this);
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/tile.png")), 0, 0, 50, 50);
		return texture;
	}

	@Override
	public boolean passThroughAble() {
		return false;
	}

}
