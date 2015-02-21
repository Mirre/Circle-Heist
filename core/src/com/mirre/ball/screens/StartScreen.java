package com.mirre.ball.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.mirre.ball.CircleHeist;
import com.mirre.ball.utils.ChainedTextButton;

public class StartScreen extends AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();


	public StartScreen(Game game){
		super(game);
		
		Gdx.input.setInputProcessor(getStage());
		
		boolean b = Gdx.app.getType() == ApplicationType.Android;
		
		getTable().setFillParent(true);
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/background.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		getTable().setBackground(drawable);
		getStage().addActor(getTable());
		
		for(int i = 1 ; Gdx.files.internal("data/level" + i + ".png").exists() ; i++){
			final int level = i;
			TextButton textButton = new ChainedTextButton("" + i)
			.addFont(b ? 4F : 1.3F, b ? 4F : 1.3F, Color.WHITE).styleUp(Color.DARK_GRAY)
			.styleDown(Color.DARK_GRAY).styleChecked(Color.LIGHT_GRAY)
			.styleOver(Color.RED).create();
			if(b)
				getTable().add(textButton).size(300, 150).space(30);
			else
				getTable().add(textButton).size(100, 50).space(10);
			textButton.addListener(new ClickListener(){
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					if(((CircleHeist) getGame()).getCompletedLevels() >= level)
						getGame().setScreen(new GameScreen(getGame(), level));
				}
			});
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
	
	@Override
	public void dispose() {
		getStage().clear();
	}
}


//BitmapFont font = new BitmapFont();
//font.setScale(2F, 2F);

//LabelStyle labelStyle = new LabelStyle(font, Color.OLIVE);
//Label heading = new Label("Circle's Heist", labelStyle);
//heading.setX(getStage().getWidth()/2 - heading.getWidth()/2);
//heading.setY(getStage().getHeight()/3 * 2);

//getStage().addActor(heading);
