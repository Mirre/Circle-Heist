package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.SimpleMovingObject;
import com.mirre.ball.objects.moving.ChasingGuard;

public class DistanceUtil {

	
	public static boolean inSight(SimpleMovingObject target, SimpleMovingObject targeter, int maxXDistance, int maxYDistance, boolean facingCheck){
				
		float xDistance = Math.abs(target.getBounds().getX() - targeter.getBounds().getX());
		float yDistance = Math.abs(target.getBounds().getY() - targeter.getBounds().getY());
		boolean right = (targeter.isRightOf(target.getBounds()) && facingCheck ? targeter.getDirection() == Direction.RIGHT : true);
		boolean left = (targeter.isLeftOf(target.getBounds()) && facingCheck ? targeter.getDirection() == Direction.LEFT : true);
		
		if(xDistance <= maxXDistance && yDistance <= maxYDistance){
			if(right || left){
				if(targeter instanceof ChasingGuard)
					Gdx.app.log("Test", "xDistance: " + xDistance + " yDistance: " + yDistance);
				Rectangle r = new Rectangle(targeter.getBounds());
				r.setHeight(0.8F);
				r.setWidth(0.8F);
				Vector2 guard = r.getPosition(new Vector2());
				Vector2 ball = target.getBounds().getPosition(new Vector2());
				guard.add(ball);
				guard.nor();
				if(yDistance <= 0.2)
					guard.y = 0;
				if(left)
					guard.x = -guard.x;
				while(!r.overlaps(target.getBounds())){
					Vector2 position = r.getPosition(new Vector2()).add(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int) (r.getY())) != null){
						Gdx.app.log("Location", r.toString() + " " + target.getBounds().toString());
						return false;
					}
				}
				Gdx.app.log("Seen", "Yes");
				return true;
			}
		}
		return false;
	}

}
