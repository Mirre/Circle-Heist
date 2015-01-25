package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.objects.Level;

public abstract class SimpleMovingTextureObject extends TextureObject {
	
	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
	private Level level;
	
	public SimpleMovingTextureObject(Level level, int x, int y, float width, float height) {
		super(x, y, width, height);
		setLevel(level);
		getPosition().set(x, y);
	}

	public SimpleMovingTextureObject(int x, int y) {
		super(x, y);
	}
	
	public abstract void update(float deltaTime);

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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}
