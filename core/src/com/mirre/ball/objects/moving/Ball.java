package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Stair;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.screens.LevelEndScreen;
import com.mirre.ball.utils.Logger;
import com.mirre.ball.CircleHeist;

public class Ball extends BallData {

	
	public TextureRegion texture = null;
	public static TextureRegion textureLeft = null;
	public static TextureRegion textureRight = null;
	public static TextureRegion textureStealth = null;
	
	

	
	public Ball(int x, int y){
		super(x, y, 1F, 1F);
		setStateTime(0);
		setDirection(Direction.STILL);
		setState(BallState.NOTHING);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		if(getState() == BallState.WON || getState() == BallState.SEEN){
			setEndDelay(getEndDelay() - 0.05F);
			if(getEndDelay() <= 0){
				CircleHeist game = ((CircleHeist) Level.getCurrentInstance().getGame());
				if(getState() != BallState.SEEN)
					game.setCompletedLevels(Level.getCurrentInstance().getLevelID() + 1);
				game.setScreen(new LevelEndScreen(game, getState() == BallState.WON, Level.getCurrentInstance().getLevelID()));
			}
			return;
		}
		
		if(getBounceDelay() != 0F){
			setBounceDelay(getBounceDelay() <= 0 ? 0 : getBounceDelay()-0.04F);
		}
		
		Logger.getInstance().logMessage("State", "" + getState().toString());
		
		
		if(getEscapeZone().getBounds().contains(getBounds()) && getGoldCollected() >= Gold.getAmountOfGold())
			setState(BallState.WON);

		setStateTime(getStateTime() + deltaTime);
	}
	
	@Override
	public void onCollideX(PixelObject collideX, boolean yCollided) {
		Gdx.app.log("X", "Collided");
		if(collideX instanceof Stair && !yCollided){
			setOnStairs(true);
			getVelocity().y = 0;
			getAcceleration().y = 0;
		}else if(isOnStairs() && !yCollided)
			setOnStairs(false);
		if(collideX instanceof Gold && !((Gold)collideX).isCollected()){
			addGold();
			((Gold)collideX).setCollected(true);
		}else if(collideX instanceof Bounceable){
			if(getBounceDelay() <= 0F){
				setBounceDelay(0.5F);
				getVelocity().x = getDirection().getReverse().getDir();
				getVelocity().y = 0.07F + getVelocity().y;
			}
			getAcceleration().x = getDirection().getReverse().getDir();
		}else if(collideX instanceof CollideableTile){
			//getVelocity().x = 0;
			//getAcceleration().x = getDirection().getReverse().getDir();
		}
	}

	@Override
	public void onCollideY(PixelObject collideY, boolean xCollided) {
		if(collideY instanceof Stair && !xCollided){
			setOnStairs(true);
			getVelocity().y = 0;
		}else if(isOnStairs() && !xCollided)
			setOnStairs(false);
		if(collideY instanceof Gold && !((Gold)collideY).isCollected()){
			addGold();
			((Gold)collideY).setCollected(true);
		}else if(collideY instanceof Bounceable){
			if(getBounds().getY() <= collideY.getBounds().getY()){
				getVelocity().y = 0;
			}else if(getBounceDelay() == 0F){
				setBounceDelay(0.5F);
				getVelocity().y = 0.2F + (-0.2F * getVelocity().y);
			}
		}
		else if(collideY instanceof CollideableTile)
			getVelocity().y = 0;
	}
	
	@Override
	public void onCollideXY(PixelObject collideX, PixelObject collideY) {
		Gdx.app.log("XY", "Collided");
		if(collideX instanceof Stair || collideY instanceof Stair){
			setOnStairs(true);
		}else if(isOnStairs())
			setOnStairs(false);
	}
	
	@Override
	public void onNoCollide() {
		if(isOnStairs()){
			setOnStairs(false);
		}
		
	}
	
	
	@Override
	public void onObjectCreation(Level level) {
		super.onObjectCreation(level);
		level.setBall(this);
		Gdx.app.log("Create", "Ball");
	}
	
	@Override
	public void draw(Batch batch){
		batch.draw(getTexture(), getPosition().x, getPosition().y, 1, 1);
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


	public PixelObject getEscapeZone() {
		return Level.getCurrentInstance().getStartLocation();
	}
	
	public void direction(Direction dir, boolean dampen){
		if(Direction.UP == dir){
			getVelocity().y = 10f;
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
	


	@Override
	public void changeDirection() {
		if (getState() == BallState.WON || getState() == BallState.SEEN)
			return;

		if((Gdx.input.isKeyPressed(Keys.Q)) && isOnGround() && getState() == BallState.NOTHING){
			texture = textureStealth; 
			setStealth(true);
		}
		
		
		
		if((Gdx.input.isKeyPressed(Keys.W))){
			texture = textureRight;
			setStealth(false);
			if(isOnStairs()){
				getVelocity().y = 5F;
			}else if(isOnGround()){
				setState(BallState.MOVING);
				direction(Direction.UP);
			}
		}else if((Gdx.input.isKeyPressed(Keys.S)) && isOnStairs()){
			getVelocity().y = -5F;
		}
		if(Gdx.input.isKeyPressed(Keys.A) ){
			texture = textureLeft; 
			setStealth(false);
			if(isOnGround()) 
				setState(BallState.MOVING);
			direction(Direction.LEFT);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			texture = textureRight; 
			setStealth(false);
			if(isOnGround()) 
				setState(BallState.MOVING);
			direction(Direction.RIGHT);
		}else{
			if(isOnGround()){
				setState(BallState.NOTHING);
				direction(Direction.STILL);
			}else 
				direction(Direction.STILL);
		}
		
	}

}
