package com.cpts.game.command;

import com.badlogic.gdx.math.Rectangle;

public class MoveLeftCommand extends CommandComponent {
    
	public MoveLeftCommand(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    }

	@Override
	public void execute(float deltaTime) {
		this.moveableEntity.moveLeft(deltaTime, leftLimit);
	}
	
    
}
