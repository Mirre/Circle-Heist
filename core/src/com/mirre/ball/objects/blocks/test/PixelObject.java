package com.mirre.ball.objects.blocks.test;

import com.badlogic.gdx.math.Rectangle;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.interfaces.Collideable;
import com.mirre.ball.objects.blocks.interfaces.LevelObject;

public class PixelObject implements LevelObject {

	private Rectangle bounds;
	private Level level;
	
	public PixelObject(Level level, int x, int y){
		setBounds(new Rectangle(x,y,1,1));
		setLevel(level);
	}
	
	public PixelObject(Level level, int x, int y, int width, int height){
		setBounds(new Rectangle(x,y,width,height));
		setLevel(level);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public boolean canCache() {
		return true;
	}

	@Override
	public boolean hasTexture() {
		return false;
	}

	public boolean isCollideable() {
		return this instanceof Collideable;
	}

}
