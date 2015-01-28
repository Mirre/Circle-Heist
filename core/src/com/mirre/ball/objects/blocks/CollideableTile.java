package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.AdvancedMovingObject;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.objects.blocks.core.TextureObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;
import com.mirre.ball.utils.RandomGenerator;

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
		Gdx.app.log("Guard", "Test " + mto.getClass().getSimpleName());
		if(mto instanceof Ball){
			Ball b = ((Ball)mto);
			b.getVelocity().x = 0;
			b.getAcceleration().x = b.getDirection().getReverse().getDir();
		}if(mto instanceof Guard){
			Gdx.app.log("Guard", "XD");
			Guard g = ((Guard)mto);
			g.getAcceleration().x = g.getDirection().getDir();
			if(g.getDirectionDelay() <= 0){
				if(RandomGenerator.getRandom().nextInt(3) == 2)
					g.getVelocity().y = 0.15f;
				else{
					g.setDirection(g.getDirection().getReverse());
					g.setDirectionDelay(5);
				}
			}
		}
	}
	
	@Override
	public void onCollideY(SimpleMovingObject mto){
		if(mto instanceof AdvancedMovingObject){
			((AdvancedMovingObject)mto).getVelocity().y = 0;
			
		}
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

	@Override
	public boolean passThroughAble() {
		return false;
	}
}
