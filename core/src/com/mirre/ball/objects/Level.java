package com.mirre.ball.objects;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.mirre.ball.enums.BallState;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.utils.BiValue;

public class Level {
	
	private static Level instance = null;
	private Ball ball;
	private BiValue<Integer,Integer> startLocation = null;
	private int height;
	private int width;
	private HashMap<BiValue<Integer,Integer>, PixelObject> pixelObjects = new HashMap<BiValue<Integer,Integer>, PixelObject>();
	private int levelID;
	private Game game;
	
	public Level(Game game, int i){
		setGame(game);
		setLevelID(i);
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/level" + i + ".png"));
		setHeight(pixmap.getHeight());
		setWidth(pixmap.getWidth());
		for (int y = 0; y < pixmap.getHeight(); y++) {
			for (int x = 0; x < pixmap.getWidth(); x++) {
				int pix = pixmap.getPixel(x, y);
				if(Color.rgba8888(Color.RED) == pix && startLocation == null){
					setStartLocation(new BiValue<Integer,Integer>(x, pixmap.getHeight() - y));
					setBall(new Ball(this));
				}
				addPixelObject(PixelObject.colorToPixelObject(pix, x, pixmap.getHeight() - y));
			}
		}
		setInstance(this);
	}
	
	
	public BiValue<Integer,Integer> getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(BiValue<Integer,Integer> startLocation) {
		this.startLocation = startLocation;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public void update(float deltaTime) {
		getBall().update(deltaTime);
		if (getBall().getState() == BallState.DEAD)
			setBall(new Ball(this));
	}

	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public HashMap<BiValue<Integer,Integer>, PixelObject> getPixelObjects() {
		return pixelObjects;
	}


	public void addPixelObject(PixelObject object) {
		pixelObjects.put(new BiValue<Integer,Integer>((int)object.getBounds().getX(),(int)object.getBounds().getY()), object);
	}


	public int getLevelID() {
		return levelID;
	}


	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static Level getCurrentInstance() {
		if(instance == null)
			throw new NullPointerException();
		return instance;
	}

	public static void setInstance(Level instance) {
		Level.instance = instance;
	}

}
