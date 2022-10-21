package com.cpts.game.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.actors.Actors;

public interface EnemyFactory {

    //    Actors spawnCharacter();
    Actors spawnCharacter(TextureRegion enemyTextureRegion, TextureRegion enemyShieldTextureRegion, float xCentre, float yCentre, float width, float height, float movementSpeed, String movement);
}
