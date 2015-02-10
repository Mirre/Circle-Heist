package com.mirre.ball.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mirre.ball.managers.LevelRenderer;
import com.mirre.ball.objects.Level;

public class GameScreen extends AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();
	private ProgressBar bar;
	private Level level;
	private LevelRenderer renderer;
	private int levelID;
	
	public GameScreen(Game game, int level) {
		super(game);
		TextureRegionDrawable bar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/progressbar.png")), 100, 50));
		TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/knob.png")), 20, 20));
		bar.setMinHeight(1);
		bar.setMinWidth(1);
		knob.setMinHeight(1);
		knob.setMinWidth(1);
		
		ProgressBarStyle style = new ProgressBarStyle(bar, knob);
		//style.knobBefore = knob;
		//style.disabledBackground = bar;
		//style.knobAfter = bar;
		setBar(new ProgressBar(0, 10, 1F, false, style));

		getBar().setBounds(0, 0, 1F, 1F);
		getBar().setValue(10F);
		getBar().setAnimateDuration(0);
		
		Gdx.input.setInputProcessor(getStage());
		
		getStage().setViewport(new ExtendViewport(24, 18, getStage().getCamera())); //24, 18
		
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/ui.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		getTable().setBackground(drawable);
		getTable().setFillParent(true);
		
		
		getTable().add(getBar()).maxWidth(10).padTop(10);
		getStage().addActor(getTable());
		
		
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
		
		Gdx.app.log("Bar", "X" + getBar().getX() + " Y" + getBar().getY());
		Gdx.app.log("Ball", "X" + getLevel().getBall().getBounds().getX() + " Y" + getLevel().getBall().getBounds().getY()); 
		Gdx.app.log("Camera", "X" + getStage().getCamera().position.x + " Y" + getStage().getCamera().position.y);

		getTable().setBounds(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/2, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
		
		//getBar().setX((getStage().getCamera().position.x));
		//getBar().setY(getStage().getCamera().position.y);

		
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

	public ProgressBar getBar() {
		return bar;
	}

	public void setBar(ProgressBar bar) {
		this.bar = bar;
	}

}