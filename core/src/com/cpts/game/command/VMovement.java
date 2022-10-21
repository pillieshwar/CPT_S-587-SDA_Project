package com.cpts.game.command;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.WORLD_WIDTH;

public class VMovement extends CommandComponent {

    ArrayList<CommandComponent> commandList = new ArrayList<CommandComponent>();
    public void add(CommandComponent newCommand) {
        commandList.add(newCommand);
    }

    public VMovement(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    }

    @Override
    public void execute(float deltaTime) {
        // left, up down 
        if (boundingBox.y >= WORLD_HEIGHT * 2 / 3) {
            commandList.get(0).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
        } else {
            commandList.get(2).execute(deltaTime);
            commandList.get(0).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
        }
    }
    
}

// if (boundingBox.x <= WORLD_WIDTH / 2) {
//     this.moveableEntity.moveLeft(deltaTime, leftLimit);
//     this.moveableEntity.moveUp(deltaTime, upLimit);
//     this.moveableEntity.moveUp(deltaTime, upLimit);
// } else {
//     this.moveableEntity.moveDown(deltaTime, downLimit);
//     this.moveableEntity.moveLeft(deltaTime, leftLimit);
//     this.moveableEntity.moveDown(deltaTime, downLimit);
// }
