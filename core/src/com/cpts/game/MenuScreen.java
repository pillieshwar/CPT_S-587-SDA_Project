package com.cpts.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpts.game.screens.GameScreen;

public class MenuScreen implements Screen {

    public SpriteBatch batch;
    public Stage stage;
    public Viewport viewport;
    public OrthographicCamera camera;
    public TextureAtlas atlas;
    public Skin skin;
    public Texture background;

    public MenuScreen() {
        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 900, camera);
        viewport.apply();

        //camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        background = new Texture("powerpuffmenu.png");
    }


    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
//        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        //TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        playButton.setHeight(1);
        //Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        ;
        //Add buttons to table
        mainTable.add(playButton);

        mainTable.row();
//        mainTable.add (playButton).width (playButton.getWidth()*0.5f);

//        mainTable.row();
        //mainTable.add(optionsButton);
        mainTable.add(exitButton);
//        playButton.setTransform(true);
//        playButton.setScale(4f);
//
//        exitButton.setTransform(true);
//        exitButton.setScale(4f);
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("powerpuffmenu.png"))));

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
//        batch.begin();
//        batch.draw(background, 0, 0, 72, 128);
//        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(1200, 900,true);
//        camera.position.set(camera.viewportWidth, camera.viewportHeight, true);
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
