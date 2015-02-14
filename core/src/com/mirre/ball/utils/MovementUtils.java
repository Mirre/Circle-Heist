package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mirre.ball.enums.Direction;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.SimpleMovingObject;

public class MovementUtils {

	
	public static boolean inSight(SimpleMovingObject target, SimpleMovingObject targeter, int maxXDistance, int maxYDistance, boolean facingCheck){
				
		float xDistance = Math.abs(target.getBounds().getX() - targeter.getBounds().getX());
		float yDistance = Math.abs(target.getBounds().getY() - targeter.getBounds().getY());
		boolean right = (targeter.isRightOf(target.getBounds()) && (facingCheck ? targeter.getDirection() == Direction.RIGHT : true));
		boolean left = (targeter.isLeftOf(target.getBounds()) && (facingCheck ? targeter.getDirection() == Direction.LEFT : true));
		if(xDistance <= maxXDistance && yDistance <= maxYDistance){
			if(right || left){
				Rectangle r = new Rectangle(targeter.getBounds());
				r.setHeight(0.8F);
				r.setWidth(0.8F);
				Vector2 guard = r.getCenter(new Vector2());
				Vector2 ball = target.getBounds().getCenter(new Vector2());
				guard.sub(ball);
				guard.nor();
				if(yDistance <= 0.2)
					guard.y = 0;
				while(!r.overlaps(target.getBounds())){
					Vector2 position = r.getCenter(new Vector2()).sub(guard);
					r.setPosition(position);
					if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int)r.getY()) != null){
						return false;
					}else if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int)(r.getY() + 0.5F)) != null){
						return false;
					}else if(Level.getCurrentInstance().getCollideTile((int)r.getX(), (int)(r.getY() + 0.5F)) != null){
						return false;
					}
				}
				Gdx.app.log("Seen", "Yes");
				return true;
			}
		}
		return false;
	}

	public static boolean inSightV3(SimpleMovingObject target, SimpleMovingObject targeter, Rectangle sightBox, boolean facingCheck){
		
		if(sightBox.overlaps(target.getBounds())){
			
			Vector2 point1 = target.getBounds().getCenter(new Vector2());
			Vector2 point2 = targeter.getBounds().getCenter(new Vector2());
			
			Vector2 vectorPointer = point2.cpy().sub(point1).nor().clamp(-0.4F, 0.4F);
			Rectangle r = new Rectangle(targeter.getBounds());
			
			int i = 0;
			while(r.overlaps(sightBox)){
				r.setPosition(r.getPosition(new Vector2()).sub(vectorPointer));
				Vector2 center = r.getCenter(new Vector2());
				//Gdx.app.log("CenterXY", center.toString());
				//Gdx.app.log("BallXY", target.getBounds().getCenter(new Vector2()).toString());
				if(Level.getCurrentInstance().getCollideTile((int)center.x, (int)center.y) != null && ((target.isRightOf(r) && targeter.isLeftOf(target.getBounds())) || (target.isLeftOf(r) && targeter.isRightOf(target.getBounds())))){
					Gdx.app.log("Null", "Set");
					return false;
				}if(i >= 100){
					Gdx.app.log("Test", "Seen");
					return true;
				}
				i++;
			}
			return true;
		}
		return false;
	}
}
