package com.mirre.ball.enums;

import com.badlogic.gdx.graphics.Color;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Guard;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.blocks.core.PixelObject;

public enum ObjectColor {

	BOUNCEABLE(Color.YELLOW) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Bounceable(x,y);
		}
	},
	COLLIDEABLE(Color.WHITE) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new CollideableTile(x,y);
		}
	},
	GOLD(Color.BLUE) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Gold(x,y);
		}
	},
	TRUCK(Color.GREEN) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Truck(x,y);
		}
	},
	BALL(Color.RED) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Ball(x,y);
		}
	},
	GUARD(Color.CYAN) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Guard(x,y);
		}
	};
	
	private Color color;
	
	ObjectColor(Color c){
		setColor(c);
	}
	
	public abstract PixelObject getObject(int x, int y);

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
