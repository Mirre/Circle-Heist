package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.MovingTextureObject;

public class Bounceable extends CollideableTile {

	
	public static TextureRegion texture = null;
	
	public Bounceable(int x, int y) {
		super(x, y, 1F, 1F);
	}


	public static int POWER = 8;

	@Override
	public void onCollideX(MovingTextureObject mto){
		if(mto instanceof Ball){
			if(((Ball)mto).getBounceDelay() == 0F){
				((Ball)mto).setBounceDelay(0.5F);
				mto.getVelocity().x = mto.getMovementManager().getDirection().getReverse().getDir();
				mto.getVelocity().y = 0.07F + (mto.getVelocity().y);
			}
			mto.getAcceleration().x = mto.getMovementManager().getDirection().getReverse().getDir();
		}
	}
	
	@Override
	public void onCollideY(MovingTextureObject mto){
		if(mto instanceof Ball){
			if(((Ball)mto).getBounceDelay() == 0F){
				((Ball)mto).setBounceDelay(0.5F);
				mto.getVelocity().y = 0.2F + (-0.2F * mto.getVelocity().y);
			}
		}
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/diamond.png")), 0, 0, 20, 20);
		return texture;
	}

	
	@Override
	public boolean canCache(){
		return true;
	}
}
