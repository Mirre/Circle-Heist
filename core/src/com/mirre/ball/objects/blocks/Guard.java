package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;

public class Guard extends SimpleMovingObject implements Collideable {

	public static TextureRegion textureLeft = null;
	public static TextureRegion textureRight = null;
	private float directionDelay = 0;
	
	public Guard(int x, int y) {
		super(x, y, 1, 1);
		setDirection(Direction.LEFT);
	}

	public Guard(int x, int y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void update(float deltaTime) {
		if(getDirectionDelay() != 0F){
			setDirectionDelay(getDirectionDelay() <= 0 ? 0 : getDirectionDelay()-0.05F);
		}
		
		fetchBoundaries();
		
		//Make on collide automatic for SimpleMovingObjects!!!!
		PixelObject pix = getClosest();
		if(pix != null){
			((Collideable) pix).onCollideX(this);
		}
		
		getBounds().setX((float) (getBounds().getX() + (0.05 * getDirection().getDir())));
	}

	@Override
	public void onCollideX(SimpleMovingObject mto) {
		if(mto instanceof Ball){
			Gdx.app.log("Loss", "Test");
		}
	}

	@Override
	public void onCollideY(SimpleMovingObject mto) {
		
	}

	@Override
	public TextureRegion getTexture() {
		if(textureLeft == null || textureRight == null){
			textureLeft = new TextureRegion(new Texture(Gdx.files.internal("data/Lel.png")), 0, 0, 66, 78);
			textureRight = new TextureRegion(new Texture(Gdx.files.internal("data/Lel.png")), 0, 0, 66, 78);
		}
		return getDirection() == Direction.LEFT ? textureLeft : textureRight;
	}

	public float getDirectionDelay() {
		return directionDelay;
	}

	public void setDirectionDelay(float directionDelay) {
		this.directionDelay = directionDelay;
	}


}
