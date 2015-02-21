package com.mirre.ball.objects.moving;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.CircleState;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.core.SimpleMovingObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;
import com.mirre.ball.utils.BiValue;

public class Guard extends SimpleMovingObject {

	private float directionDelay = 0;
	private float maxVelocity;
	
	public Guard(int x, int y, ObjectColor color) {
		super(x, y, 1, 1, color);
		setDirection(Direction.LEFT);
		int i = new Random().nextInt(2);
		setMaxVelocity(4 +(2 * i));
	}
	
	@Override
	public void onCollideX(PixelObject collideX, boolean yCollided) {
		if(collideX.isCollideable()){
			getAcceleration().x = getDirection().getDir();
		}
	}

	@Override
	public void onCollideY(PixelObject collideY, boolean xCollided) {
		if(collideY.isCollideable()){
			getVelocity().y = 0;
			int x = (int) (collideY.getBounds().x + (getDirection().getDir() * 1.2));
			int y = (int) (collideY.getBounds().y);
			if(!xCollided && !(Level.getCurrentInstance().getPixelObjects().get(new BiValue<Integer,Integer>(x,y)) instanceof Collideable)){
				setDirection(getDirection().getReverse());
				setDirectionDelay(2);
			}
		}
	}
	
	@Override
	public void onCollideXY(PixelObject collideX, PixelObject collideY) {
		if(getDirectionDelay() <= 0){
			setDirection(getDirection().getReverse());
			setDirectionDelay(2);
		}
	}
	
	@Override
	public void onNoCollide() {
		
	}
	
	@Override
	public void update(float deltaTime) {
		Circle b = Level.getCurrentInstance().getCircle();
		
		if(isFacing(b) && inSight(b, new Rectangle(0,0,7,3).setCenter(getBounds().getCenter(new Vector2())), false) && !b.isStealth()){
			b.setState(CircleState.LOSS);
		}
		
		if(getDirectionDelay() != 0F){
			setDirectionDelay(getDirectionDelay() <= 0 ? 0 : getDirectionDelay()-0.05F);
		}
		super.update(deltaTime);
	}

	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/guardRight.png")){
			assets.addSavedRegion("data/guardRight.png", new TextureRegion(assets.getAssetManager().get("data/guardRight.png", Texture.class), 0, 0, 66, 78));
			assets.addSavedRegion("data/guardLeft.png", new TextureRegion(assets.getAssetManager().get("data/guardLeft.png", Texture.class), 0, 0, 66, 78));
		}
		
		if(getDirection() == Direction.RIGHT)
			return assets.getRegion("data/guardRight.png");
		else
			return assets.getRegion("data/guardLeft.png");
	}

	public float getDirectionDelay() {
		return directionDelay;
	}

	public void setDirectionDelay(float directionDelay) {
		this.directionDelay = directionDelay;
	}

	@Override
	public void changeMovement() { 
		getAcceleration().x = (getStandardAcceleration() * getDirection().getDir());
	}

	@Override
	public float getGravity() {
		return 20f;
	}

	@Override
	public float getStandardAcceleration() {
		return 30f;
	}

	@Override
	public float getMaxVelocity() {
		return maxVelocity;
	}

	@Override
	public float getDampening() {
		return 0.90f;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}


}
