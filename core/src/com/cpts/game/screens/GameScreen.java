package com.cpts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpts.game.constants.config;
import com.cpts.game.gamesystems.GamePlayTest;
import com.cpts.game.observer.ScoreObserver;

public class GameScreen implements Screen, ScoreObserver {

	public Camera camera;
	public Viewport viewport;

	// Graphics
	public SpriteBatch batch;

	// Sound
	Music wavSound;
	BackGround backGround;

	TextButton pauseButton;

//    GamePlay gamePlay;
	GamePlayTest gamePlayTest;
	ScoreDisplay scoreDisplay;
	private boolean renderScore;
	private boolean renderLives;
	private boolean renderPower;
	static GameScreen gameScreen;

	public GameScreen() {
		wavSound = Gdx.audio.newMusic(Gdx.files.internal("theme.mp3"));
		camera = new OrthographicCamera();
		viewport = new StretchViewport(config.WORLD_WIDTH, config.WORLD_HEIGHT, camera);
		// Background Screen Rendering
		this.backGround = new BackGround();
		// Initializing GamePlay
//        gamePlay = new GamePlay();

		// Testing the new system
		gamePlayTest = new GamePlayTest();

		// Intialyzing the Main batch
		batch = new SpriteBatch();
		// Initializing ScoreDisplays

		scoreDisplay = new ScoreDisplay(batch, gamePlayTest.getScoreTracker());
		batch.begin();
		scoreDisplay.prepareHUD();
		batch.end();
		renderScore = true;
		renderLives = true;
		renderPower = true;
		gameScreen = this;
	}

	public static GameScreen getInstance() {
		// if(gameScreen == null){
		return gameScreen;
		// }
		// return gameScreen;
	}

	@Override
	public void render(float deltaTime) {

		batch.begin();
		// wavSound.play();
		backGround.renderBackground(batch, deltaTime);
		gamePlayTest.render(batch, deltaTime);

		if (renderScore) {
			scoreDisplay.updateAndRenderScore();
			renderScore = false;
		}
		scoreDisplay.updateAndRenderScore();

		if (renderLives) {
			scoreDisplay.updateAndRenderLives();
			renderLives = false;
		}
		scoreDisplay.updateAndRenderLives();

		if (renderPower) {
			scoreDisplay.updateAndRenderPower();
			renderPower = false;
		}
		scoreDisplay.updateAndRenderPower();

		batch.end();
		// Gdx.app.exit();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void updateScore() {
		this.renderScore = true;

	}

	@Override
	public void updateLives() {
		this.renderLives = true;

	}

	@Override
	public void updatePower() {
		this.renderPower = true;

	}

}