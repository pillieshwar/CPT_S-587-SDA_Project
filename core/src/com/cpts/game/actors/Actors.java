package com.cpts.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cpts.game.command.Moveable;
import com.cpts.game.command.MovementController;
import com.cpts.game.decorators.Destroyable;

public abstract class Actors implements Moveable, Destroyable {
    public Rectangle boundingBox;
    public float movementSpeed;
    public float timeSinceLastShot = 0;
    public float timeBetweenShots;
    public int shield;
    public MovementController movementController;
    public String movement;
    public TextureRegion actorTextureRegion, actorShieldTextureRegion;

    public Actors(float xCentre, float yCentre, float width, float height, float timeBetweenShots, float movementSpeed, String movement) {
        this.movementController = new MovementController();
        this.boundingBox = new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height);
        this.timeBetweenShots = timeBetweenShots;
        this.movementSpeed = movementSpeed;
        this.movement = movement;
    }

    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShots >= 0);
    }

    public abstract void decrementLives();

    public abstract Vector2 getDirectionVector();

    public abstract int getLives();

    @Override
    public void draw(Batch batch) {
        batch.draw(actorTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    @Override
    public void moveUp(float deltaTime, float limit) {
       // boundingBox.setPosition(boundingBox.x + 0f, boundingBox.y + 0.11f);
        boundingBox.setPosition(boundingBox.x + 0f, Math.min(boundingBox.y + this.movementSpeed * deltaTime, limit));
		// this.translate(0f, );
	}
    @Override
	public void moveDown(float deltaTime, float limit) {
        boundingBox.setPosition(boundingBox.x + 0f, Math.min(boundingBox.y - this.movementSpeed * deltaTime, limit));
		// this.translate(0f, Math.max(-this.movementSpeed * deltaTime, limit));	
	}
    @Override
	public void moveLeft(float deltaTime, float limit) {
        boundingBox.setPosition(Math.min(boundingBox.x -this.movementSpeed * deltaTime, limit), boundingBox.y + 0f );
		// this.translate(Math.max(-this.movementSpeed * deltaTime, limit), 0f);
        // playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftLimit), 0f);	
	}
    @Override
	public void moveRight(float deltaTime, float limit) {
        boundingBox.setPosition(Math.min(boundingBox.x + this.movementSpeed * deltaTime, limit), boundingBox.y + 0f );
		// this.translate(Math.min(this.movementSpeed * deltaTime, limit), 0f);	
	}

	public void movement(float deltaTime){

    }

}
