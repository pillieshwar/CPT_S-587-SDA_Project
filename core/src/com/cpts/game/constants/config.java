package com.cpts.game.constants;

public class config {

    public static final float WORLD_WIDTH = 72;
    public static final float WORLD_HEIGHT = 128;

//    Player details
    public static final float PLAYER_HEIGHT = 15;
    public static final float PLAYER_WIDTH = 15;
    public static final float PLAYER_LASER_WIDTH = 1.3f;
    public static final float PLAYER_LASER_HEIGHT = 5;
    public static final float PLAYER_MOVEMENT_SPEED = 45;

    public static final float PLAYER_LASER_MOVEMENT_SPEED = 85;
    public static final float PLAYER_TIME_BETWEEN_SHOTS = 0.5f;


    // Regular enemy details
    public static final float ENEMY_HEIGHT = 10;
    public static final float ENEMY_WIDTH = 10;
    public static final float ENEMY_TIME_BETWEEN_SHOTS = 1.3f;
    public static final float ENEMY_LASER_HEIGHT = 2;
    public static final float ENEMY_LASER_WIDTH = 2f;

    public static final float ENEMY_LASER_MOVEMENT_SPEED = 40;
    public static final float REG_EWNEMY_A_SPEED = 48;
    public static final float REG_EWNEMY_B_SPEED = 68;

    // Mid enemy details
    public static final float MID_ENEMY_HEIGHT = 15;
    public static final float MID_ENEMY_WIDTH = 18;

    public static final float MID_ENEMY_MOVEMENT_SPEED = 60;
    public static final float MID_ENEMY_LASER_SPEED = 53;
    public static final float MID_ENEMY_LASER_HEIGHT = 2;
    public static final float MID_ENEMY_LASER_WIDTH = 1f;
    public static final float MID_ENEMY_TIME_BETWEEN_SLOTS= 0.35f;

    // Final enemy details
    public static final float FINAL_ENEMY_LASER_HEIGHT = 2;
    public static final float FINAL_ENEMY_LASER_WIDTH = 1.5f;
    public static final float FINAL_ENEMY_TIME_BETWEEN_SLOTS= 0.25f;
    public static final float FINAL_ENEMY_MOVEMENT_SPEED = 60;
    public static final float FINAL_ENEMY_LASER_SPEED = 60;

    // Laser Details


    // timing

    public static float TOTAL_GAME_TIME = 0f;
    public static float TIME_BETWEEN_ENEMY_SPAWNS = .80f;
    public static float ENEMY_MID_BOSS_TIMER = 0;
    public static float ENEMY_SPAWN_TIMER = 0;
    public static float ENEMY_FINAL_BOSS_TIMER = 0;
    public static float ENEMY_A_BOSS_TIMER = 0;
    public static float ENEMY_B_BOSS_TIMER = 0;

    public static float ENEMY_A_COUNTER= 10;
    public static int ENEMY_B_COUNTER = 10;

    public static int FLAG_A_BOSS = 0;
    public static int FLAG_B_BOSS = 0;
    public static int FLAG_MID_BOSS = 1;
    public static int FLAG_FINAL_BOSS = 1;

    public static int FLAG_MID_BOSS_KILLED = 0;
    public static int FLAG_FINAL_BOSS_KILLED = 0;


}
