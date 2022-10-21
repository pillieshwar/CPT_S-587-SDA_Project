package com.cpts.game.factories;

import com.cpts.game.actors.Actors;
import com.cpts.game.actors.enemies.RegularEnemyA;

public class RegularEnemyAFactory implements ActorsFactory {
    @Override
    public Actors spawnCharacter() {
        return new RegularEnemyA();
    }
}
