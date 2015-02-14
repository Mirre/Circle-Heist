package com.mirre.ball.objects.moving.actions;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.core.SimpleMovingObject;

public class ChaseAction extends MovementAction {

	private SimpleMovingObject target;
	private float directionDelay = 0;
	
	public ChaseAction(SimpleMovingObject object, SimpleMovingObject target) {
		super(object);
		setTarget(target);
	}

	@Override
	public void calculateMovement() {
		Rectangle r = new Rectangle(getMover().getBounds());
		Vector2 mover = r.getPosition(new Vector2());
		Vector2 target = getTarget().getBounds().getPosition(new Vector2());
		mover.sub(target);
		mover.nor();
		mover.y = mover.y * 10;
		mover.x = (mover.x * getMover().getStandardAcceleration());
		if(getMover().isLeftOf(getTarget().getBounds())){
			if(getMover().getDirection() != Direction.LEFT){
				getMover().setDirection(Direction.LEFT);
				setDirectionDelay(25);
			}
		}else if(getMover().isRightOf(getTarget().getBounds())){
			if(getMover().getDirection() != Direction.RIGHT){
				getMover().setDirection(Direction.RIGHT);
				setDirectionDelay(25);
			}
		}
		getMover().getAcceleration().sub(mover);
	}

	public SimpleMovingObject getTarget() {
		return target;
	}

	public void setTarget(SimpleMovingObject target) {
		this.target = target;
	}

	public float getDirectionDelay() {
		return directionDelay;
	}

	public void setDirectionDelay(float directionDelay) {
		this.directionDelay = directionDelay;
	}


}
