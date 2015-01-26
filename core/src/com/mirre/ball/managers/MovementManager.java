package com.mirre.ball.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;
import com.mirre.ball.utils.BiValue;

public class MovementManager {

	public static float ACCELERATION = 20f;
	public static float JUMP_VELOCITY = 10;
	public static float GRAVITY = 20.0f;
	public static float MAX_VELOCITY = 10F;
	public static float DAMP = 0.95f;
	
	private Direction direction = Direction.STILL;
	private List<PixelObject> boundaries = new ArrayList<PixelObject>();
	private Ball ball;
	private boolean onGround = true;
	
	public MovementManager(Ball o){
		setBall(o);
	}
	
	public void direction(Direction dir, boolean dampen){
		if(Direction.UP == dir){
			getBall().getVelocity().y = JUMP_VELOCITY;
			setOnGround(false);
		}else{
			getBall().getAcceleration().x = !dampen ? (ACCELERATION * dir.getDir()) : (ACCELERATION * dir.getDir() * 0.1F);
			if(getDirection() != dir && dir != Direction.STILL)
				setDirection(dir);
		}
	}
	
	public Ball getBall(){
		return ball;
	}
	
	public void setBall(Ball b){
		ball = b;
	}
	
	public void direction(Direction dir){
		direction(dir, false);
	}
	
	public void continueDirection(boolean dampen){
		direction(getDirection(), dampen);
	}
	
	public void continueDirection(){
		direction(getDirection());
	}
	
	public void reverseDirection(){
		direction(getDirection().getReverse());
	}
	
	public void move(float deltaTime){
		getBall().getAcceleration().y = -GRAVITY;
		getBall().getAcceleration().scl(deltaTime);
		getBall().getVelocity().add(getBall().getAcceleration());
		
		if (getBall().getAcceleration().x == 0) 
			getBall().getVelocity().x *= DAMP;
		if (getBall().getVelocity().x > MAX_VELOCITY)
			getBall().getVelocity().x = MAX_VELOCITY;
		if (getBall().getVelocity().x < -MAX_VELOCITY) 
			getBall().getVelocity().x = -MAX_VELOCITY;
		
		getBall().getVelocity().scl(deltaTime);
		attemptMove();
		getBall().getVelocity().scl(1.0F / deltaTime);
	}

	
	//Collide Listener
	public void attemptMove() {
		Ball mto = getBall();

		mto.getBounds().x += mto.getVelocity().x;
		fetchBoundaries();
		PixelObject pix = getClosest();
		if(pix != null){
			if(mto.getVelocity().x < 0)
				mto.getBounds().x = pix.getBounds().x + pix.getBounds().width + 0.01f;
			else 
				mto.getBounds().x = pix.getBounds().x - mto.getBounds().width - 0.01f;
			if(pix instanceof Collideable)
				((Collideable)pix).onCollideX(mto);
		}
			
		mto.getBounds().y += mto.getVelocity().y;
		fetchBoundaries();
		pix = getClosest();
		if(pix != null){
			if(mto.getVelocity().y < 0) {
				mto.getBounds().setY(pix.getBounds().y + pix.getBounds().height + 0.01f);
				setOnGround(true);
			}else
				mto.getBounds().setY(pix.getBounds().y - mto.getBounds().height - 0.01f);
			if(pix instanceof Collideable)
				((Collideable)pix).onCollideY(mto);
		}
		mto.getPosition().set(mto.getBounds().getX(), mto.getBounds().getY());
	}
	

	private void fetchBoundaries() {
		
		int bottomLeftX = (int)getBall().getBounds().getX(); //Left side of Ball, Checks below Ball also
		int bottomLeftY = (int)Math.floor(getBall().getBounds().getY()); //Left side of Ball, Checks below Ball also
		int bottomRightX = (int)(getBall().getBounds().getX() + getBall().getBounds().getWidth()); //Right side of Ball, Checks below Ball also
		int bottomRightY = (int)Math.floor(getBall().getBounds().getY()); //Right side of Ball Checks below Ball also
		int topRightX = (int)(getBall().getBounds().getX() + getBall().getBounds().getWidth()); //Right side of Ball, Checks above Ball also
		int topRightY = (int)(getBall().getBounds().getY() + getBall().getBounds().getHeight()); //Right side of Ball, Checks above Ball also
		int topLeftX = (int)getBall().getBounds().getX(); //Left side of Ball, Checks above Ball also
		int topLeftY = (int)(getBall().getBounds().getY() + getBall().getBounds().getHeight()); //Left side of Ball, Checks above Ball also
		
		HashMap<BiValue<Integer,Integer>,PixelObject> tiles = Level.getCurrentInstance().getPixelObjects();
		
		PixelObject bottomLeft = tiles.get(new BiValue<Integer,Integer>(bottomLeftX, bottomLeftY));
		PixelObject bottomRight = tiles.get(new BiValue<Integer,Integer>(bottomRightX, bottomRightY));
		PixelObject topRight = tiles.get(new BiValue<Integer,Integer>(topRightX, topRightY));
		PixelObject topLeft = tiles.get(new BiValue<Integer,Integer>(topLeftX, topLeftY));
		
		PixelObject[] tileArray = new PixelObject[]{ bottomRight, bottomLeft, topRight, topLeft };
		
		
		//On Collide Add
		clearBoundaries();
		for(PixelObject p : tileArray){
			if(p.isCollideable() && p.hasTexture()){
				addBoundary(p);
			}
		}
			
		//OnGround Listener.
		setOnGround(bottomLeft.isCollideable() && bottomRight.isCollideable());

	}	
	
	public PixelObject getClosest(){
		PixelObject closest = null;
		for(PixelObject p : getBoundaries()){
			if(p.getBounds().overlaps(getBall().getBounds())){
				if(closest == null)
					closest = p;
				else{
					float distance1 = Math.abs(getBall().getBounds().getX() - p.getBounds().getX());
					float distance2 = Math.abs(getBall().getBounds().getX() - closest.getBounds().getX());
					if(distance1 < distance2){ //Is less than distance2
						closest = p;
					}
				}
			}
		}
		
		return closest;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public List<PixelObject> getBoundaries() {
		return boundaries;
	}

	public void addBoundary(PixelObject pixel) {
		boundaries.add(pixel);
	}
	
	public void clearBoundaries() {
		boundaries.clear();
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
}
