package com.mirre.ball.unused;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.blocks.core.AdvancedMovingObject;
import com.mirre.ball.objects.blocks.core.PixelObject;
import com.mirre.ball.objects.blocks.core.TextureObject;
import com.mirre.ball.objects.blocks.interfaces.Moveable;
import com.mirre.ball.utils.BiValue;

public class LevelRenderer {
	private Level level;
	private OrthographicCamera cam;
	private SpriteCache cache = null;
	private SpriteBatch batch = new SpriteBatch(5460);
	private FPSLogger fpsLogger = new FPSLogger();
	private List<Integer> cacheIDs = new ArrayList<Integer>();
	private List<TextureObject> uncachedObjects = new ArrayList<TextureObject>();
	private float stateTime = 0;
	private Vector3 lerpTarget = new Vector3();

	public LevelRenderer(Level level){
		setLevel(level);
		setCam(new OrthographicCamera(24, 16));
		getCam().position.set(level.getStartLocation().getBounds().getPosition(new Vector2()), 0);
		setCache(new SpriteCache(getLevel().getHeight() * getLevel().getWidth(), false));
		createBlocks();
	}
	
	private void createBlocks() {
		Iterator<Entry<BiValue<Integer, Integer>, PixelObject>> iterator = getLevel().getPixelObjects().entrySet().iterator();
		int i = 0;
		boolean isCaching = false;
		while(iterator.hasNext()){
			Entry<BiValue<Integer, Integer>, PixelObject> entry = iterator.next();
			int x = entry.getKey().getFirst();
			int y = entry.getKey().getSecond();
			PixelObject tile = entry.getValue();
			if(i == 0 && !isCaching){
				getCache().beginCache();
				isCaching = true;
			}
			if(tile.canCache() && tile.hasTexture()){
				getCache().add(((TextureObject)tile).getTexture(), x, y, tile.getBounds().getWidth(), tile.getBounds().getHeight());
				i++;
			}else if(tile instanceof TextureObject){
				addUncachedObject((TextureObject)tile);
			}
			if(i >= 16 && isCaching){
				addCachedID(getCache().endCache());
				i = 0;
				isCaching = false;
			}
		}
		if(isCaching)
			addCachedID(getCache().endCache());
	}



	public void render(float deltaTime) {
		
		if(getCache() == null)
			return;
		getCam().position.lerp(getLerpTarget().set(getLevel().getBall().getPosition().x, getLevel().getBall().getPosition().y, 0), 2f * deltaTime);
		getCam().update();

		
		getCache().setProjectionMatrix(getCam().combined);
		Gdx.gl.glDisable(GL20.GL_BLEND);
		getCache().begin();
		for(int i : getCachedIDs()){
			getCache().draw(i);
		}
		getCache().end();
		setStateTime(getStateTime() + deltaTime);
		getBatch().setProjectionMatrix(getCam().combined);
		getBatch().begin();
		for(TextureObject tile : getUncachedObjects()){
			if(tile.hasTexture() && !(tile instanceof AdvancedMovingObject))
				tile.draw(getBatch());
		}
		//Toodoo Automaticly make moving objects draw themself after moving?.
		for(Moveable m : getLevel().getMovingObjects()){
			if(m instanceof AdvancedMovingObject){
				AdvancedMovingObject amo = (AdvancedMovingObject) m;
				amo.draw(getBatch());			
			}
		}
		getBatch().end();

		getFpsLogger().log();
	}
	
	
	public void reset(){
		Gold.clearAmountOfGold();
		Gdx.app.log("Test", "" + Gold.getAmountOfGold());
	}

	public void dispose() {
		getCache().dispose();
		getBatch().dispose();
		CollideableTile.texture.getTexture().dispose();
		if(Bounceable.texture != null)
			Bounceable.texture.getTexture().dispose();
		Ball.texture.getTexture().dispose();
		Truck.texture.getTexture().dispose();
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

	public SpriteCache getCache() {
		return cache;
	}

	public void setCache(SpriteCache cache) {
		this.cache = cache;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public FPSLogger getFpsLogger() {
		return fpsLogger;
	}


	public List<Integer> getCacheIDs() {
		return cacheIDs;
	}

	public void setCacheIDs(List<Integer> cacheIDs) {
		this.cacheIDs = cacheIDs;
	}

	public Vector3 getLerpTarget() {
		return lerpTarget;
	}
	
	public List<Integer> getCachedIDs(){
		return cacheIDs;
	}
	
	public void addCachedID(int i){
		cacheIDs.add(i);
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<TextureObject> getUncachedObjects() {
		return uncachedObjects;
	}

	public void addUncachedObject(TextureObject p) {
		uncachedObjects.add(p);
	}
	
}
