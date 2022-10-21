package com.cpts.game.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.PowerPuffGirls;
import com.cpts.game.actors.Enemy;
// import com.cpts.game.factories.LaserFactory;

import static com.cpts.game.constants.config.WORLD_WIDTH;
import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.ENEMY_TIME_BETWEEN_SHOTS;
import static com.cpts.game.constants.config.ENEMY_LASER_HEIGHT;
import static com.cpts.game.constants.config.ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.ENEMY_LASER_MOVEMENT_SPEED;
import static com.cpts.game.constants.config.REG_EWNEMY_B_SPEED;;

public class RegularEnemyB extends Enemy {
    private final TextureRegion enemyTextureRegion;
    private final TextureRegion enemyShieldTextureRegion;

    public static int shield = 1;

    public RegularEnemyB() {
        super((float) (WORLD_WIDTH - 5), WORLD_HEIGHT, 5, 6,
                ENEMY_TIME_BETWEEN_SHOTS, shield, REG_EWNEMY_B_SPEED, null);

        TextureAtlas textureAtlas = PowerPuffGirls.getInstance().getTextureAtlas();
        this.enemyTextureRegion = textureAtlas.findRegion("enemyBlue5");
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
