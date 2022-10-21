package com.cpts.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.cpts.game.PowerPuffGirls;

public abstract class Enemy extends Actors {
    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0;
    float directionChangeFrequency = 0.75f;
    public int shield;

    public Enemy(float xCentre, float yCentre, float width, float height, float timeBetweenShots, int shield, float movementSpeed, String movement) {
        super(xCentre, yCentre, width, height, timeBetweenShots, movementSpeed, movement);
        directionVector = new Vector2(10, -10);
        this.shield = shield;
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = PowerPuffGirls.random.nextDouble() * 6.283185;
        directionVector.x = (float) Math.sin(bearing);
        directionVector.y = (float) Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }


    // Only applies to player
    @Override
    public void decrementLives() {
    }

    @Override
    public int getLives() {
        // TODO Auto-generated method stub
        return 0;
    }

    public abstract boolean hitAndCheckDestroyed();
}
