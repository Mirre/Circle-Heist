package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.blocks.interfaces.Collideable;


public class PixelObject {

	
	private Rectangle bounds;
	
	public PixelObject(Rectangle bounds){
		setBounds(bounds);
	}
	
	public PixelObject(int x, int y){
		setBounds(new Rectangle(x,y,1F,1F));
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
	
	
	public boolean isCollideable(){
		return this instanceof Collideable;
	}
	
	public void onCollideX(MovingTextureObject mto){

	}
	
	public void onCollideY(MovingTextureObject mto){

	}
	
	public boolean hasTexture(){
		return false;
	}
	
	public boolean canCache(){
		return false;
	}
	
	public static PixelObject colorToPixelObject(int pixColor, int x, int y){
		for(ObjectColor ob : ObjectColor.values()){
			if(Color.rgba8888(ob.getColor()) == pixColor)
				return ob.getObject(x, y);
		}
		return new PixelObject(x,y);
			
	}
	
}
