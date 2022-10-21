package com.cpts.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cpts.game.screens.MainMenu;

import java.util.Random;

public class PowerPuffGirls extends Game {

    //    GameScreen gameScreen;
    MainMenu menuScreen;

    private static PowerPuffGirls instance;
    public TextureAtlas textureAtlas;
    public static Random random = new Random();

    @Override
    public void create() {
        instance = this;
        textureAtlas = new TextureAtlas("powerpuff.atlas");
        menuScreen = new MainMenu();
        setScreen(menuScreen);
//        gameScreen = new GameScreen();
//        setScreen(gameScreen);
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }
    public static PowerPuffGirls getInstance() {
        return instance;
    }
    @Override
    public void dispose() {
        menuScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        menuScreen.resize(width, height);
    }
}
