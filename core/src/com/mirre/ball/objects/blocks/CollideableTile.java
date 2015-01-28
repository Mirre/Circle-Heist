package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.objects.blocks.core.TextureObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;

public class CollideableTile extends TextureObject implements Collideable {

	
	public static TextureRegion texture = null;
	
	public CollideableTile(int x, int y) {
		super(x, y);
	}
	
	public CollideableTile(int x, int y, float width, float height){
		super(x, y, width, height);
	}
	
	
	@Override
	public void onCollideX(SimpleMovingObject mto){
		if(mto instanceof Ball){
			Ball b = ((Ball)mto);
			b.getVelocity().x = 0;
			b.getAcceleration().x = b.getDirection().getReverse().getDir();
		}if(mto instanceof Guard){
			Gdx.app.log("Guard", "XD");
			Guard g = ((Guard)mto);
			if(g.getDirectionDelay() <= 0){
				g.setDirection(g.getDirection().getReverse());
				g.setDirectionDelay(2);
			}
		}
	}
	
	@Override
	public void onCollideY(SimpleMovingObject mto){
		if(mto instanceof Ball)
			((Ball)mto).getVelocity().y = 0;
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
