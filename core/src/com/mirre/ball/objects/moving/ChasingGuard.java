package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
		if(DistanceUtil.inSight(b, this, 15, 5, false)){
			setChasing(true);
		}else if(isChasing() && DistanceUtil.inSight(b, this, 15, 5, true)){ //Is chasing and is in sight with facing checks.
			setChasing(false);
		}
		
		super.update(deltaTime);
	}
	
	@Override
	public void changeDirection() {
		if(isChasing()){
			Ball b = Level.getCurrentInstance().getBall();
			Rectangle r = new Rectangle(getBounds());
			Vector2 guard = r.getPosition(new Vector2());
			Vector2 ball = b.getBounds().getPosition(new Vector2());
			guard.add(ball);
			guard.nor();
			guard.x = guard.x * getStandardAcceleration();
			if(isLeftOf(b.getBounds())){
				getAcceleration().sub(guard);
				setDirection(Direction.LEFT);
			}else if(isRightOf(b.getBounds())){
				getAcceleration().add(guard);
				setDirection(Direction.RIGHT);
			}
		}
	}

	@Override
	public float getGravity() {
		return 0F;
	}

	@Override
	public float getStandardAcceleration() {
		return 10F;
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
