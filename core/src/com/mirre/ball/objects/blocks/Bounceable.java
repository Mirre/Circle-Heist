package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;

public class Bounceable extends CollideableTile {

	
	public static TextureRegion texture = null;
	
	public Bounceable(int x, int y) {
		super(x, y, 1F, 1F);
	}


	public static int POWER = 8;

	@Override
	public void onCollideX(SimpleMovingObject mto){
		if(mto instanceof Ball){
			Ball b = ((Ball)mto);
			if(b.getBounceDelay() == 0F){
				b.setBounceDelay(0.5F);
				b.getVelocity().x = ((Ball)mto).getMovementManager().getDirection().getReverse().getDir();
				b.getVelocity().y = 0.07F + (((Ball)mto).getVelocity().y);
			}
			b.getAcceleration().x = ((Ball)mto).getMovementManager().getDirection().getReverse().getDir();
		}
	}
	
	@Override
	public void onCollideY(SimpleMovingObject mto){
		if(mto instanceof Ball){
			Ball b = ((Ball)mto);
			if(b.getBounds().getY() <= getBounds().getY()){
				b.getVelocity().y = 0;
				return;
			}if(b.getBounceDelay() == 0F){
				b.setBounceDelay(0.5F);
				b.getVelocity().y = 0.2F + (-0.2F * ((Ball)mto).getVelocity().y);
			}
		}
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/diamond.png")), 0, 0, 20, 20);
		return texture;
	}

}
