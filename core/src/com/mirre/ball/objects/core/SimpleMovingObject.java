package com.mirre.ball.objects.core;

import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.interfaces.Collideable;

public abstract class SimpleMovingObject extends MovingObject {

	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
	public SimpleMovingObject(int x, int y, ObjectColor color) {
		super(x, y, color);
	}

	public SimpleMovingObject(int x, int y, float width, float height, ObjectColor color) {
		super(x, y, width, height, color);
	}
	
	public abstract void changeDirection();
	public abstract float getGravity();
	public abstract float getStandardAcceleration();
	public abstract float getMaxVelocity();
	public abstract float getDampening();
	public abstract void onCollideXY(PixelObject collideX, PixelObject collideY);
	public abstract void onCollideX(PixelObject collideX, boolean yCollided);
	public abstract void onCollideY(PixelObject collideY, boolean xCollided);
	public abstract void onNoCollide();
	
	
	@Override
	public void update(float deltaTime) {
		move(deltaTime);
		changeDirection();
	}
	
	public void move(float deltaTime){
		getAcceleration().y = !isOnGround() ? -getGravity() : 0;
		getAcceleration().scl(deltaTime);
		getVelocity().add(getAcceleration());
		
		if(getAcceleration().x == 0) 
			getVelocity().x *= getDampening();
		if(getVelocity().x > getMaxVelocity())
			getVelocity().x = getMaxVelocity();
		if(getVelocity().x < -getMaxVelocity()) 
			getVelocity().x = -getMaxVelocity();
		
		getVelocity().scl(deltaTime);
		attemptMove();
		getVelocity().scl(1.0F / deltaTime);
	}

	public void attemptMove() {
		getBounds().x += getVelocity().x;
		fetchBoundaries();
		PixelObject pixX = getClosest();
		boolean x = pixX != null;
		for(PixelObject pix : getBoundaries()){
			if(pix.getBounds().overlaps(getBounds())){
				Collideable coll = (Collideable) pix;
				if(getVelocity().x < 0 && !coll.passThroughAble())
					getBounds().x = pix.getBounds().x + pix.getBounds().width + 0.01f;
				else if(!coll.passThroughAble())
					getBounds().x = pix.getBounds().x - getBounds().width - 0.01f;
				if(!coll.passThroughAble())
					getVelocity().x = 0;
			}
		}
			
		getBounds().y += getVelocity().y;
		fetchBoundaries();
		PixelObject pixY = getClosest();
		boolean y = pixY != null;
		if(y){
			Collideable coll = (Collideable) pixY;
			if(getVelocity().y < 0 && !coll.passThroughAble()) {
				getBounds().setY(pixY.getBounds().y + pixY.getBounds().height + 0.01f);
				setOnGround(true);
			}else if(!coll.passThroughAble())
				getBounds().setY(pixY.getBounds().y - getBounds().height - 0.01f);
		}
		
		if(x && y){
			onCollideXY(pixX, pixY);
		}else if(!x && !y){
			onNoCollide();
		}
		if(x){
			onCollideX(pixX, y);
		}if(y){
			onCollideY(pixY, x);
		}
			
			
		getPosition().set(getBounds().getX(), getBounds().getY());
	}	
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}
