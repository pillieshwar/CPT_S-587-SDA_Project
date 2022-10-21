package com.cpts.game.decorators;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Destroyable {
    public void draw(Batch batch);

    public boolean hitAndCheckDestroyed();
}
