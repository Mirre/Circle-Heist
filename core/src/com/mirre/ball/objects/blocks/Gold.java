package com.mirre.ball.objects.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.objects.Ball;
import com.mirre.ball.objects.blocks.core.SimpleMovingObject;
import com.mirre.ball.objects.blocks.core.TextureObject;
import com.mirre.ball.objects.blocks.interfaces.Collideable;

public class Gold extends TextureObject implements Collideable {

	public static TextureRegion texture = null;
	private boolean collected = false;
	private static int amountOfGold = 0;
	
	public Gold(int x, int y) {
		super(x, y);
		addGold();
	}

	@Override
	public TextureRegion getTexture() {
		if(texture == null)
			texture = new TextureRegion(new Texture(Gdx.files.internal("data/gold.png")), 0, 0, 20, 20);
		return texture;
	}

	@Override
	public boolean hasTexture(){
		return !isCollected();
	}

	public boolean isCollected() {
		return collected;
	}
	
	@Override
	public boolean canCache() {
		return false;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public static int getAmountOfGold() {
		return amountOfGold;
	}

	public static void addGold() {
		Gold.amountOfGold++;
	}
	
	public static void clearAmountOfGold(){
		Gold.amountOfGold = 0;
	}

	@Override
	public void onCollideX(SimpleMovingObject mto) {
		if(mto instanceof Ball && !isCollected()){
			((Ball)mto).addGold();
			setCollected(true);
		}
	}

	@Override
	public void onCollideY(SimpleMovingObject mto) {
		if(mto instanceof Ball && !isCollected()){
			((Ball)mto).addGold();
			setCollected(true);
		}
	}
}
