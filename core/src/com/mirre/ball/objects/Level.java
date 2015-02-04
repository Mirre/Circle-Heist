package com.mirre.ball.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.interfaces.Moveable;
import com.mirre.ball.objects.moving.Ball;
import com.mirre.ball.utils.BiValue;

public class Level {
	
	private static Level instance = null;
	private Ball ball;
	private Truck startLocation = null;
	private int height;
	private int width;
	private HashMap<BiValue<Integer,Integer>, PixelObject> pixelObjects = new HashMap<BiValue<Integer,Integer>, PixelObject>();
	private HashMap<BiValue<Integer,Integer>, PixelObject> collideTiles = new HashMap<BiValue<Integer,Integer>, PixelObject>();
	private List<Moveable> movingObjects = new ArrayList<Moveable>();
	private int levelID;
	private Game game;
	
	public Level(Game game, int i){
		resetLevel();
		setGame(game);
		setLevelID(i);
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/level" + i + ".png"));
		setHeight(pixmap.getHeight());
		setWidth(pixmap.getWidth());
		for (int y = 0; y < pixmap.getHeight(); y++) {
			for (int x = 0; x < pixmap.getWidth(); x++) {
				int pix = pixmap.getPixel(x, y);
				PixelObject pixelObject = PixelObject.colorToPixelObject(pix, x, pixmap.getHeight() - y);
				pixelObject.onLevelCreation(this);
			}
		}
		setInstance(this);
	}
	
	
	public Truck getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(Truck startLocation) {
		this.startLocation = startLocation;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public void update(float deltaTime) {
		for(Moveable m : getMovingObjects()){
			m.update(deltaTime);
		}
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
	
	public PixelObject getPixelObject(int x, int y) {
		return pixelObjects.get(new BiValue<Integer,Integer>(x,y));
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
	
	public List<Moveable> getMovingObjects() {
		return movingObjects;
	}

	public void addMovingObject(Moveable movingObject) {
		movingObjects.add(movingObject);
	}


	public HashMap<BiValue<Integer,Integer>, PixelObject> getCollideTiles() {
		return collideTiles;
	}
	
	public PixelObject getCollideTile(int x, int y) {
		BiValue<Integer,Integer> key = new BiValue<Integer,Integer>(x,y);
		if(collideTiles.containsKey(key))
			return collideTiles.get(key);
		return null;
	}

	public void addCollideTile(PixelObject tile) {
		Rectangle r = tile.getBounds();
		collideTiles.put(new BiValue<Integer, Integer>((int)r.x, (int)r.y), tile);
	}

	//Resets the static level variables.
	public void resetLevel(){
		Gold.clearAmountOfGold();
	}
}
