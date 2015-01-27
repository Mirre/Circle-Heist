package com.mirre.ball.objects.blocks.core;

import com.badlogic.gdx.Gdx;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.interfaces.Moveable;

public abstract class SimpleMovingObject extends SpriteObject implements Moveable {
	
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
}
