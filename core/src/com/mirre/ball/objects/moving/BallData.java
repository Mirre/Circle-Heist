package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.blocks.core.AdvancedMovingObject;

public abstract class BallData extends AdvancedMovingObject {

	private BallState state;
	private float stateTime = 0;
	private float bounceDelay = 0;
	private float endDelay = 3;
	private int goldCollected = 0;
	private boolean stealth = false;
	private boolean onStairs = false;
	
	public BallData(int x, int y, float width, float height) {
		super(x, y, width, height, ObjectColor.BALL);
	}

	@Override
	public float getGravity() {
		return 20f;
	}

	@Override
	public float getStandardAcceleration() {
		return 20f;
	}


	@Override
	public float getMaxVelocity() {
		return 10f;
	}

	@Override
	public float getDampening() {
		return 0.95f;
	}	
	
	public int getGoldCollected() {
		return goldCollected;
	}

	public void addGold() {
		goldCollected++;
		Gdx.app.log("Gold Collected", "= " + getGoldCollected());
	}

	public BallState getState() {
		return state;
	}

	public void setState(BallState state) {
		this.state = state;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getBounceDelay() {
		return bounceDelay;
	}

	public void setBounceDelay(float bounceDelay) {
		this.bounceDelay = bounceDelay;
	}

	public float getEndDelay() {
		return endDelay;
	}

	public void setEndDelay(float endDelay) {
		this.endDelay = endDelay;
	}

	public boolean isStealth() {
		return stealth;
	}

	public void setStealth(boolean stealth) {
		this.stealth = stealth;
	}

	public boolean isOnStairs() {
		return onStairs;
	}

	public void setOnStairs(boolean onStairs) {
		this.onStairs = onStairs;
	}
}
