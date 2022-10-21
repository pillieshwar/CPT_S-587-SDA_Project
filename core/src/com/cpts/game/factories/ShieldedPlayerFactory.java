package com.cpts.game.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cpts.game.actors.Actors;
import com.cpts.game.decorators.Shield;

public class ShieldedPlayerFactory {
    TextureRegion shieldTextureRegion;
    Actors player;

    public ShieldedPlayerFactory(TextureRegion textureRegion, Actors player) {
        this.shieldTextureRegion = textureRegion;
        this.player = player;
    }

    public Shield spawnDestroyable() {
        return new Shield(this.shieldTextureRegion, this.player);
    }
}
