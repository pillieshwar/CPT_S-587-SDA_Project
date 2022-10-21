package com.cpts.game.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.PowerPuffGirls;
import com.cpts.game.actors.Enemy;
import com.cpts.game.command.EnemyMovementInvoker;
import com.cpts.game.constants.config;

import static com.cpts.game.constants.config.*;

;

public class WaveEnemy extends Enemy {
    private final TextureRegion enemyTextureRegion;
    private final TextureRegion enemyShieldTextureRegion;

    public static int shield = 1;
    float leftLimit = 0.0f + boundingBox.x;
    float downLimit = 0.0f + boundingBox.y;
    float rightLimit = config.WORLD_WIDTH - boundingBox.x
            - boundingBox.width;
    float upLimit = config.WORLD_HEIGHT - boundingBox.height;
    EnemyMovementInvoker enemyMovement;

    @Override
    public void movement(float deltaTime) {
        switch (this.movement) {
            case "Random":
                randomMovement(deltaTime);
                break;
            case "V":
                enemyMovement.executeVMovement(deltaTime);
                break;
            case "W":
                enemyMovement.executeWMovement(deltaTime);
                break;
            case "L":
                enemyMovement.executeLMovement(deltaTime);
                break;
            case "RL":
                enemyMovement.executeRLMovement(deltaTime);
                break;
        }
    }

    public void randomMovement(float deltaTime) {
        leftLimit = -boundingBox.x;

        downLimit = (float) WORLD_HEIGHT / 2 - boundingBox.y;

        rightLimit = WORLD_WIDTH - boundingBox.x - boundingBox.width;
        upLimit = WORLD_HEIGHT - boundingBox.y - boundingBox.height;

        float xMove = getDirectionVector().x * movementSpeed * deltaTime;
        float yMove = getDirectionVector().y * movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        translate(xMove, yMove);

    }

    public WaveEnemy(TextureRegion enemyTextureRegion, TextureRegion enemyShieldTextureRegion, float xCentre, float yCentre, float width, float height, float movementSpeed, String movement) {
        super(xCentre, yCentre, width, height,
                ENEMY_TIME_BETWEEN_SHOTS, shield, movementSpeed, movement);
        TextureAtlas textureAtlas = PowerPuffGirls.getInstance().getTextureAtlas();
        this.enemyTextureRegion = enemyTextureRegion;
        this.enemyShieldTextureRegion = enemyShieldTextureRegion;
        enemyMovement = new EnemyMovementInvoker(this, upLimit,  downLimit,  rightLimit,  leftLimit,  boundingBox );
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
