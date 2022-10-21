package com.cpts.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cpts.game.actors.Laser;

public class LaserUtility {

    private Laser laser[];

    public Laser[] getPlayerLasers(Rectangle boundingBox, float laserWidth, float laserHeight, float laserMovementSpeed,
                                   TextureRegion tex) {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.38f, boundingBox.y + boundingBox.height * 0.45f, laserWidth, laserHeight, 0, laserMovementSpeed, tex);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.62f, boundingBox.y + boundingBox.height * 0.45f, laserWidth, laserHeight, 0, laserMovementSpeed, tex);
        return laser;
    }

    public Laser[] getEnemyLasers(Rectangle boundingBox, float laserWidth, float laserHeight, float laserMovementSpeed,
            TextureRegion tex) {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.18f, boundingBox.y - laserHeight, laserWidth, laserHeight, 0, laserMovementSpeed, tex);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.82f, boundingBox.y - laserHeight, laserWidth, laserHeight, 0, laserMovementSpeed, tex);
        return laser;
    }

    public Life getLife(Rectangle boundingBox, float laserWidth, float laserHeight, float laserMovementSpeed,
            TextureRegion tex) {
        Life life =  new Life.LaserBuilder().setxCentre(boundingBox.x + boundingBox.width * 0.18f)
                .setyBottom(boundingBox.y - laserHeight).setWidth(laserWidth)
                .setHeight(laserHeight).setMovementSpeed(laserMovementSpeed)
                .setTextureRegion(tex).builder();
        return life;
    }

}
