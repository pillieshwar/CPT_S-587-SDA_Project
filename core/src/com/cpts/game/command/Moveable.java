package com.cpts.game.command;

public interface Moveable {
    public void moveUp(float deltaTime, float limit);
    public void moveDown(float deltaTime, float limit);
    public void moveLeft(float deltaTime, float limit);
    public void moveRight(float deltaTime, float limit);
    
}
