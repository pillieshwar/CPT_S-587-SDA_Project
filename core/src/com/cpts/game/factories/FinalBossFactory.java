package com.cpts.game.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.actors.Actors;
import com.cpts.game.actors.enemies.FinalBoss;

public class FinalBossFactory implements ActorsFactory {

    @Override
    public Actors spawnCharacter() {
        return new FinalBoss();
    }


}
