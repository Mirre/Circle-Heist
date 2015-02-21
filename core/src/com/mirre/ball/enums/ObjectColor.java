package com.mirre.ball.enums;

import com.badlogic.gdx.graphics.Color;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Lava;
import com.mirre.ball.objects.blocks.Stair;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.moving.Circle;
import com.mirre.ball.objects.moving.Drone;
import com.mirre.ball.objects.moving.Guard;

public enum ObjectColor {
	PIXEL(Color.BLACK) {
		@Override
		public PixelObject getObject(int x, int y) {
			return new PixelObject(x,y, this);
		}
	},
	BOUNCEABLE(Color.YELLOW, "data/bounceBlock.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Bounceable(x,y, this);
		}
	},
	COLLIDEABLE(Color.WHITE, "data/tile.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new CollideableTile(x,y, this);
		}
	},
	GOLD(Color.BLUE, "data/gold1.png", "data/gold2.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Gold(x,y, this);
		}
	},
	TRUCK(Color.GREEN, "data/truck.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Truck(x,y, this);
		}
	},
	CIRCLE(Color.RED, "data/BallStealthRight.png", "data/BallStealthLeft.png", "data/BallLeft.png", "data/BallRight.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Circle(x,y, this);
		}
	},
	GUARD(Color.CYAN, "data/guardLeft.png", "data/guardRight.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Guard(x,y, this);
		}
	},
	STAIR(Color.MAGENTA, "data/stairs.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Stair(x,y, this);
		}
	},

	DRONE(Color.PINK, "data/helicopterDrone0.png", "data/helicopterDrone1.png", "data/helicopterDrone2.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Drone(x,y, this);
		}
	},
	LAVA(Color.PURPLE, "data/Lava.png") {
		@Override
		public PixelObject getObject(int x, int y) {
			return new Lava(x,y, this);
		}
	};
	
	private Color color;
	private boolean hasTexture;
	private String[] textureLocations;
	
	ObjectColor(Color c){
		setColor(c);
		setHasTexture(false);
	}
	
	ObjectColor(Color c, String... textureLocation){
		setColor(c);
		setHasTexture(true);
		setTextureLocations(textureLocation);
	}
	
	public abstract PixelObject getObject(int x, int y);

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean hasTexture() {
		return hasTexture;
	}

	public void setHasTexture(boolean hasTexture) {
		this.hasTexture = hasTexture;
	}

	public String[] getTextureLocations() {
		return textureLocations;
	}

	public void setTextureLocations(String... textureLocations) {
		this.textureLocations = textureLocations;
	}
}
