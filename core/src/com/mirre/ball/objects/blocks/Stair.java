package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;

public class Stair extends TextureObject implements Collideable {

	
	public Stair(int x, int y, ObjectColor color) {
		super(x, y, 1, 1, color);
	}

	@Override
	public boolean passThroughAble() {
		return true;
	}
	
	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/stairs.png"))
			assets.addSavedRegion("data/stairs.png", new TextureRegion(assets.getAssetManager().get("data/stairs.png", Texture.class), 0, 0, 100, 100));
		return assets.getRegion("data/stairs.png");
	}

}
