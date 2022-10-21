package com.cpts.game.command;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.WORLD_WIDTH;

public class WMovement extends CommandComponent {

    ArrayList<CommandComponent> commandList = new ArrayList<CommandComponent>();
    public WMovement(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        super(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
    }

    public void add(CommandComponent newCommand) {
        commandList.add(newCommand);
    }
   
    @Override
    public void execute(float deltaTime) {
        // right, up, down
        if (boundingBox.x <= WORLD_WIDTH / 4) {
            commandList.get(0).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
        } else if (boundingBox.x >= WORLD_WIDTH / 4 && boundingBox.x <= WORLD_WIDTH / 2) {
            commandList.get(0).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);

        } else if (boundingBox.x >= WORLD_WIDTH / 2 && boundingBox.x <= WORLD_WIDTH * 3 / 4) {
            commandList.get(0).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
            commandList.get(2).execute(deltaTime);
            commandList.get(2).execute(deltaTime);

        } else {
            commandList.get(0).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);
            commandList.get(1).execute(deltaTime);

        }
        
    }
    
}
