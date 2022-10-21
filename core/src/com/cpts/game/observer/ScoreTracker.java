package com.cpts.game.observer;

import com.cpts.game.decorators.Shield;
import com.cpts.game.screens.GameScreen;

public class ScoreTracker {
	private int score, lives, power;
	public Shield player;
	public GameScreen observer;

	public ScoreTracker(Shield player) {
		score = 0;
		lives = 5;
		power = 0;
		this.player = player;
		this.observer = GameScreen.getInstance();

	}

	public void addScore(int update) {
		score += update;
		this.observer = GameScreen.getInstance();
		observer.updateScore();

	}

	public void updateLives(int update) {
		lives += update;
		this.observer = GameScreen.getInstance();
		observer.updateLives();
	}

	public void updatePower(int update) {
		power += update;
		this.observer = GameScreen.getInstance();
		observer.updatePower();

	}

	public int getShieldHealth() {
		return player.getShieldStrength();
	}

	public int getScore() {
		return score;
	}

	public int getLives() {
		return lives;
	}

	public int getPower() {
		return power;
	}

}
