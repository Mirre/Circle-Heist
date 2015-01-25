package com.mirre.ball.objects.blocks.interfaces;

import com.mirre.ball.objects.blocks.core.MovingTextureObject;

public interface Collideable {
	
	public void onCollideX(MovingTextureObject mto);
	
	public void onCollideY(MovingTextureObject mto);
	
}
