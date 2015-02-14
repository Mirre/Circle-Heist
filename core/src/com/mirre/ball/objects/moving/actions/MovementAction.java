package com.mirre.ball.objects.moving.actions;

import com.mirre.ball.objects.core.SimpleMovingObject;

public abstract class MovementAction {

	private SimpleMovingObject mover;
	
	public MovementAction(SimpleMovingObject object){
		setMover(object);
	}
	
	public abstract void calculateMovement();
	

	public SimpleMovingObject getMover() {
		return mover;
	}

	public void setMover(SimpleMovingObject mover) {
		this.mover = mover;
	}
}
