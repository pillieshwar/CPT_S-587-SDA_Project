package com.cpts.game.command;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public abstract class CommandComponent { //Command Interface and CompositeComponent
    
    
    float upLimit, downLimit, rightLimit, leftLimit;
    Rectangle boundingBox;
    Moveable moveableEntity;
     
    CommandComponent(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox){
        this.moveableEntity = moveableEntity;
        this.upLimit = upLimit;
        this.downLimit = downLimit;
        this.rightLimit = rightLimit;
        this.leftLimit = leftLimit;
        this.boundingBox = boundingBox;
    }
    // add the constructor from Invoker 
    // make changes in execute and in the invoker

    

    public abstract void execute(float deltaTime);
}
