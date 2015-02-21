package com.mirre.ball.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mirre.ball.CircleHeist;
import com.mirre.ball.managers.LevelRenderer;
import com.mirre.ball.objects.Level;
import com.mirre.ball.objects.blocks.Gold;
import com.mirre.ball.objects.moving.CircleController;
import com.mirre.ball.utils.ProgressBar;

public class GameScreen extends AbstractScreen {

	private Stage stage = new Stage();
	private Table table = new Table();
	private ProgressBar progressBar;
	private Label labelXYGold;
	private Level level;
	private LevelRenderer renderer;
	
	private Button moveButton;
	private Button stealthButton;
	private Button jumpButton;
	
	public GameScreen(Game game, int levelID) {
		super(game);
		
		
		
		boolean b = Gdx.app.getType() == ApplicationType.Android;
		CircleHeist.rgbaToRGB(0.1f, 0.1f, 0.1f, 1);
		Gdx.input.setInputProcessor(getStage());
		
		getStage().setViewport(new ExtendViewport(b ? 16 : 24, b ? 12 : 18, getStage().getCamera())); //24, 18
	
		ProgressBar bar = Gdx.app.getType() == ApplicationType.Android ? new ProgressBar(7, 1).setProgress(10) : new ProgressBar(5, 1).setProgress(10);
		for(int i = 10 ; i != -1 ; i--){
			TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/progressbar" + i + ".png")), 100, 50);
			bar.addBarTextures(region);
		}
		setProgressBar(bar);
		
		BitmapFont font = new BitmapFont();
		font.setUseIntegerPositions(false);
		LabelStyle textStyle = new LabelStyle(font, Color.RED);
		
		setLabelXYGold(new Label("X / Y", textStyle));
		getLabelXYGold().setFontScaleX(getLabelXYGold().getFontScaleX()/(b ? 15 : 10));
		getLabelXYGold().setFontScaleY(getLabelXYGold().getFontScaleY()/(b ? 15 : 10));
		
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("data/ui.png")));
		TextureRegionDrawable drawable = new TextureRegionDrawable(region); 
		
	
		
		getTable().setBackground(drawable);
		getTable().setFillParent(true);
		getStage().addActor(getTable());
		getStage().addActor(getProgressBar());
		getStage().addActor(getLabelXYGold());
		
		if(b){
			TextureRegion asd = new TextureRegion(new Texture(Gdx.files.internal("data/ASD.png")));
			TextureRegionDrawable asdDrawable = new TextureRegionDrawable(asd); 
			setMoveButton(new Button(asdDrawable));
			getMoveButton().addListener(new ClickListener(){
				@Override
				public void touchDragged(InputEvent event, float x, float y, int pointer){
					super.touchDragged(event, x, y, pointer);
					CircleController.movementButtonX = x;
					CircleController.movementButtonY = y;
				}
			});
			
			TextureRegion stealth = new TextureRegion(new Texture(Gdx.files.internal("data/StealthButton.png")));
			TextureRegionDrawable stealthDrawable = new TextureRegionDrawable(stealth); 
			setStealthButton(new Button(stealthDrawable));
			getStealthButton().addListener(new ClickListener());
			
			TextureRegion jump = new TextureRegion(new Texture(Gdx.files.internal("data/JumpButton.png")));
			TextureRegionDrawable jumpDrawable = new TextureRegionDrawable(jump); 
			setJumpButton(new Button(jumpDrawable));
			getJumpButton().addListener(new ClickListener());
			
			
			getStage().addActor(getMoveButton());
			getStage().addActor(getStealthButton());
			getStage().addActor(getJumpButton());
		}
		setLevel(new Level(getGame(), levelID));
		setRenderer(new LevelRenderer(getLevel(), getStage()));
		
	
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.1F, delta); //Change the 0.2F if you feel lag.
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			getGame().setScreen(new StartScreen(getGame()));
			return;
		}
		
	
		if(!CircleHeist.isPaused())
			getLevel().update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getRenderer().render(delta);
		
		getTable().setBounds(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/2, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
		
		getLabelXYGold().setX(getStage().getCamera().position.x + getStage().getCamera().viewportWidth/3);
		getLabelXYGold().setY(getStage().getCamera().position.y - getStage().getCamera().viewportHeight/ 2.5F);
		getLabelXYGold().setText(getLevel().getCircle().getGoldCollected() + " / " + (Gold.getAmountOfGold()));
				
	

		if(Gdx.app.getType() == ApplicationType.Android){
			getLabelXYGold().setY(getStage().getCamera().position.y - getStage().getCamera().viewportHeight/ 2.5F);
			getProgressBar().update(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/4, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2);
			getMoveButton().setBounds(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/2, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, 5, 5);
			getStealthButton().setBounds(getStage().getCamera().position.x + getStage().getCamera().viewportWidth/3 - 0.5F, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, 2, 2);
			getJumpButton().setBounds(getStage().getCamera().position.x + getStage().getCamera().viewportWidth/6 - 0.5F, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2, 2, 2);
		}else{
			getLabelXYGold().setY(getStage().getCamera().position.y - getStage().getCamera().viewportHeight/8.18F);
			getProgressBar().update(getStage().getCamera().position.x - getStage().getCamera().viewportWidth/8, getStage().getCamera().position.y - getStage().getCamera().viewportHeight/2);
		}
		
		
		getStage().act(delta / 10);
		getStage().draw();
	}

	@Override
	public void pause() {
		CircleHeist.setPaused(true);
	}
	
	@Override
	public void resume() {
		CircleHeist.setPaused(false);
	}
	
	@Override
	public void resize(int width, int height) {
		getStage().getViewport().update(width, height);
	}
	
	@Override
	public void dispose() {
		Gdx.app.log("Test", "");
		getLabelXYGold().getStyle().font.dispose();
		getProgressBar().dispose();
		getStage().clear();
		//getStage().dispose();
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

	public Button getMoveButton() {
		return moveButton;
	}

	public void setMoveButton(Button moveButton) {
		this.moveButton = moveButton;
	}

	public Button getStealthButton() {
		return stealthButton;
	}

	public void setStealthButton(Button stealthButton) {
		this.stealthButton = stealthButton;
	}

	public Button getJumpButton() {
		return jumpButton;
	}

	public void setJumpButton(Button jumpButton) {
		this.jumpButton = jumpButton;
	}

}
