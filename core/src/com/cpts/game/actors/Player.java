package com.cpts.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.cpts.game.PowerPuffGirls;

import static com.cpts.game.constants.config.WORLD_WIDTH;
import static com.cpts.game.constants.config.PLAYER_HEIGHT;
import static com.cpts.game.constants.config.PLAYER_WIDTH;
import static com.cpts.game.constants.config.PLAYER_TIME_BETWEEN_SHOTS;
import static com.cpts.game.constants.config.PLAYER_MOVEMENT_SPEED;

public class Player extends Actors {
    private int lives;
    public Boolean isHurt;
    public int shield;

    public Player() {
        super(WORLD_WIDTH / 2, WORLD_WIDTH / 2, PLAYER_WIDTH, PLAYER_HEIGHT,
                PLAYER_TIME_BETWEEN_SHOTS, PLAYER_MOVEMENT_SPEED,null);
        TextureAtlas textureAtlas = PowerPuffGirls.getInstance().getTextureAtlas();
        actorTextureRegion = textureAtlas.findRegion("BlossomMain");
        actorShieldTextureRegion = textureAtlas.findRegion("shield2");
        this.lives = 3;
        this.shield = 3;
    }

    public void decrementLives() {
        this.lives--;
    }

    public void turnTemporaryInvisible(boolean turnInvisible) {
        this.isHurt = turnInvisible;
        if (this.isHurt) {
            // @TODO: 3/26/22 Complete this function
        }
    }

    public boolean isGameOver() {
        if (lives > 0)
            return false;
        return true;
    }

    @Override
    public Vector2 getDirectionVector() {
        return null;
    }


    @Override
    public boolean hitAndCheckDestroyed() {

        return true;
    }

    @Override
    public int getLives() {
        return this.lives;
    }
}
