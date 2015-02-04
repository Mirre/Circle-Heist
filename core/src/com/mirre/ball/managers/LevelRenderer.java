package com.mirre.ball.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.Bounceable;
import com.mirre.ball.objects.blocks.CollideableTile;
import com.mirre.ball.objects.blocks.Truck;
import com.mirre.ball.objects.core.AdvancedMovingObject;
import com.mirre.ball.objects.core.PixelObject;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Moveable;
import com.mirre.ball.objects.moving.Ball;
import com.mirre.ball.utils.BiValue;

public class LevelRenderer {

	private Level level;
	private SpriteCache cache;
	private Stage stage;
	private FPSLogger fpsLogger = new FPSLogger();
	private List<Integer> cacheIDs = new ArrayList<Integer>();
	private List<TextureObject> uncachedObjects = new ArrayList<TextureObject>();
	private float stateTime = 0;
	private Vector3 lerpTarget = new Vector3();
	
	
	
	public LevelRenderer(Level level, Stage stage){
		
		setLevel(level);
		setStage(stage);
		stage.getCamera().position.set(level.getStartLocation().getBounds().getPosition(new Vector2()), 0);
		setCache(new SpriteCache(getLevel().getHeight() * getLevel().getWidth(), false));
		createTiles();
	}
	
	public void createTiles(){
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
		getStage().getCamera().position.lerp(getLerpTarget().set(getLevel().getBall().getPosition().x, getLevel().getBall().getPosition().y, 0), 2f * deltaTime);
		getStage().getCamera().update();

		
		getCache().setProjectionMatrix(getStage().getCamera().combined);
		Gdx.gl.glDisable(GL20.GL_BLEND);
		getCache().begin();
		for(int i : getCacheIDs()){
			getCache().draw(i);
		}
		getCache().end();
		setStateTime(getStateTime() + deltaTime);
		getStage().getBatch().setProjectionMatrix(getStage().getCamera().combined);
		getStage().getBatch().begin();
		for(TextureObject tile : getUncachedObjects()){
			if(tile.hasTexture() && !(tile instanceof AdvancedMovingObject)){
				tile.draw(getStage().getBatch());
			}
		}
		for(Moveable m : getLevel().getMovingObjects()){
			if(m instanceof AdvancedMovingObject){
				AdvancedMovingObject amo = (AdvancedMovingObject) m;
				amo.draw(getStage().getBatch());			
			}
		}
		getStage().getBatch().end();

		getFpsLogger().log();
	}	
	
	public void dispose() {
		getCache().dispose();
		CollideableTile.texture.getTexture().dispose();
		if(Bounceable.texture != null)
			Bounceable.texture.getTexture().dispose();
		Ball.textureLeft.getTexture().dispose();
		Ball.textureRight.getTexture().dispose();
		Ball.textureStealth.getTexture().dispose();
		Truck.texture.getTexture().dispose();
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public SpriteCache getCache() {
		return cache;
	}

	public void setCache(SpriteCache cache) {
		this.cache = cache;
	}

	public FPSLogger getFpsLogger() {
		return fpsLogger;
	}

	public List<Integer> getCacheIDs() {
		return cacheIDs;
	}

	public void addCachedID(int i){
		cacheIDs.add(i);
	}

	public List<TextureObject> getUncachedObjects() {
		return uncachedObjects;
	}

	public void addUncachedObject(TextureObject p) {
		uncachedObjects.add(p);
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Vector3 getLerpTarget() {
		return lerpTarget;
	}

	public void setLerpTarget(Vector3 lerpTarget) {
		this.lerpTarget = lerpTarget;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
