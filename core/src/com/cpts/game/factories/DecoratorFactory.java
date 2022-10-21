package com.cpts.game.factories;

import com.cpts.game.decorators.Destroyable;

public interface DecoratorFactory {
    Destroyable spawnDestroyable();
}
