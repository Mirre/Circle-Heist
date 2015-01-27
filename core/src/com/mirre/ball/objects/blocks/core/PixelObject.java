package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.interfaces.Collideable;
import com.mirre.ball.objects.blocks.interfaces.LevelObject;

public class PixelObject implements LevelObject {

	private Rectangle bounds;
	
	public PixelObject(int x, int y){
		setBounds(new Rectangle(x,y,1,1));
	}
	
	public PixelObject(int x, int y, float width, float height){
		setBounds(new Rectangle(x,y,width,height));
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

	public static PixelObject colorToPixelObject(int pixColor, int x, int y){
		for(ObjectColor ob : ObjectColor.values()){
			if(Color.rgba8888(ob.getColor()) == pixColor)
				return ob.getObject(x, y);
		}
		return new PixelObject(x,y);
			
	}

	public void onLevelCreation(Level level) {
		level.addPixelObject(this);
		onObjectCreation(level);
	}

	public void onObjectCreation(Level level) {
		
	}
}
