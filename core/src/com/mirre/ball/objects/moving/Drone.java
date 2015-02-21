package com.mirre.ball.objects.moving;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.CircleState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.core.SimpleMovingObject;
import com.mirre.ball.utils.Assets;

public class Drone extends SimpleMovingObject {

	private boolean chasing = false;
	private boolean caught = false;
	
	private static Animation animation = null;
	private float directionDelay = 0;
	private float stateTime = 0;

	
	public Drone(int x, int y, ObjectColor color) {
		super(x, y, 1F, 1F, color);
		setDirection(null);
		animation = null;
	}

	@Override
	public void update(float deltaTime) {
		
		Circle b = Level.getCurrentInstance().getCircle();
		
		Rectangle r = new Rectangle(getBounds()).setSize(0.2F, 0.2F);
		if(b.getBounds().overlaps(r)){
			b.setState(CircleState.LOSS);
			setCaught(true);
			
		}
		
		if(inSight(b, new Rectangle(0,0,15,5).setCenter(getBounds().getCenter(new Vector2())), false) && !isChasing()){
			setDirection(null);
			setChasing(true);
		}else if(isChasing() && getDirectionDelay() <= 0){ //Is chasing and Direction not changed within a short duration..
			setChasing(false);
		}
		
		
		
		//Gdx.app.log("Chasing", "" + isChasing());
		//Gdx.app.log("Time", "" + getDirectionDelay());
		//(5/0.05)/60 = Time in seconds
		if(getDirectionDelay() > 0){
			setDirectionDelay(getDirectionDelay() - 0.05F);
		}
		
		super.update(deltaTime);
	}
	
	@Override
	public void move(float deltaTime){
		if(hasCaught())
			return;
		getAcceleration().scl(deltaTime);
		getVelocity().add(getAcceleration());
		
		if(getAcceleration().x == 0) 
			getVelocity().x *= getDampening();
		if(getVelocity().x > getMaxVelocity())
			getVelocity().x = getMaxVelocity();
		if(getVelocity().x < -getMaxVelocity()) 
			getVelocity().x = -getMaxVelocity();
		
		if(getAcceleration().y == 0) 
			getVelocity().y *= getDampening();
		if(getVelocity().y > getMaxVelocity())
			getVelocity().y = getMaxVelocity();
		if(getVelocity().y < -getMaxVelocity()) 
			getVelocity().y = -getMaxVelocity();
		
		
		getVelocity().scl(deltaTime);
		attemptMove();
		getVelocity().scl(1.0F / deltaTime);
	}
	
	@Override
	public void changeMovement() {
			
		if(isChasing() && !hasCaught()){
			Circle b = Level.getCurrentInstance().getCircle();
			Rectangle r = new Rectangle(getBounds());
			Vector2 guard = r.getPosition(new Vector2());
			Vector2 ball = b.getBounds().getPosition(new Vector2());
			guard.sub(ball);
			guard.nor();
			guard.y = guard.y * 10;
			guard.x = (guard.x * getStandardAcceleration());
			if(isLeftOf(b.getBounds())){
				if(getDirection() != Direction.LEFT){
					setDirection(Direction.LEFT);
					setDirectionDelay(5);
				}
			}else if(isRightOf(b.getBounds())){
				if(getDirection() != Direction.RIGHT){
					setDirection(Direction.RIGHT);
					setDirectionDelay(5);
				}
			}
			getAcceleration().sub(guard);
		}
		else if(hasCaught()){
			Circle b = Level.getCurrentInstance().getCircle();
			Rectangle r = new Rectangle(b.getBounds());
			r.y = r.y + r.height;
			setBounds(r);
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
		return 8F;
	}

	@Override
	public float getDampening() {
		return 0.90F;
	}

	@Override
	public void onCollideXY(PixelObject collideX, PixelObject collideY) {
		
	}

	@Override
	public void onCollideX(PixelObject collideX, boolean yCollided) {
		if(collideX.isCollideable())
			getAcceleration().x = getDirection().getDir();
		if(isChasing() && !hasCaught() && getDirectionDelay() <= 0){
			setChasing(false);
		}
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

		if(animation == null){
			List<TextureRegion> animationFrames = new ArrayList<TextureRegion>();
			
			Assets assets = Assets.getInstance();
			animationFrames.add(new TextureRegion(assets.getAssetManager().get("data/helicopterDrone0.png", Texture.class), 0, 0, 300, 200));
			animationFrames.add(new TextureRegion(assets.getAssetManager().get("data/helicopterDrone1.png", Texture.class), 0, 0, 300, 200));
			animationFrames.add(new TextureRegion(assets.getAssetManager().get("data/helicopterDrone2.png", Texture.class), 0, 0, 300, 200));
			animation = new Animation(0.05F, animationFrames.toArray(new TextureRegion[animationFrames.size()])); 
			animation.setPlayMode(PlayMode.LOOP);
		}
		
		setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
		
		return animation.getKeyFrame(getStateTime());
	}

	public boolean isChasing() {
		return chasing;
	}

	public void setChasing(boolean chasing) {
		this.chasing = chasing;
	}

	public float getDirectionDelay() {
		return directionDelay;
	}

	public void setDirectionDelay(float directionDelay) {
		this.directionDelay = directionDelay;
	}

	public static Animation getAnimation() {
		return animation;
	}

	public static void setAnimation(Animation animation) {
		Drone.animation = animation;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean hasCaught() {
		return caught;
	}

	public void setCaught(boolean caught) {
		this.caught = caught;
	}

}
