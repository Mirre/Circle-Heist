package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;

public class CollideableTile extends TextureObject implements Collideable {

	
	public CollideableTile(int x, int y, ObjectColor color) {
		super(x, y, color);
	}
	
	@Override
	public void onObjectCreation(Level level){
		level.addCollideTile(this);
	}

	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/tile.png")){
			assets.addSavedRegion("data/tile.png", new TextureRegion(assets.getAssetManager().get("data/tile.png", Texture.class), 0, 0, 50, 50));
		}
		return assets.getRegion("data/tile.png");
	}

	@Override
	public boolean passThroughAble() {
		return false;
	}

}
