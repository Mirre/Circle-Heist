package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mirre.ball.managers.LevelRenderer;
import com.mirre.ball.objects.Level;
import com.mirre.ball.utils.ProgressBar;

public class GameScreen extends AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();
	private ProgressBar progressBar;
	private Level level;
	private LevelRenderer renderer;
	private int levelID;
	
	public GameScreen(Game game, int level) {
		super(game);
		
		Gdx.input.setInputProcessor(getStage());
		
		getStage().setViewport(new ExtendViewport(24, 18, getStage().getCamera())); //24, 18
		
		
		setProgressBar(new ProgressBar(new Rectangle(0,0,5,1)));
		
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/ui.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		getTable().setBackground(drawable);
		getTable().setFillParent(true);
		
		getStage().addActor(getTable());
		getStage().addActor(getProgressBar());
		
		
		setLevel(new Level(game, level));
		setRenderer(new LevelRenderer(getLevel(), getStage()));
		
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.2F, delta); //Change the 0.2F if you feel lag.
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			getGame().setScreen(new StartScreen(getGame()));
			return;
		}
		
	
		getLevel().update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getRenderer().render(delta);
		
		getTable().setBounds(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/2, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
		
		getProgressBar().update(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/8, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2);

		
		getStage().act();
		getStage().draw();
	}

	
	@Override
	public void resize(int width, int height) {
		getStage().getViewport().update(width, height);
	}
	
	@Override
	public void dispose() {
		getRenderer().dispose();
		getProgressBar().dispose();
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public LevelRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(LevelRenderer renderer) {
		this.renderer = renderer;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
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

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
