package com.cpts.game.command;

public class MovementController {
	public void executeMove(CommandComponent command, float deltaTime) {
		command.execute(deltaTime);
	}
}
