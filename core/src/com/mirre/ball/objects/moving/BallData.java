package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.AdvancedMovingObject;
import com.mirre.ball.objects.core.PixelObject;

public abstract class BallData extends AdvancedMovingObject {

	private BallState state;
	private float stateTime = 0;
	private float bounceDelay = 0;
	private float endDelay = 3;
	private int goldCollected = 0;
	private float stealthMeter = 10;
	private boolean stealth = false;
	private boolean onStairs = false;
	
	public TextureRegion texture = null;
	public static TextureRegion textureLeft = null;
	public static TextureRegion textureRight = null;
	public static TextureRegion textureStealth = null;
	
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
	
	@Override
	public TextureRegion getTexture() {
		if(texture == null){
			textureStealth = new TextureRegion(new Texture(Gdx.files.internal("data/lelStealth.png")), 0, 0, 66, 78);
			textureLeft = new TextureRegion(new Texture(Gdx.files.internal("data/lelLeft.png")), 0, 0, 66, 78);
			textureRight = new TextureRegion(new Texture(Gdx.files.internal("data/lelRight.png")), 0, 0, 66, 78);
			texture = textureRight;
		}
		return texture;
	}
	
	@Override
	public void draw(Batch batch){
		batch.draw(getTexture(), getPosition().x, getPosition().y, 1, 1);
	}
	
	public PixelObject getEscapeZone() {
		return Level.getCurrentInstance().getStartLocation();
	}

	public float getStealthMeter() {
		return stealthMeter;
	}

	public void setStealthMeter(float stealthMeter) {
		this.stealthMeter = stealthMeter >= 10 ? 10 : stealthMeter;
		//CircleHeist game = ((CircleHeist)Gdx.app.getApplicationListener());
		//GameScreen screen = (GameScreen) game.getScreen();
		//screen.getBar().setValue(stealthMeter);
		//Gdx.app.log("Fire", "" + stealthMeter);
	}
}
