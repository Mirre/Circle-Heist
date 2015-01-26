package com.mirre.ball.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.managers.MovementManager;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.screens.LevelEndScreen;
import com.mirre.ball.CircleHeist;

public class Ball extends SimpleMovingObject {

	
	public static TextureRegion texture = null;
	private BallState state;
	private float stateTime = 0;
	private float bounceDelay = 0;
	private float winDelay = 2;
	private int goldCollected = 0;
	
	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
	private MovementManager movementManager = new MovementManager(this);
	
	public Ball(int x, int y){
		super( x, y, 0.8f, 0.8f);
		setState(BallState.SPAWNED);
		setStateTime(0);
	}
	
	public MovementManager getMovementManager(){
		return movementManager;
	}
	
	public void setMovementManager(MovementManager mov){
		movementManager = mov;
	}
	
	@Override
	public void update(float deltaTime){
		if(getBounceDelay() != 0F){
			setBounceDelay(getBounceDelay() <= 0 ? 0 : getBounceDelay()-0.05F);
		}
		
		//golds/2 + rounded root(level)
		if(getEscapeZone().getBounds().contains(getBounds()) && getGoldCollected() >= Gold.getAmountOfGold()/2)
			setState(BallState.WON);
			
		processKeys();
		getMovementManager().move(deltaTime);
		
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
	private void processKeys(){
		if (getState() == BallState.SPAWNED || getState() == BallState.DYING || getState() == BallState.WON)
			return;

		if ((Gdx.input.isKeyPressed(Keys.W)) && getMovementManager().isOnGround()) {
			setState(BallState.JUMP);
			getMovementManager().direction(Direction.UP);
		}

		if (Gdx.input.isKeyPressed(Keys.A) ) {
			if (getState() != BallState.JUMP) 
				setState(BallState.MOVING);
			getMovementManager().direction(Direction.LEFT);
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			if (getState() != BallState.JUMP) 
				setState(BallState.MOVING);
			getMovementManager().direction(Direction.RIGHT);
		}else {
			if (getState() != BallState.JUMP){
				setState(BallState.NOTHING);
				getMovementManager().direction(Direction.STILL);
			}else 
				getMovementManager().direction(Direction.STILL);
		}
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
