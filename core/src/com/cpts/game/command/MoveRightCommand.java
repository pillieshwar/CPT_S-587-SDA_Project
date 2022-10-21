package com.cpts.game.command;

import com.badlogic.gdx.math.Rectangle;

public class MoveRightCommand extends CommandComponent {
    
    
    public MoveRightCommand(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    } 

	@Override
	public void execute(float deltaTime) {
		this.moveableEntity.moveRight(deltaTime, rightLimit);
	}
	    
}
