package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.blocks.core.MovingTextureObject;
import com.mirre.ball.objects.blocks.core.TextureObject;

public class CollideableTile extends TextureObject {

	
	public static TextureRegion texture = null;
	
	public CollideableTile(int x, int y) {
		super(x, y);
	}
	
	public CollideableTile(int x, int y, float width, float height){
		super(x, y, width, height);
	}
	
	
	@Override
	public void onCollideX(MovingTextureObject mto){
		mto.getVelocity().x = 0;
		mto.getAcceleration().x = mto.getMovementManager().getDirection().getReverse().getDir();
	}
	
	@Override
	public void onCollideY(MovingTextureObject mto){
		mto.getVelocity().y = 0;
	}
	
	
	@Override
	public boolean isCollideable(){
		return true;
	}
	

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/tile.png")), 0, 0, 20, 20);
		return texture;
	}
	
	@Override
	public boolean canCache(){
		return true;
	}
}
