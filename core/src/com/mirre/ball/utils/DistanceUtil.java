package com.mirre.ball.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.SimpleMovingObject;

public class DistanceUtil {

	
	public static boolean ballInSight(SimpleMovingObject target, SimpleMovingObject targeter, int maxXDistance, int maxYDistance){
			
		
		float xDistance = Math.abs(target.getBounds().getX() - targeter.getBounds().getX());
		float yDistance = Math.abs(target.getBounds().getY() - targeter.getBounds().getY());
		
		if(xDistance <= maxXDistance && yDistance <= maxYDistance){
			if(targeter.getDirection() == Direction.LEFT && target.getBounds().getX() <= targeter.getBounds().getX()
					|| targeter.getDirection() == Direction.RIGHT && target.getBounds().getX() > targeter.getBounds().getX()){
				Rectangle r = new Rectangle(targeter.getBounds());
				Vector2 guard = r.getPosition(new Vector2());
				Vector2 ball = target.getBounds().getPosition(new Vector2());
				guard.add(ball);
				guard.nor();
				if(target.getBounds().getX() <= targeter.getBounds().getX() && targeter.getDirection() == Direction.LEFT)
					guard.x = -guard.x;
				while(!r.overlaps(target.getBounds())){
					Vector2 position = r.getPosition(new Vector2()).add(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int) r.getY()) != null)
						return false;
				}
			}
		}
		return true;
	}

}
