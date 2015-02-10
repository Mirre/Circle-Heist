package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.SimpleMovingObject;

public class DistanceUtil {

	
	public static boolean inSight(SimpleMovingObject target, SimpleMovingObject targeter, int maxXDistance, int maxYDistance){
			
		
		float xDistance = Math.abs(target.getBounds().getX() - targeter.getBounds().getX());
		float yDistance = Math.abs(target.getBounds().getY() - targeter.getBounds().getY());
		boolean right = isRightOf(target, targeter);
		boolean left = isLeftOf(target, targeter);
		
		if(xDistance <= maxXDistance && yDistance <= maxYDistance){
			if(right || left){
				Rectangle r = new Rectangle(targeter.getBounds());
				Vector2 guard = r.getPosition(new Vector2());
				Vector2 ball = target.getBounds().getPosition(new Vector2());
				guard.add(ball);
				guard.nor();
				if(left)
					guard.x = -guard.x;
				while(!r.overlaps(target.getBounds())){
					Vector2 position = r.getPosition(new Vector2()).add(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int) r.getY()) != null)
						return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean isLeftOf(SimpleMovingObject target, SimpleMovingObject targeter){
		return (targeter.getDirection() == Direction.LEFT && target.getBounds().getX() <= targeter.getBounds().getX());

	}
	
	public static boolean isRightOf(SimpleMovingObject target, SimpleMovingObject targeter){
		return (targeter.getDirection() == Direction.RIGHT && target.getBounds().getX() > targeter.getBounds().getX());

	}
	
	public static void rgbaToRGB(Color color){
		
		int r = (int) (((((1 - color.a) * color.r) + (color.a * color.r)) * 255));
		int g = (int) (((((1 - color.a) * color.g) + (color.a * color.g)) * 255));
		int b = (int) (((((1 - color.a) * color.b) + (color.a * color.b)) * 255));
		Gdx.app.log("Color", color.toString() + " is " + r + " " + g + " " + b);
	}

}
