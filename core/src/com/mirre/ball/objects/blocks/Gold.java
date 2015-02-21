package com.mirre.ball.objects.blocks;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mirre.ball.enums.ObjectColor;
import com.mirre.ball.objects.core.TextureObject;
import com.mirre.ball.objects.interfaces.Collideable;
import com.mirre.ball.utils.Assets;

public class Gold extends TextureObject implements Collideable {

	private boolean collected = false;
	private static int amountOfGold = 0;
	private int typeOfGold;
	
	public Gold(int x, int y, ObjectColor color) {
		super(x, y, color);
		addGold();
		setTypeOfGold(new Random().nextInt(2) + 1);
	}
	
	@Override
	public TextureRegion getTexture() {
		Assets assets = Assets.getInstance();
		if(!assets.containsKey("data/gold" + getTypeOfGold() + ".png")){
			assets.addSavedRegion("data/gold" + getTypeOfGold() + ".png", new TextureRegion(assets.getAssetManager().get("data/gold" + getTypeOfGold() + ".png", Texture.class), 0, 0, 250, 250));
		}
		return assets.getRegion("data/gold" + getTypeOfGold() + ".png");
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

	public int getTypeOfGold() {
		return typeOfGold;
	}

	public void setTypeOfGold(int typeOfGold) {
		this.typeOfGold = typeOfGold;
	}

	@Override
	public boolean passThroughAble() {
		return true;
	}

}
