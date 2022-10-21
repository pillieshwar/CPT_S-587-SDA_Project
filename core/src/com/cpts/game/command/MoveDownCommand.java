package com.cpts.game.command;

import com.badlogic.gdx.math.Rectangle;

public class MoveDownCommand extends CommandComponent{

	public MoveDownCommand(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    }

	@Override
	public void execute(float deltaTime) {
		// Akshaya to-do constants entries
		this.moveableEntity.moveDown(deltaTime, downLimit);
	}
	
    
}
