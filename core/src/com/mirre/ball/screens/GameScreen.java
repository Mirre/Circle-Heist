package com.mirre.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mirre.ball.CircleHeist;
import com.mirre.ball.managers.LevelRenderer;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.utils.ProgressBar;

public class GameScreen extends AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();
	private ProgressBar progressBar;
	private Label labelXYGold;
	private Level level;
	private LevelRenderer renderer;
	private int levelID;
	
	public GameScreen(Game game, int level) {
		super(game);
		
		CircleHeist.rgbaToRGB(Color.PINK);
		Gdx.input.setInputProcessor(getStage());
		
		getStage().setViewport(new ExtendViewport(24, 18, getStage().getCamera())); //24, 18
		
	
		ProgressBar bar = new ProgressBar(5, 1).setProgress(10);
		for(int i = 10 ; i != -1 ; i--){
			TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar" + i + ".png")), 100, 50);
			bar.addBarTextures(region);
		}
		setProgressBar(bar);
		
		BitmapFont font = new BitmapFont();
		font.setUseIntegerPositions(false);
		LabelStyle textStyle = new LabelStyle(font, Color.RED);
		
		setLabelXYGold(new Label("X / Y", textStyle));
		getLabelXYGold().setFontScaleX(getLabelXYGold().getFontScaleX()/10);
		getLabelXYGold().setFontScaleY(getLabelXYGold().getFontScaleY()/10);
		
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/ui.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		
		getTable().setBackground(drawable);
		getTable().setFillParent(true);
		getStage().addActor(getTable());
		getStage().addActor(getProgressBar());
		getStage().addActor(getLabelXYGold());
		
		
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
		
		getLabelXYGold().setX(getStage().getCamera().position.x + getStage().getCamera().viewportWidth/3);
		getLabelXYGold().setY(getStage().getCamera().position.y - 2.2F);
		getLabelXYGold().setText(getLevel().getBall().getGoldCollected() + " / " + Gold.getAmountOfGold());
				
		getProgressBar().update(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/8, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2);

		
		getStage().act(delta / 10);
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
		getStage().dispose();
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

	public Label getLabelXYGold() {
		return labelXYGold;
	}

	public void setLabelXYGold(Label labelXYGold) {
		this.labelXYGold = labelXYGold;
	}

}
