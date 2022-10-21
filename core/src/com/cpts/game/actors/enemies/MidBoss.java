package com.cpts.game.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.PowerPuffGirls;
import com.cpts.game.actors.Enemy;

import static com.cpts.game.constants.config.WORLD_WIDTH;
import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.MID_ENEMY_HEIGHT;
import static com.cpts.game.constants.config.MID_ENEMY_WIDTH;
import static com.cpts.game.constants.config.MID_ENEMY_TIME_BETWEEN_SLOTS;
import static com.cpts.game.constants.config.MID_ENEMY_LASER_HEIGHT;
import static com.cpts.game.constants.config.MID_ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.MID_ENEMY_LASER_SPEED;
import static com.cpts.game.constants.config.MID_ENEMY_MOVEMENT_SPEED;

public class MidBoss extends Enemy {
    private final TextureRegion enemyTextureRegion;
    private final TextureRegion enemyShieldTextureRegion;

    public static int shield = 20;
    public static float movementSpeed;

    public MidBoss() {
        super(PowerPuffGirls.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT - 5, MID_ENEMY_WIDTH,
                MID_ENEMY_HEIGHT,
                MID_ENEMY_TIME_BETWEEN_SLOTS, shield, MID_ENEMY_MOVEMENT_SPEED, null);
        TextureAtlas textureAtlas = PowerPuffGirls.getInstance().getTextureAtlas();
        this.enemyTextureRegion = textureAtlas.findRegion("HIM");
        this.enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(enemyTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (shield > 0) {
            batch.draw(enemyShieldTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        }
    }

    @Override
    public boolean hitAndCheckDestroyed() {
        if (shield > 0) {
            shield--;
            return false;
        }
        return true;
    }

}
