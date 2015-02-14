package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;

public class Lava extends TextureObject implements Collideable {

	public static TextureRegion texture = null;
	
	public Lava(int x, int y, ObjectColor color) {
		super(x, y, 1F, 0.7F, color);
	}

	@Override
	public boolean passThroughAble() {
		return true;
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null){
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/Lava.png")), 0, 0, 200, 200);
		}
		return texture;
	}
	
	@Override
	public float getTextureHeight(){
		return 1;
	}
	
}