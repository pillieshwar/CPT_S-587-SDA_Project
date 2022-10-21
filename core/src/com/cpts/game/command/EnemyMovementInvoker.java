package com.cpts.game.command;

import com.badlogic.gdx.math.Rectangle;

import static com.cpts.game.constants.config.WORLD_HEIGHT;
import static com.cpts.game.constants.config.WORLD_WIDTH;

public class EnemyMovementInvoker {
    Moveable moveableEntity;
    float upLimit, downLimit, rightLimit, leftLimit;
    Rectangle boundingBox;
    MoveUpCommand moveUpCommand;
    MoveDownCommand moveDownCommand;
    MoveLeftCommand moveLeftCommand;
    MoveRightCommand moveRightCommand;

    VMovement vMovement;
    RLMovement rlMovement;
    LMovement lMovement;
    WMovement wMovement;

    public EnemyMovementInvoker(Moveable moveableEntity, float upLimit, float downLimit, float rightLimit, float leftLimit, Rectangle boundingBox) {
        this.moveableEntity = moveableEntity;
        this.upLimit = upLimit;
        this.downLimit = downLimit;
        this.rightLimit = rightLimit;
        this.leftLimit = leftLimit;
        this.boundingBox = boundingBox;
        createCommands();

    }

    private void createCommands() {
        moveUpCommand = new MoveUpCommand(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
        moveDownCommand = new MoveDownCommand(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
        moveRightCommand = new MoveRightCommand(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
        moveLeftCommand = new MoveLeftCommand(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);

         vMovement = new VMovement(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
         vMovement.add(moveLeftCommand);
         vMovement.add(moveUpCommand);
         vMovement.add(moveDownCommand);

         rlMovement = new RLMovement(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
         rlMovement.add(moveDownCommand);
         rlMovement.add(moveLeftCommand);

         lMovement = new LMovement(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
         lMovement.add(moveDownCommand);
         lMovement.add(moveRightCommand);

         wMovement = new WMovement(moveableEntity, upLimit, downLimit, rightLimit, leftLimit, boundingBox);
         wMovement.add(moveRightCommand);
         wMovement.add(moveUpCommand);
         wMovement.add(moveDownCommand);
    }


    public void executeLMovement(float deltaTime) {
        // if (boundingBox.y >= WORLD_HEIGHT * 2 / 3) {
        //     moveDownCommand.execute(deltaTime);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        // } else {
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        // }

        lMovement.execute(deltaTime);
    }

    public void executeRLMovement(float deltaTime) {
        // if (boundingBox.y >= WORLD_HEIGHT * 2 / 3) {
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        // } else {
        //     this.moveableEntity.moveLeft(deltaTime, leftLimit);
        //     this.moveableEntity.moveLeft(deltaTime, leftLimit);
        //     this.moveableEntity.moveLeft(deltaTime, leftLimit);
        // }

        rlMovement.execute(deltaTime);
    }

    public void executeWMovement(float deltaTime) {
        // if (boundingBox.x <= WORLD_WIDTH / 4) {
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        // } else if (boundingBox.x >= WORLD_WIDTH / 4 && boundingBox.x <= WORLD_WIDTH / 2) {
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);

        // } else if (boundingBox.x >= WORLD_WIDTH / 2 && boundingBox.x <= WORLD_WIDTH * 3 / 4) {
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        // } else {
        //     this.moveableEntity.moveRight(deltaTime, rightLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);

        // }
        wMovement.execute(deltaTime);
    }

    public void executeVMovement(float deltaTime) {
        // if (boundingBox.x <= WORLD_WIDTH / 2) {
        //     this.moveableEntity.moveLeft(deltaTime, leftLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        //     this.moveableEntity.moveUp(deltaTime, upLimit);
        // } else {
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        //     this.moveableEntity.moveLeft(deltaTime, leftLimit);
        //     this.moveableEntity.moveDown(deltaTime, downLimit);
        // }

        vMovement.execute(deltaTime);
    }

}
