package com.cpts.game.screens;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpts.game.constants.config;
import com.cpts.game.observer.ScoreTracker;
import com.cpts.game.gamesystems.GamePlayTest;

import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.Screen;
public class ScoreDisplay{
	SpriteBatch batch;
	ScoreTracker scoreTracker;
	public Stage stage;
	public Viewport viewport;
	public OrthographicCamera camera;
	public TextButton playButton;
	public Skin skin;
	public TextureAtlas atlas;
	// Heads-Up Display
	BitmapFont font;
	private GamePlayTest gamePlay;
	float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth, hudCentreXP;

	public ScoreDisplay(SpriteBatch batch, ScoreTracker scoreTracker) {
		this.batch = batch;
		this.scoreTracker = scoreTracker;

	}


	// Prepare the scoredisplay
	public void prepareHUD() {
		// Create a BitmapFont from our font file
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
				Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 68;
		fontParameter.borderWidth = 3.6f;
		fontParameter.color = new Color(1, 1, 1, 0.3f);
		fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

		font = fontGenerator.generateFont(fontParameter);

		// scale the font to fit world
		font.getData().setScale(0.08f);

		// calculate hud margins, etc.
		hudVerticalMargin = font.getCapHeight() / 2;
		hudLeftX = hudVerticalMargin;
		hudRightX = config.WORLD_WIDTH * 2 / 3 - hudLeftX;
		hudCentreX = config.WORLD_WIDTH / 5;
		hudCentreXP = config.WORLD_WIDTH / 2;
		hudRow1Y = config.WORLD_HEIGHT - hudVerticalMargin;
		hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
		hudSectionWidth = config.WORLD_WIDTH / 3;
		font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(batch, "Shield", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
		font.draw(batch, "Power", hudCentreXP, hudRow1Y, hudSectionWidth, Align.center, false);
		// render second row values
		font.draw(batch, String.format(Locale.getDefault(), "%06d", scoreTracker.getScore()), hudLeftX, hudRow2Y,
				hudSectionWidth, Align.left, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", scoreTracker.getShieldHealth()), hudCentreX,
				hudRow2Y, hudSectionWidth, Align.center, false);

		}

	// Update and render the score display
	public void updateAndRenderScore() {
		// render top row labels
		font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);

		font.draw(batch, "Shield", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
		// render second row values
		font.draw(batch, String.format(Locale.getDefault(), "%06d", scoreTracker.getScore()), hudLeftX, hudRow2Y,
				hudSectionWidth, Align.left, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", scoreTracker.getShieldHealth()), hudCentreX,
				hudRow2Y, hudSectionWidth, Align.center, false);
	}

	public void updateAndRenderLives() {
		// render top row labels
		font.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", scoreTracker.getLives()), hudRightX, hudRow2Y,
				hudSectionWidth, Align.right, false);
	}

	// Update and render the power display
	public void updateAndRenderPower() {
		// render top row labels
		font.draw(batch, "Power", hudCentreXP, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", scoreTracker.getPower()), hudCentreXP, hudRow2Y,
				hudSectionWidth, Align.center, false);
	}


}
