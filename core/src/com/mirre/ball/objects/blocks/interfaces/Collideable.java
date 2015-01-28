package com.mirre.ball.objects.blocks.interfaces;

import com.mirre.ball.objects.blocks.core.SimpleMovingObject;

public interface Collideable {
	
	public void onCollideX(SimpleMovingObject mto);
	
	public void onCollideY(SimpleMovingObject mto);
	
	public boolean passThroughAble();
}
