package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.utils.Assets;

public class Truck extends TextureObject {

	
	public Truck(int x, int y, ObjectColor color) {
		super(x, y, 5, 3, color);
	}

	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/truck.png")){
			assets.addSavedRegion("data/truck.png", new TextureRegion(assets.getAssetManager().get("data/truck.png", Texture.class), 0, 0, 652, 322));
		}
		return assets.getRegion("data/truck.png");
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
