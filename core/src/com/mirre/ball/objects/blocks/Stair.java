package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;

public class Stair extends TextureObject implements Collideable {

	public static TextureRegion texture = null;
	
	public Stair(int x, int y) {
		super(x, y, 1, 1, ObjectColor.STAIR);
	}

	@Override
	public boolean passThroughAble() {
		return true;
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/stairs.png")), 0, 0, 100, 100);
		return texture;
	}

}
