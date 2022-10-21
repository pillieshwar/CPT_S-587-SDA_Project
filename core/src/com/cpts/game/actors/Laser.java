package com.cpts.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Actors {

    public Laser(float xCentre, float yCentre, float width, float height, float timeBetweenShots, float movementSpeed, TextureRegion tex) {
        super(xCentre, yCentre, width, height, timeBetweenShots, movementSpeed, null);
        actorTextureRegion = tex;
    }

    @Override
    public void decrementLives() {

    }

    @Override
    public Vector2 getDirectionVector() {
        return null;
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public boolean hitAndCheckDestroyed() {
        return false;
    }

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

}
