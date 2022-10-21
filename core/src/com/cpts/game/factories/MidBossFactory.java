package com.cpts.game.factories;

import com.cpts.game.actors.Actors;
import com.cpts.game.actors.enemies.MidBoss;

public class MidBossFactory implements ActorsFactory{

    @Override
    public Actors spawnCharacter() {
        return new MidBoss();
    }


    
}
