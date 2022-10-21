package com.cpts.game.factories;

import com.cpts.game.actors.Actors;
import com.cpts.game.actors.enemies.RegularEnemyB;

public class RegularEnemyBFactory implements ActorsFactory {
    @Override
    public Actors spawnCharacter() {
        return new RegularEnemyB();
    }
}
