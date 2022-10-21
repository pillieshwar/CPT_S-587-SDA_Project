package com.cpts.game.decorators;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.cpts.game.actors.Actors;


public abstract class PlayerDecorator implements Destroyable{
    public Actors playerShip;

    PlayerDecorator(Actors player){
        this.playerShip = player;
    }

    @Override
    public void draw(Batch batch) {
        playerShip.draw(batch);
    }

    @Override
    public boolean hitAndCheckDestroyed() {
        return playerShip.hitAndCheckDestroyed();
    }
}
