package com.mirre.ball.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexturedRegion {

	private static HashMap<String, TextureRegion> texturedRegions = new HashMap<String, TextureRegion>();
	private String key;
	
	public TexturedRegion(String loc, float width, float height){
		if(!texturedRegions.containsKey(loc)){
			TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(loc)), 0,0,width,height);
			texturedRegions.put(key, texture);
		}
		key = loc;
	}

	public TextureRegion get(){
		return texturedRegions.containsKey(key) ? texturedRegions.get(key) : null;
	}
	
	public static HashMap<String, TextureRegion> getTexturedRegions() {
		return texturedRegions;
	}
	
	public static void dispose(){
		for(TextureRegion region : texturedRegions.values()){
			region.getTexture().dispose();
		}
	}

}
