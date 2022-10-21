package com.cpts.game.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.actors.Actors;

public interface ActorsFactory {
    Actors spawnCharacter();
//    Actors spawnCharacter(TextureRegion enemyTextureRegion, TextureRegion enemyShieldTextureRegion);
}
