package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.core.TextureObject;

public class Truck extends TextureObject {

	public static TextureRegion texture = null;
	
	public Truck(int x, int y) {
		super(x, y, 5, 3, ObjectColor.TRUCK);
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/truck.png")), 0, 0, 652, 322);
		return texture;
	}

	@Override
	public boolean canCache(){
		return false;
	}
	
	@Override
	public void onObjectCreation(Level level) {
		level.setStartLocation(this);
		Gdx.app.log("Create", "Truck");
	}
	
}
