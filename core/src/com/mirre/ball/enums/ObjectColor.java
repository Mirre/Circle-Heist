package com.mirre.ball.enums;

import com.badlogic.gdx.graphics.Color;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Stair;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.moving.Ball;
import com.mirre.ball.objects.moving.ChasingGuard;
import com.mirre.ball.objects.moving.Guard;

public enum ObjectColor {
	PIXEL(Color.BLACK) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new PixelObject(x,y, this);
		}
	},
	BOUNCEABLE(Color.YELLOW) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Bounceable(x,y, this);
		}
	},
	COLLIDEABLE(Color.WHITE) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new CollideableTile(x,y, this);
		}
	},
	GOLD(Color.BLUE) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Gold(x,y, this);
		}
	},
	TRUCK(Color.GREEN) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Truck(x,y, this);
		}
	},
	BALL(Color.RED) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Ball(x,y, this);
		}
	},
	GUARD(Color.CYAN) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Guard(x,y, this);
		}
	},
	STAIR(Color.MAGENTA) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Stair(x,y, this);
		}
	},
	CHASINGGUARD(Color.PINK) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new ChasingGuard(x,y, this);
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
