package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.core.SimpleMovingObject;
import com.mirre.ball.utils.DistanceUtil;

public class ChasingGuard extends SimpleMovingObject {

	private boolean chasing = false;
	private TextureRegion texture = null;
	
	public ChasingGuard(int x, int y, ObjectColor color) {
		super(x, y, 1, 1, color);
		setDirection(Direction.LEFT);
	}

	@Override
	public void update(float deltaTime) {
		
		Ball b = Level.getCurrentInstance().getBall();
		if(DistanceUtil.inSight(b, this, 10, 4)){
			setChasing(true);
		} //else{
			//setChasing(false);
		//}
		
		super.update(deltaTime);
	}
	
	@Override
	public void changeDirection() {
		if(isChasing()){
			Ball b = Level.getCurrentInstance().getBall();
			if(DistanceUtil.isLeftOf(b, this)){
				setDirection(Direction.LEFT);
			}else if(DistanceUtil.isRightOf(b, this)){
				setDirection(Direction.RIGHT);
			}
			getAcceleration().x = (getStandardAcceleration() * getDirection().getDir());
		}
	}

	@Override
	public float getGravity() {
		return 0F;
	}

	@Override
	public float getStandardAcceleration() {
		return 30F;
	}

	@Override
	public float getMaxVelocity() {
		return 10F;
	}

	@Override
	public float getDampening() {
		return 0.95F;
	}

	@Override
	public void onCollideXY(PixelObject collideX, PixelObject collideY) {
		if(collideX.isCollideable())
			getAcceleration().x = getDirection().getDir();
	}

	@Override
	public void onCollideX(PixelObject collideX, boolean yCollided) {
		
	}

	@Override
	public void onCollideY(PixelObject collideY, boolean xCollided) {
		if(collideY.isCollideable())
			getVelocity().y = 0;
	}

	@Override
	public void onNoCollide() {
		
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/guardRight.png")), 0, 0, 66, 78);
		return texture;
	}

	public boolean isChasing() {
		return chasing;
	}

	public void setChasing(boolean chasing) {
		this.chasing = chasing;
	}

}
