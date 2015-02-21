package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;

public class Bounceable extends TextureObject implements Collideable {
	
	public Bounceable(int x, int y, ObjectColor color) {
		super(x, y, 1F, 1F, color);
	}

	@Override
	public void onObjectCreation(Level level){
		level.addCollideTile(this);
	}
	
	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/bounceBlock.png")){
			assets.addSavedRegion("data/bounceBlock.png", new TextureRegion(assets.getAssetManager().get("data/bounceBlock.png", Texture.class), 0, 0, 200, 200));
		}
		return assets.getRegion("data/bounceBlock.png");
	}

	@Override
	public boolean passThroughAble() {
		return false;
	}

}
