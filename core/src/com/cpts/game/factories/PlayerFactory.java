package com.cpts.game.factories;
import com.cpts.game.actors.Actors;
import com.cpts.game.actors.Player;

public class PlayerFactory implements ActorsFactory {
    @Override
    public Actors spawnCharacter() {
        return new Player();
    }
}
