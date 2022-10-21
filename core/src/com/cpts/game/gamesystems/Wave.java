package com.cpts.game.gamesystems;

public class Wave {
    //Most important part of the game
    int waveId;

    float startTime;
    float endTime;
    int numberOfEnemies;
    float spawnInterval;


    public Wave(float startTime, float endTime, int waveId, int numberOfEnemies, float spawnInterval) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.waveId = waveId;
        this.numberOfEnemies = numberOfEnemies;
        this.spawnInterval = spawnInterval;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public void setNumberOfEnemies(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public int getWaveId() {
        return waveId;
    }

    public void setWaveId(int waveId) {
        this.waveId = waveId;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }

    public void setSpawnInterval(int spawnInterval) {
        this.spawnInterval = spawnInterval;
    }
}
