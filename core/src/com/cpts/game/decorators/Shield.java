package com.cpts.game.decorators;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.cpts.game.actors.Actors;

public class Shield extends PlayerDecorator{
    TextureRegion playerShieldTextureRegion; 
    int shieldStrength; 

    public Shield(TextureRegion textureRegion, Actors player) {
        super(player);
        this.playerShieldTextureRegion = textureRegion;
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (this.shieldStrength > 0)
            drawShield(batch);
        
    }
    public void drawShield(Batch batch){
        batch.draw(playerShieldTextureRegion, this.playerShip.boundingBox.x, this.playerShip.boundingBox.y, this.playerShip.boundingBox.width, this.playerShip.boundingBox.height);
    }

    public int getShieldStrength() {
        return shieldStrength;
    }

    public void setShieldStrength(int strength) {
        this.shieldStrength = strength;
    }
    @Override
    public boolean hitAndCheckDestroyed() {
        if(this.shieldStrength > 0){
            this.shieldStrength--;
            return false;
        }
        else return super.hitAndCheckDestroyed();
    }
    
}
