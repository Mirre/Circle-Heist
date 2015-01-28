package com.mirre.ball.objects.blocks.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.interfaces.Moveable;
import com.mirre.ball.utils.BiValue;

public abstract class SimpleMovingObject extends SpriteObject implements Moveable {
	
	
	private Direction direction;
	private List<PixelObject> boundaries = new ArrayList<PixelObject>();
	private boolean onGround = true;
	
	public SimpleMovingObject(int x, int y, float width, float height) {
		super(x, y, width, height);
	}

	public SimpleMovingObject(int x, int y) {
		super(x, y);
	}

	@Override
	public void onObjectCreation(Level level) {
		level.addMovingObject(this);
		Gdx.app.log("Create", "Moving");
	}
	
	public PixelObject getClosest(){
		PixelObject closest = null;
		for(PixelObject p : getBoundaries()){
			if(p.getBounds().overlaps(getBounds())){
				if(closest == null)
					closest = p;
				else{
					float distance1 = Math.abs(getBounds().getX() - p.getBounds().getX());
					float distance2 = Math.abs(getBounds().getX() - closest.getBounds().getX());
					if(distance1 < distance2){ //Is less than distance2
						closest = p;
					}
				}
			}
		}
		
		return closest;
	}	
	
	
	public void fetchBoundaries() {
		
		int bottomLeftX = (int)getBounds().getX(); //Left side of Ball, Checks below Ball also
		int bottomLeftY = (int)Math.floor(getBounds().getY()); //Left side of Ball, Checks below Ball also
		int bottomRightX = (int)(getBounds().getX() + getBounds().getWidth()); //Right side of Ball, Checks below Ball also
		int bottomRightY = (int)Math.floor(getBounds().getY()); //Right side of Ball Checks below Ball also
		int topRightX = (int)(getBounds().getX() + getBounds().getWidth()); //Right side of Ball, Checks above Ball also
		int topRightY = (int)(getBounds().getY() + getBounds().getHeight()); //Right side of Ball, Checks above Ball also
		int topLeftX = (int)getBounds().getX(); //Left side of Ball, Checks above Ball also
		int topLeftY = (int)(getBounds().getY() + getBounds().getHeight()); //Left side of Ball, Checks above Ball also
		
		HashMap<BiValue<Integer,Integer>,PixelObject> tiles = Level.getCurrentInstance().getPixelObjects();
		
		PixelObject bottomLeft = tiles.get(new BiValue<Integer,Integer>(bottomLeftX, bottomLeftY));
		PixelObject bottomRight = tiles.get(new BiValue<Integer,Integer>(bottomRightX, bottomRightY));
		PixelObject topRight = tiles.get(new BiValue<Integer,Integer>(topRightX, topRightY));
		PixelObject topLeft = tiles.get(new BiValue<Integer,Integer>(topLeftX, topLeftY));
		
		PixelObject[] tileArray = new PixelObject[]{ bottomRight, bottomLeft, topRight, topLeft };
		
		
		//On Collide Add
		clearBoundaries();
		for(PixelObject p : tileArray){
			if(p != null)
				if(p.isCollideable() && p.hasTexture())
					addBoundary(p);
		}
			
		//OnGround Listener.
		if(bottomLeft != null && bottomRight != null)
			setOnGround(bottomLeft.isCollideable() && bottomRight.isCollideable());
		else
			setOnGround(false);

	}

	public void clearBoundaries() {
		boundaries.clear();
	}	
	
	public List<PixelObject> getBoundaries() {
		return boundaries;
	}

	public void addBoundary(PixelObject pixel) {
		boundaries.add(pixel);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	
}
