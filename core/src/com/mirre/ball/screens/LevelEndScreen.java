package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mirre.ball.utils.ChainedTextButton;

public class LevelEndScreen extends  AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();
	
	public LevelEndScreen(Game game, final boolean won, final int levelID){
		super(game);
		
		Gdx.input.setInputProcessor(getStage());
		
		boolean b = Gdx.app.getType() == ApplicationType.Android;
		
		getTable().setFillParent(true);
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal(won ? "data/winScreen.png" : "data/failScreen.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		getTable().setBackground(drawable);
		getStage().addActor(getTable());
		
		TextButton nextLevel = new ChainedTextButton(won ? "Next Level" : "Retry")
		.addFont(b ? 4F : 1.3F, b ? 4F : 1.3F, Color.WHITE).styleUp(Color.DARK_GRAY)
		.styleDown(Color.DARK_GRAY).styleChecked(Color.DARK_GRAY)
		.styleOver(Color.RED).create();
		nextLevel.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(won){
					if(Gdx.files.internal("data/level" + (levelID + 1) + ".png").exists())
						getGame().setScreen(new GameScreen(getGame(), "" + (levelID + 1)));
				}else
					getGame().setScreen(new GameScreen(getGame(), "" + levelID));
			}
		});
		
		TextButton mainMenu = new ChainedTextButton("Main Menu")
		.addFont(b ? 4F : 1.3F, b ? 4F : 1.3F, Color.WHITE).styleUp(Color.DARK_GRAY)
		.styleDown(Color.DARK_GRAY).styleChecked(Color.DARK_GRAY)
		.styleOver(Color.RED).create();
		mainMenu.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				getGame().setScreen(new StartScreen(getGame()));
			}
		});
		
		
		if(b){
			getTable().add(nextLevel).size(300, 150).padTop(150).row();
			getTable().add(mainMenu).size(300, 150).space(30);
		}else{
			getTable().add(nextLevel).size(100, 50).row();
			getTable().add(mainMenu).size(100, 50).space(10);
		}
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1); //Changes background color.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		getStage().act(delta);
		getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
		getStage().getViewport().update(width, height, true);
	}


	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
