package com.cpts.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.badlogic.gdx.Gdx.app;


public class GameOver implements Screen {
    public SpriteBatch batch;
    public Stage stage;
    public Viewport viewport;
    public OrthographicCamera camera;
    public TextureAtlas atlas;
    public Skin skin;
    public Texture background;
    //Create Buttons
    public TextButton playButton, soundButton, exitButton;


    public GameOver() {
        //Setup the atlas and skins for the MenuScreens
        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);


        batch = new SpriteBatch();
        //background = new Texture("powerpuffmenu.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 900, camera);
        viewport.apply();

//
        camera.update();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);
        createButtons();
    }

    private void createButtons() {
        int buttonWidth = 240;
        int buttonHeight = 60;
        final Screen self = this;


        //Create Buttons
        this.playButton = new TextButton("Play Again", skin);
        this.exitButton = new TextButton("Exit", skin);
        background = new Texture("powerpuffmenu.png");


        //Set Button Characteristics
        setButtons(playButton, buttonWidth, buttonHeight, 350);
        setButtons(exitButton, buttonWidth, buttonHeight, 250);

        playButton.addListener(new InputListener() {

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startPowerPuffBattle();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getLabel().setFontScale(1.5f, 1.5f);
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getLabel().setFontScale(1.2f, 1.2f);
            }
        });



        exitButton.addListener(new InputListener() {

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                app.exit();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getLabel().setFontScale(1.5f, 1.5f);
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getLabel().setFontScale(1.2f, 1.2f);
            }
        });

        stage.addActor(playButton);
        stage.addActor(exitButton);

//        stage(new TextureRegionDrawable(new TextureRegion(new Texture("powerpuffmenu.png"))));

    }

    private void setButtons(final TextButton button, int buttonWidth, int buttonHeight, int yLocation) {
        button.setSize(buttonWidth, buttonHeight);
        button.setPosition((Gdx.graphics.getWidth() - button.getWidth()) / 2, yLocation);
        button.getLabel().setFontScale(1.2f, 1.2f);

    }

    private void startPowerPuffBattle() {


        this.dispose();
        stage.dispose();
        ((Game) app.getApplicationListener()).setScreen(new GameScreen());

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        game.batch.begin();
//        game.batch.draw(background, 0, 0, 1200, 900);
        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(1200, 900, true);
        camera.update();
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
    public void dispose() {
        skin.dispose();
        atlas.dispose();
    }
}

