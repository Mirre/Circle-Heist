package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.AdvancedMovingObject;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.BiValue;
import com.mirre.ball.utils.Logger;

public class Guard extends AdvancedMovingObject {

	public static TextureRegion texture = null;
	private float directionDelay = 0;
	
	public Guard(int x, int y) {
		super(x, y, 1, 1, ObjectColor.GUARD);
		setDirection(Direction.LEFT);
	}
	
	@Override
	public void onCollideX(PixelObject collideX, boolean yCollided) {
		if(collideX.isCollideable()){
			getAcceleration().x = getDirection().getDir();
		}
	}

	@Override
	public void onCollideY(PixelObject collideY, boolean xCollided) {
		if(collideY.isCollideable()){
			getVelocity().y = 0;
			int x = (int) collideY.getBounds().x + getDirection().getDir();
			int y = (int) (collideY.getBounds().y);
			if(!xCollided && !(Level.getCurrentInstance().getPixelObjects().get(new BiValue<Integer,Integer>(x,y)) instanceof Collideable)){
				setDirection(getDirection().getReverse());
				setDirectionDelay(2);
			}
		}
	}
	
	@Override
	public void onCollideXY(PixelObject collideX, PixelObject collideY) {
		if(getDirectionDelay() <= 0){
			setDirection(getDirection().getReverse());
			setDirectionDelay(2);
		}
	}
	
	@Override
	public void onNoCollide() {
		
	}
	
	@Override
	public void update(float deltaTime) {
		Ball b = Level.getCurrentInstance().getBall();
		//Gdx.app.log("Guard Bounds", getBounds().toString());
		//Gdx.app.log("Ball Bounds", b.getBounds().toString());
		
		float xDistance = Math.abs(b.getBounds().getX() - getBounds().getX());
		float yDistance = Math.abs(b.getBounds().getY() - getBounds().getY());
		//Gdx.app.log("Distance", "" + distance);
		if(xDistance <= 5 && yDistance <= 3){
			
			Boolean seen = null;
			if(b.getBounds().getX() <= getBounds().getX() && getDirection() == Direction.LEFT){
				seen = true;
				Rectangle r = new Rectangle(getBounds());
				Vector2 guard = r.getPosition(new Vector2());
				Vector2 ball = b.getBounds().getPosition(new Vector2());
				guard.add(ball);
				guard.nor();
				guard.x = -guard.x;
				while(!r.overlaps(b.getBounds())){
					Vector2 position = r.getPosition(new Vector2()).add(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int) r.getY()) != null){
						seen = false;
						break;
					}
				}
			}else if(b.getBounds().getX() > getBounds().getX() && getDirection() == Direction.RIGHT){
				seen = true;
				Rectangle r = new Rectangle(getBounds());
				Vector2 guard = r.getPosition(new Vector2());
				Vector2 ball = b.getBounds().getPosition(new Vector2());
				guard.add(ball);
				guard.nor();
				while(!r.overlaps(b.getBounds())){
					Vector2 position = r.getPosition(new Vector2()).add(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int) r.getY()) != null){
						seen = false;
						break;
					}
				}
			}
			if(seen != null && seen){
				if(!b.isStealth()){
					Logger.getInstance().logTag("Ball", "is seen" + getDirection().toString());
					b.setState(BallState.SEEN);
				}
			}else{
				Logger.getInstance().logTag("TBall", "was not seen");
			}
		}
		
		if(getDirectionDelay() != 0F){
			setDirectionDelay(getDirectionDelay() <= 0 ? 0 : getDirectionDelay()-0.05F);
		}
		super.update(deltaTime);
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null){
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/lelRight.png")), 0, 0, 66, 78);
		}
		return texture;
	}

	public float getDirectionDelay() {
		return directionDelay;
	}

	public void setDirectionDelay(float directionDelay) {
		this.directionDelay = directionDelay;
	}

	@Override
	public void changeDirection() {
		getAcceleration().x = (getStandardAcceleration() * getDirection().getDir());
	}

	@Override
	public float getGravity() {
		return 20f;
	}

	@Override
	public float getStandardAcceleration() {
		return 30f;
	}

	@Override
	public float getMaxVelocity() {
		return 5f;
	}

	@Override
	public float getDampening() {
		return 0.95f;
	}


}
