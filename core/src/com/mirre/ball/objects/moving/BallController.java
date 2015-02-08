package com.mirre.ball.objects.moving;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.enums.ObjectColor;

public abstract class BallController extends BallData {

	public BallController(int x, int y, float width, float height, ObjectColor color) {
		super(x, y, width, height, color);
	}
	
	public void direction(Direction dir){
		if(Direction.UP == dir){
			getVelocity().y = 10f;
			setOnGround(false);
		}else{
			getAcceleration().x = (getStandardAcceleration() * dir.getDir());
			if(getDirection() != dir)
				setDirection(dir);
		}
	}
	
	@Override
	public void changeDirection() {
		
		if (getState() == BallState.WON || getState() == BallState.SEEN)
			return;

		//Q-Press ; Stealth
		if((Gdx.input.isKeyPressed(Keys.Q)) && isOnGround() && getState() == BallState.NOTHING && getStealthMeter() == 10){
			texture = textureStealth; 
			setStealth(true);
		}else if(isStealth() && !(Gdx.input.isKeyPressed(Keys.Q))){
			texture = textureRight;
			setStealth(false);
		}
		
		//W-Press ; Jump
		if((Gdx.input.isKeyPressed(Keys.W))){
			texture = textureRight;
			setStealth(false);
			if(isOnStairs()){
				getVelocity().y = 5F;
			}else if(isOnGround()){
				setState(BallState.MOVING);
				direction(Direction.UP);
			}
		
		//S-Press ; Climb down Stairs
		}else if((Gdx.input.isKeyPressed(Keys.S)) && isOnStairs()){
			getVelocity().y = -5F;
		}
		
		//A-Press ; Move Left
		if(Gdx.input.isKeyPressed(Keys.A) ){
			texture = textureLeft; 
			setStealth(false);
			if(isOnGround()) 
				setState(BallState.MOVING);
			direction(Direction.LEFT);
		}
		
		//D-Press ; Move Right
		else if(Gdx.input.isKeyPressed(Keys.D)){
			texture = textureRight; 
			setStealth(false);
			if(isOnGround()) 
				setState(BallState.MOVING);
			direction(Direction.RIGHT);
		}
		
		//No A-D key presses. 
		else{
			if(isOnGround()){
				setState(BallState.NOTHING);
				getAcceleration().x = 0; //Activates dampening at Line 44 AdvancedMovingObject.java
			}else 
				getAcceleration().x = 0; //Activates dampening at Line 44 AdvancedMovingObject.java
		}
		
	}
	
}
