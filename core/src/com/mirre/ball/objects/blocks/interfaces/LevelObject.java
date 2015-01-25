package com.mirre.ball.objects.blocks.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.mirre.ball.objects.Level;

public interface LevelObject {

	public Rectangle getBounds();
	public Level getLevel();
	public boolean canCache();
	public boolean hasTexture();
	public boolean isCollideable();
	
	
}
