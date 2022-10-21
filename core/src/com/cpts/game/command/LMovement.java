package com.cpts.game.command;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.WORLD_WIDTH;

public class LMovement extends CommandComponent {

    ArrayList<CommandComponent> commandList = new ArrayList<CommandComponent>();

    public LMovement(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    }
    public void add(CommandComponent newCommand) {
        commandList.add(newCommand);
    }
    @Override
    public void execute(float deltaTime) {
        // down and right
        if (boundingBox.y >= WORLD_HEIGHT * 2 / 3) {
            commandList.get(0).execute(deltaTime);
            commandList.get(0).execute(deltaTime);
            commandList.get(0).execute(deltaTime);
        } else {
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
        }
        
    }
    
}
