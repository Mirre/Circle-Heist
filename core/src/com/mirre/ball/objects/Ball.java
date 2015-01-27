package com.mirre.ball.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;
import com.mirre.ball.screens.LevelEndScreen;
import com.mirre.ball.CircleHeist;

public class Ball extends SimpleMovingObject {

	
	public static TextureRegion texture = null;
	private BallState state;
	private float stateTime = 0;
	private float bounceDelay = 0;
	private float winDelay = 2;
	private int goldCollected = 0;
	private boolean onGround = true;
	
	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
	
	public static float ACCELERATION = 20f;
	public static float JUMP_VELOCITY = 10;
	public static float GRAVITY = 20.0f;
	public static float MAX_VELOCITY = 10F;
	public static float DAMP = 0.95f;
	
	public Ball(int x, int y){
		super( x, y, 0.8f, 0.8f);
		setState(BallState.SPAWNED);
		setStateTime(0);
	}
	
	@Override
	public void update(float deltaTime){
		if(getBounceDelay() != 0F){
			setBounceDelay(getBounceDelay() <= 0 ? 0 : getBounceDelay()-0.05F);
		}
		
		//golds/2 + rounded root(level)
		if(getEscapeZone().getBounds().contains(getBounds()) && getGoldCollected() >= Gold.getAmountOfGold()/2)
			setState(BallState.WON);
			
		processKeysAndMove(deltaTime);
		
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
	private void processKeysAndMove(float deltaTime){
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
		
		getAcceleration().y = -GRAVITY;
		getAcceleration().scl(deltaTime);
		getVelocity().add(getAcceleration());
		
		if (getAcceleration().x == 0) 
			getVelocity().x *= DAMP;
		if (getVelocity().x > MAX_VELOCITY)
			getVelocity().x = MAX_VELOCITY;
		if (getVelocity().x < -MAX_VELOCITY) 
			getVelocity().x = -MAX_VELOCITY;
		
		getVelocity().scl(deltaTime);
		attemptMove();
		getVelocity().scl(1.0F / deltaTime);
	}

	public void attemptMove() {

		getBounds().x += getVelocity().x;
		fetchBoundaries();
		PixelObject pix = getClosest();
		if(pix != null){
			if(getVelocity().x < 0)
				getBounds().x = pix.getBounds().x + pix.getBounds().width + 0.01f;
			else 
				getBounds().x = pix.getBounds().x - getBounds().width - 0.01f;
			if(pix instanceof Collideable)
				((Collideable)pix).onCollideX(this);
		}
			
		getBounds().y += getVelocity().y;
		fetchBoundaries();
		pix = getClosest();
		if(pix != null){
			if(getVelocity().y < 0) {
				getBounds().setY(pix.getBounds().y + pix.getBounds().height + 0.01f);
				setOnGround(true);
			}else
				getBounds().setY(pix.getBounds().y - getBounds().height - 0.01f);
			if(pix instanceof Collideable)
				((Collideable)pix).onCollideY(this);
		}
		getPosition().set(getBounds().getX(), getBounds().getY());
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
	
	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public void direction(Direction dir, boolean dampen){
		if(Direction.UP == dir){
			getVelocity().y = JUMP_VELOCITY;
			setOnGround(false);
		}else{
			getAcceleration().x = !dampen ? (ACCELERATION * dir.getDir()) : (ACCELERATION * dir.getDir() * 0.1F);
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
