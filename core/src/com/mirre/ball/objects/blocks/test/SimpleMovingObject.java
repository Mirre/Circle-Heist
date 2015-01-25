package com.mirre.ball.objects.blocks.test;

import com.mirre.ball.objects.Level;

public abstract class SimpleMovingObject extends SpriteObject {
	
	
	public SimpleMovingObject(Level level, int x, int y, int width, int height) {
		super(level, x, y, width, height);
		move(x,y);
	}

	public SimpleMovingObject(Level level, int x, int y) {
		super(level, x, y);
	}
	
	public void move(float x, float y){
		getSprite().setPosition(x, y);
	}
	
	public abstract void update(float deltaTime);
	

}
