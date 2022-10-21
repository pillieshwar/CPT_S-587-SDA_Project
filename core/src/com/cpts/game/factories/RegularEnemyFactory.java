package com.cpts.game.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.actors.Actors;
import com.cpts.game.actors.enemies.RegularEnemyA;
import com.cpts.game.actors.enemies.WaveEnemy;

public class RegularEnemyFactory implements EnemyFactory {


    @Override
    public Actors spawnCharacter(TextureRegion enemyTextureRegion, TextureRegion enemyShieldTextureRegion, float xCentre, float yCentre, float width, float height, float movementSpeed, String movement) {
        return new WaveEnemy(enemyTextureRegion, enemyShieldTextureRegion, xCentre, yCentre, width, height, movementSpeed, movement);
    }


}
