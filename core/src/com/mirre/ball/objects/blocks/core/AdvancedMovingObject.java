package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.objects.blocks.interfaces.Collideable;

public abstract class AdvancedMovingObject extends SimpleMovingObject {

	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
	public AdvancedMovingObject(int x, int y) {
		super(x, y);
	}

	public AdvancedMovingObject(int x, int y, float width, float height) {
		super(x, y, width, height);
	}
	
	public abstract void changeDirection();
	public abstract float getGravity();
	public abstract float getStandardAcceleration();
	public abstract float getJumpVelocity();
	public abstract float getMaxVelocity();
	public abstract float getDampening();
	
	
	@Override
	public void update(float deltaTime) {
		move(deltaTime);
		changeDirection();
	}
	
	public void move(float deltaTime){
		getAcceleration().y = -getGravity();
		getAcceleration().scl(deltaTime);
		getVelocity().add(getAcceleration());
		
		if (getAcceleration().x == 0) 
			getVelocity().x *= getDampening();
		if (getVelocity().x > getMaxVelocity())
			getVelocity().x = getMaxVelocity();
		if (getVelocity().x < -getMaxVelocity()) 
			getVelocity().x = -getMaxVelocity();
		
		getVelocity().scl(deltaTime);
		attemptMove();
		getVelocity().scl(1.0F / deltaTime);
	}

	public void attemptMove() {

		getBounds().x += getVelocity().x;
		fetchBoundaries();
		PixelObject pix = getClosest();
		if(pix != null){
			Collideable coll = (Collideable) pix;
			if(getVelocity().x < 0 && !coll.passThroughAble())
				getBounds().x = pix.getBounds().x + pix.getBounds().width + 0.01f;
			else if(!coll.passThroughAble())
				getBounds().x = pix.getBounds().x - getBounds().width - 0.01f;
			coll.onCollideX(this);
		}
			
		getBounds().y += getVelocity().y;
		fetchBoundaries();
		pix = getClosest();
		if(pix != null){ //There is 100% chance that it is a collideable.
			Collideable coll = (Collideable) pix;
			if(getVelocity().y < 0 && !coll.passThroughAble()) {
				getBounds().setY(pix.getBounds().y + pix.getBounds().height + 0.01f);
				setOnGround(true);
			}else if(!coll.passThroughAble())
				getBounds().setY(pix.getBounds().y - getBounds().height - 0.01f);
			coll.onCollideY(this);
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
