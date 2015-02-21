package com.mirre.ball.utils;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;

public class Assets {

	private static Assets instance = null;
	private AssetManager assetManager = new AssetManager();
	private HashMap<String, TextureRegion> savedRegions = new HashMap<String, TextureRegion>();
	
	public static Assets getInstance() {
		if(instance == null)
			instance = new Assets();
		return instance;
	}
	
	public void dispose(){
		getAssetManager().dispose();
		for(TextureRegion region : savedRegions.values()){
			region.getTexture().dispose();
		}
		savedRegions.clear();
	}
	
	public void load(){
		for(ObjectColor ob : ObjectColor.values()){
			if(ob.hasTexture()){
				for(String s : ob.getTextureLocations())
					getAssetManager().load(s, Texture.class);
			}
		}
	}
	
	public void reload(){
		getAssetManager().clear();
		load();
	}

	public HashMap<String, TextureRegion> getSavedRegions() {
		return savedRegions;
	}
	
	public TextureRegion getRegion(String key){
		if(savedRegions.containsKey(key))
			return savedRegions.get(key);
		return null;
	}
	
	public boolean containsKey(String key){
		return savedRegions.containsKey(key);
	}

	public void addSavedRegion(String key, TextureRegion region) {
		if(!savedRegions.containsKey(key))
			savedRegions.put(key, region);
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	
}
