package com.mirre.ball.objects.blocks.core;

import com.mirre.ball.managers.MovementManager;
import com.mirre.ball.objects.Level;

public abstract class MovingTextureObject extends SimpleMovingTextureObject {
	

	private MovementManager movementManager = new MovementManager(this);
	
	public MovingTextureObject(Level level, int x, int y, float width, float height) {
		super(level, x, y, width, height);
	}

	public MovingTextureObject(int x, int y) {
		super(x, y);
	}
	
	public abstract void update(float deltaTime);

	public MovementManager getMovementManager() {
		return movementManager;
	}

	public void setMovementManager(MovementManager movementManager) {
		this.movementManager = movementManager;
	}

}
