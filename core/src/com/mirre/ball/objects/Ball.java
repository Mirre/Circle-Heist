package com.mirre.ball.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.core.AdvancedMovingObject;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.screens.LevelEndScreen;
import com.mirre.ball.CircleHeist;

public class Ball extends AdvancedMovingObject {

	
	public static TextureRegion texture = null;
	private BallState state;
	private float stateTime = 0;
	private float bounceDelay = 0;
	private float winDelay = 2;
	private int goldCollected = 0;

	
	public Ball(int x, int y){
		super( x, y, 0.8f, 0.8f);
		setState(BallState.SPAWNED);
		setStateTime(0);
		setDirection(Direction.STILL);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		if(getBounceDelay() != 0F){
			setBounceDelay(getBounceDelay() <= 0 ? 0 : getBounceDelay()-0.05F);
		}
		
		//golds/2 + rounded root(level)
		if(getEscapeZone().getBounds().contains(getBounds()) && getGoldCollected() >= Gold.getAmountOfGold()/2)
			setState(BallState.WON);
		
		
		if(getState() == BallState.WON){
			setWinDelay(getWinDelay() - 0.05F);
			if(getWinDelay() <= 0){
				CircleHeist game = ((CircleHeist) Level.getCurrentInstance().getGame());
				game.setCompletedLevels(Level.getCurrentInstance().getLevelID() + 1);
				game.setScreen(new LevelEndScreen(game, true, Level.getCurrentInstance().getLevelID()));
			}
			return;
		}
		if (getState() == BallState.SPAWNED){
			if (stateTime > 0.4f){
				state = BallState.NOTHING;
			}
		}

		if (getState() == BallState.DYING){
			if (stateTime > 0.4f){
				state = BallState.DEAD;
			}
		}

		stateTime = stateTime + deltaTime;
	}
	
	@Override
	public void onObjectCreation(Level level) {
		super.onObjectCreation(level);
		level.setBall(this);
		Gdx.app.log("Create", "Ball");
	}	
	
	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/Lel.png")), 0, 0, 66, 78);
		return texture;
	}

	public int getGoldCollected() {
		return goldCollected;
	}

	public void addGold() {
		goldCollected++;
		
		Gdx.app.log("Gold Collected", "= " + getGoldCollected());
	}


	public PixelObject getEscapeZone() {
		return Level.getCurrentInstance().getStartLocation();
	}

	public float getWinDelay() {
		return winDelay;
	}

	public void setWinDelay(float winDelay) {
		this.winDelay = winDelay;
	}
	
	public void direction(Direction dir, boolean dampen){
		if(Direction.UP == dir){
			getVelocity().y = getJumpVelocity();
			setOnGround(false);
		}else{
			getAcceleration().x = !dampen ? (getStandardAcceleration() * dir.getDir()) : (getStandardAcceleration() * dir.getDir() * 0.1F);
			if(getDirection() != dir && dir != Direction.STILL)
				setDirection(dir);
		}
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


	@Override
	public void changeDirection() {
		if (getState() == BallState.SPAWNED || getState() == BallState.DYING || getState() == BallState.WON)
			return;

		if ((Gdx.input.isKeyPressed(Keys.W)) && isOnGround()) {
			setState(BallState.JUMP);
			direction(Direction.UP);
		}

		if (Gdx.input.isKeyPressed(Keys.A) ) {
			if (getState() != BallState.JUMP) 
				setState(BallState.MOVING);
			direction(Direction.LEFT);
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			if (getState() != BallState.JUMP) 
				setState(BallState.MOVING);
			direction(Direction.RIGHT);
		}else {
			if (getState() != BallState.JUMP){
				setState(BallState.NOTHING);
				direction(Direction.STILL);
			}else 
				direction(Direction.STILL);
		}
		
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
	public float getJumpVelocity() {
		return 10f;
	}

	@Override
	public float getMaxVelocity() {
		return 10f;
	}

	@Override
	public float getDampening() {
		return 0.95f;
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
	
}
