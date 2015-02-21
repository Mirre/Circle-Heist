package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;

public class Lava extends TextureObject implements Collideable {

	
	public Lava(int x, int y, ObjectColor color) {
		super(x, y, 1F, 0.7F, color);
	}

	@Override
	public boolean passThroughAble() {
		return true;
	}

	
	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/Lava.png"))
			assets.addSavedRegion("data/Lava.png", new TextureRegion(assets.getAssetManager().get("data/Lava.png", Texture.class), 0, 0, 200, 200));
		return assets.getRegion("data/Lava.png");
	}
	
	@Override
	public float getTextureHeight(){
		return 1;
	}
	
}
