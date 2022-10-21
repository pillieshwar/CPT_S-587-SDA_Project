package com.cpts.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.constants.config;

public class BackGround {

    public TextureAtlas textureAtlas;
    public TextureRegion[] backgrounds;
    public float backgroundHeight; //height of background in World units

    //timing
    public float[] backgroundOffsets = {0, 0, 0, 0};
    public float backgroundMaxScrollingSpeed;

    public BackGround() {

        //set up the texture atlas
        textureAtlas = new TextureAtlas("powerpuff.atlas");
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("cloud layer 1");
        backgrounds[1] = textureAtlas.findRegion("cloud layer 2");
        backgrounds[2] = textureAtlas.findRegion("mainbkg1");
        backgrounds[3] = textureAtlas.findRegion("cloud layer 1");
        backgroundHeight = config.WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (config.WORLD_HEIGHT) / 4;
    }

    public void renderBackground(SpriteBatch batch, float deltaTime) {
        //update position of background images
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > config.WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
                    config.WORLD_WIDTH, backgroundHeight);
        }
    }
}
