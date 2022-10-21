package com.cpts.game.gamesystems;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cpts.game.Explosion;
import com.cpts.game.LaserUtility;
import com.cpts.game.MenuScreen;
import com.cpts.game.actors.Actors;
import com.cpts.game.command.*;
import com.cpts.game.actors.Laser;
import com.cpts.game.constants.config;
import com.cpts.game.decorators.Shield;
import com.cpts.game.factories.*;
import com.cpts.game.observer.ScoreTracker;
import com.cpts.game.screens.GameOver;

import static com.cpts.game.constants.config.PLAYER_LASER_HEIGHT;
import static com.cpts.game.constants.config.PLAYER_LASER_WIDTH;
import static com.cpts.game.constants.config.PLAYER_LASER_MOVEMENT_SPEED;

import static com.cpts.game.constants.config.ENEMY_LASER_HEIGHT;
import static com.cpts.game.constants.config.ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.ENEMY_LASER_MOVEMENT_SPEED;

import static com.cpts.game.constants.config.MID_ENEMY_LASER_HEIGHT;
import static com.cpts.game.constants.config.MID_ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.MID_ENEMY_LASER_SPEED;

import static com.cpts.game.constants.config.FINAL_ENEMY_LASER_HEIGHT;
import static com.cpts.game.constants.config.FINAL_ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.FINAL_ENEMY_LASER_SPEED;



import java.util.*;

public class GamePlay {
    public Shield shieldedPlayer;
    public LinkedList<Actors> enemies, enemy_A, enemy_B;
    public Actors enemy_MidBoss, enemy_FinalBoss;
    public LinkedList<Laser> playerLaserList, enemyLaserList;
    public LinkedList<Explosion> explosionList;
    public LaserUtility laserUtility;

    // Textures
    public TextureAtlas textureAtlas;
    public Texture explosionTexture;
    public TextureRegion playerShieldTextureRegion;
    public TextureRegion playerLaserTextureRegion, enemyALaserTextureRegion, enemyBLaserTextureRegion,
            midBLaserTextureRegion, finalBLaserTextureRegion;

    // Movement Commands
    CommandComponent moveUp, moveDown, moveLeft, moveRight;

    private ScoreTracker scoreTracker;

    long endTime = System.currentTimeMillis();

    public GamePlay() {
        textureAtlas = new TextureAtlas("powerpuff.atlas");

        explosionTexture = new Texture("explosion.png");
        laserUtility = new LaserUtility();

        // Set up player
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        shieldedPlayer = new ShieldedPlayerFactory(playerShieldTextureRegion, new PlayerFactory().spawnCharacter())
                .spawnDestroyable();

        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyALaserTextureRegion = textureAtlas.findRegion("laserRed03");
        enemyBLaserTextureRegion = textureAtlas.findRegion("laserBlue15");
        midBLaserTextureRegion = textureAtlas.findRegion("laserGreen11");
        finalBLaserTextureRegion = textureAtlas.findRegion("laserRed10");

        // Movement for player
        // moveUp = new MoveUpCommand(shieldedPlayer.playerShip);
        // moveDown = new MoveDownCommand(shieldedPlayer.playerShip);
        // moveLeft = new MoveLeftCommand(shieldedPlayer.playerShip);
        // moveRight = new MoveRightCommand(shieldedPlayer.playerShip);

        // Setup enemies
        enemies = new LinkedList<>();
        enemy_A = new LinkedList<>();
        enemy_B = new LinkedList<>();
        enemy_MidBoss = new MidBossFactory().spawnCharacter();
        enemy_FinalBoss = new FinalBossFactory().spawnCharacter();

        // Setup Lasers
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        // Setup Explosions
        explosionList = new LinkedList<>();

        scoreTracker = new ScoreTracker(shieldedPlayer);
    }

    public void render(SpriteBatch batch, float deltaTime) {
        // Update Score
        if (scoreTracker.getLives() == 0) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
        }
        detectCollisions();


        if (System.currentTimeMillis() < endTime) {
            updateAndRenderExplosions(batch, deltaTime);
            detectInput(deltaTime);
            shieldedPlayer.playerShip.update(deltaTime);
            ListIterator<Actors> enemyShipListIterator = enemy_A.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();
                // oveEnemy(enemyShip, deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.draw(batch);
            }
            ListIterator<Actors> enemyShipListIterator_enemyB = enemy_B.listIterator();
            while (enemyShipListIterator_enemyB.hasNext()) {
                Actors enemyShip = enemyShipListIterator_enemyB.next();
                // moveEnemyB(enemyShip, deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.draw(batch);
            }
            if (config.FLAG_MID_BOSS_KILLED == 0) {
                ListIterator<Actors> bossIterator = enemies.listIterator();
                while (bossIterator.hasNext()) {
                    Actors enemyShip = bossIterator.next();
                    // moveMidBoss(enemyShip, deltaTime);
                    enemyShip.update(deltaTime);
                    enemyShip.draw(batch);
                }
            }

            if (config.FLAG_FINAL_BOSS_KILLED == 0) {
                ListIterator<Actors> bossIterator = enemies.listIterator();
                while (bossIterator.hasNext()) {
                    Actors enemyShip = bossIterator.next();
                    // moveMidBoss(enemyShip, deltaTime);
                    enemyShip.update(deltaTime);
                    enemyShip.draw(batch);
                }
            }

            shieldedPlayer.draw(batch);
            // renderLasers(batch, deltaTime);
            detectCollisionsRespawnPlayer();
        } else {
            detectInput(deltaTime);
            shieldedPlayer.playerShip.update(deltaTime);

            spawnEnemyShips(deltaTime);

            ListIterator<Actors> enemyShipListIterator = enemy_A.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();
                moveEnemy(enemyShip, deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.draw(batch);
            }
            ListIterator<Actors> enemyShipListIterator_enemyB = enemy_B.listIterator();
            while (enemyShipListIterator_enemyB.hasNext()) {
                Actors enemyShip = enemyShipListIterator_enemyB.next();
                moveEnemyB(enemyShip, deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.draw(batch);
            }
            // player ship
            shieldedPlayer.draw(batch);

            if (config.FLAG_MID_BOSS_KILLED == 0) {
                ListIterator<Actors> bossIterator = enemies.listIterator();
                while (bossIterator.hasNext()) {
                    Actors enemyShip = bossIterator.next();
                    moveMidBoss(enemyShip, deltaTime);
                    enemyShip.update(deltaTime);
                    enemyShip.draw(batch);
                }
            }

            if (config.FLAG_FINAL_BOSS_KILLED == 0) {
                ListIterator<Actors> bossIterator = enemies.listIterator();
                while (bossIterator.hasNext()) {
                    Actors enemyShip = bossIterator.next();
                    moveMidBoss(enemyShip, deltaTime);
                    enemyShip.update(deltaTime);
                    enemyShip.draw(batch);
                }
            }

            // if (config.FLAG_MID_BOSS_KILLED ==0) {
            // moveMidBoss(enemy_MidBoss, deltaTime);
            // enemy_MidBoss.draw(batch);
            // }

            // renderLasers(batch, deltaTime);
            detectCollisions();
            updateAndRenderExplosions(batch, deltaTime);

        }

    }

    private void spawnEnemyShips(float deltaTime) {
        config.ENEMY_SPAWN_TIMER += deltaTime;
        config.ENEMY_A_BOSS_TIMER += deltaTime;
        config.ENEMY_B_BOSS_TIMER += deltaTime;
        config.ENEMY_MID_BOSS_TIMER += deltaTime;
        config.ENEMY_FINAL_BOSS_TIMER += deltaTime;

        if (config.ENEMY_SPAWN_TIMER > config.TIME_BETWEEN_ENEMY_SPAWNS) {
            Actors enemyA = new RegularEnemyAFactory().spawnCharacter();
            Actors enemyB = new RegularEnemyBFactory().spawnCharacter();

            if (config.ENEMY_A_COUNTER > 0) {
                enemy_A.add(enemyA);
                config.ENEMY_A_COUNTER = config.ENEMY_A_COUNTER - 1;
            }
            if (config.ENEMY_MID_BOSS_TIMER > 45f && config.ENEMY_B_COUNTER > 0) {
                enemy_B.add(enemyB);
                config.ENEMY_B_COUNTER = config.ENEMY_B_COUNTER - 1;
            }
            config.ENEMY_SPAWN_TIMER -= config.TIME_BETWEEN_ENEMY_SPAWNS;
        }

        if (config.FLAG_MID_BOSS == 0 && config.ENEMY_MID_BOSS_TIMER > 30f && config.FLAG_MID_BOSS_KILLED == 0) {
            // Actors midBoss = new MidBossFactory().spawnCharacter();
            enemies.add(enemy_MidBoss);
            config.FLAG_MID_BOSS = 1;
        }

        if (config.ENEMY_FINAL_BOSS_TIMER > 179f) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
        }

        if (config.FLAG_FINAL_BOSS == 0 && config.ENEMY_FINAL_BOSS_TIMER > 70f && config.FLAG_FINAL_BOSS_KILLED == 0) {
            // Actors final_enemy = new FinalBossFactory().spawnCharacter();
            enemies.add(enemy_FinalBoss);
            config.FLAG_FINAL_BOSS = 1;
        }

    }

    private void detectInput(float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = 0.0f + shieldedPlayer.playerShip.boundingBox.x;
        downLimit = 0.0f + shieldedPlayer.playerShip.boundingBox.y;
        rightLimit = 2*config.WORLD_WIDTH;
        upLimit = (float) config.WORLD_HEIGHT / 1.5f -
                - shieldedPlayer.playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(32))) {
                shieldedPlayer.playerShip.movementController.executeMove(this.moveRight, deltaTime);
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(51)) && upLimit > 0) {
                shieldedPlayer.playerShip.movementController.executeMove(this.moveUp, deltaTime);
            }

            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(29)) && leftLimit > 0) {
                shieldedPlayer.playerShip.movementController.executeMove(this.moveLeft, deltaTime);
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(47)) && downLimit > 0) {
                shieldedPlayer.playerShip.movementController.executeMove(this.moveDown, deltaTime);
            }

        }
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(32))) {
            shieldedPlayer.playerShip.movementController.executeMove(this.moveRight, deltaTime);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(51)) && upLimit > 0) {
            shieldedPlayer.playerShip.movementController.executeMove(this.moveUp, deltaTime);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(29)) && leftLimit > 0) {
            shieldedPlayer.playerShip.movementController.executeMove(this.moveLeft, deltaTime);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(47)) && downLimit > 0) {
            shieldedPlayer.playerShip.movementController.executeMove(this.moveDown, deltaTime);
        }
    }

    private void moveEnemy(Actors enemyShip, float deltaTime) {
        float downLimit;
        downLimit = (float) config.WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        float xMove = 0.0F;
        float yMove = 0.0F;
        if (yMove > downLimit)
            yMove = -.1f;
        else
            xMove = .1f;
        enemyShip.translate(xMove, yMove);
    }

    private void moveEnemyB(Actors enemyShip, float deltaTime) {
        float downLimit;
        downLimit = (float) config.WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        float xMove = 0.0F;
        float yMove = 0.0F;
        if (yMove > downLimit)
            yMove = -.1f;
        else
            xMove = -.1f;
        enemyShip.translate(xMove, yMove);
    }

    private void moveMidBoss(Actors enemyShip, float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float) config.WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        rightLimit = config.WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = config.WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        if (xMove > 0)
            xMove = Math.min(xMove, rightLimit);
        else
            xMove = Math.max(xMove, leftLimit);

        if (yMove > 0)
            yMove = Math.min(yMove, upLimit);
        else
            yMove = Math.max(yMove, downLimit);

        enemyShip.translate(xMove, yMove);
    }

    private void updateAndRenderExplosions(SpriteBatch batch, float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    private void renderLasers(SpriteBatch batch, float deltaTime) {
        if (Gdx.input.isKeyPressed(62) && shieldedPlayer.playerShip.canFireLaser()) {
            shieldedPlayer.playerShip.timeSinceLastShot = 0;
            Laser[] lasers = laserUtility.getPlayerLasers(shieldedPlayer.playerShip.boundingBox,
                    PLAYER_LASER_WIDTH, PLAYER_LASER_HEIGHT,
                    PLAYER_LASER_MOVEMENT_SPEED, playerLaserTextureRegion);
            playerLaserList.addAll(Arrays.asList(lasers));
        }
        // enemy lasers
        ListIterator<Actors> enemyShipListIterator = enemies.listIterator();
        while (enemyShipListIterator.hasNext()) {
            Actors enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                enemyShip.timeSinceLastShot = 0;
                Laser[] lasers = laserUtility.getEnemyLasers(enemyShip.boundingBox, FINAL_ENEMY_LASER_WIDTH,
                        FINAL_ENEMY_LASER_HEIGHT, FINAL_ENEMY_LASER_SPEED, finalBLaserTextureRegion);

                enemyLaserList.addAll(Arrays.asList(lasers));
            }
        }
        ListIterator<Actors> enemyShipListIteratorA = enemy_A.listIterator();
        while (enemyShipListIteratorA.hasNext()) {
            Actors enemyShip = enemyShipListIteratorA.next();
            if (enemyShip.canFireLaser()) {
                enemyShip.timeSinceLastShot = 0;
                Laser[] lasers = laserUtility.getEnemyLasers(enemyShip.boundingBox, ENEMY_LASER_WIDTH,
                        ENEMY_LASER_HEIGHT, ENEMY_LASER_MOVEMENT_SPEED, enemyALaserTextureRegion);

                enemyLaserList.addAll(Arrays.asList(lasers));
            }
        }
        ListIterator<Actors> enemyShipListIteratorB = enemy_B.listIterator();
        while (enemyShipListIteratorB.hasNext()) {
            Actors enemyShip = enemyShipListIteratorB.next();
            if (enemyShip.canFireLaser()) {
                enemyShip.timeSinceLastShot = 0;
                Laser[] lasers = laserUtility.getEnemyLasers(enemyShip.boundingBox, ENEMY_LASER_WIDTH,
                        ENEMY_LASER_HEIGHT, ENEMY_LASER_MOVEMENT_SPEED, enemyBLaserTextureRegion);

                enemyLaserList.addAll(Arrays.asList(lasers));
            }
        }

        if (enemy_MidBoss.canFireLaser()) {
            enemy_MidBoss.timeSinceLastShot = 0;

            Laser[] lasers = laserUtility.getEnemyLasers(enemy_MidBoss.boundingBox, MID_ENEMY_LASER_WIDTH,
                    MID_ENEMY_LASER_HEIGHT, MID_ENEMY_LASER_SPEED, midBLaserTextureRegion);
            enemyLaserList.addAll(Arrays.asList(lasers));
        }

        // draw lasers
        // remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();

        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.getBoundingBox().y += laser.movementSpeed * deltaTime;
            if (laser.getBoundingBox().y > config.WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.getBoundingBox().y -= laser.movementSpeed * deltaTime;
            if (laser.getBoundingBox().y + laser.getBoundingBox().height < 0) {
                iterator.remove();
            }
        }
    }

    private boolean detectCollisions() {

        // Player hitting enemies
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Actors> enemyShipListIterator = enemy_A.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.getBoundingBox())) {
                    // contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {
                        enemyShipListIterator.remove();

                        explosionList.add(
                                new Explosion(explosionTexture,
                                        new Rectangle(enemyShip.boundingBox),
                                        0.7f));
                        scoreTracker.addScore(100);
                    }
                    laserListIterator.remove();
                    break;
                }

            }

            enemyShipListIterator = enemies.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.getBoundingBox())) {
                    laserListIterator.remove();
                    // contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {

                        explosionList.add(
                                new Explosion(explosionTexture,
                                        new Rectangle(enemyShip.boundingBox),
                                        0.7f));
                        scoreTracker.addScore(1000);
                        enemyShipListIterator.remove();

                        config.FLAG_MID_BOSS_KILLED = 1;
                    }

                    break;
                }
            }

            // if (enemy_MidBoss != null &&
            // enemy_MidBoss.intersects(laser.getBoundingBox())) {
            // // contact with enemy ship
            // if (enemy_MidBoss.hitAndCheckDestroyed()) {
            //
            //
            // explosionList.add(
            // new Explosion(explosionTexture,
            // new Rectangle(enemy_MidBoss.boundingBox),
            // 0.7f));
            // scoreTracker.addScore(1000);
            // }
            // enemies.removeFirst();
            // enemy_MidBoss.boundingBox.x += 1000;
            // enemy_MidBoss.boundingBox.y += 1000;
            // config.FLAG_MID_BOSS_KILLED=1;
            //// enemy_MidBoss = null;
            // break;
            // }
            enemyShipListIterator = enemy_B.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.getBoundingBox())) {
                    // contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {
                        enemyShipListIterator.remove();

                        explosionList.add(
                                new Explosion(explosionTexture,
                                        new Rectangle(enemyShip.boundingBox),
                                        0.7f));
                        scoreTracker.addScore(100);
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }

        // for each enemy laser, check whether it intersects the player ship
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (shieldedPlayer.playerShip.intersects(laser.getBoundingBox())) {
                // contact with player ship
                if (shieldedPlayer.hitAndCheckDestroyed()) {
                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(shieldedPlayer.playerShip.boundingBox),
                                    1.6f));
                    shieldedPlayer.setShieldStrength(10);
                    shieldedPlayer.playerShip.decrementLives();

                    enemyLaserList = new LinkedList<>();
                    endTime = System.currentTimeMillis() + 5000;
                    scoreTracker.updateLives(-1);
                    return true;
                }
                laserListIterator.remove();
            }
        }

        return false;
    }

    private void detectCollisionsRespawnPlayer() {

        // for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Actors> enemyShipListIterator = enemy_A.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Actors enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.getBoundingBox())) {
                    // contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {
                        enemyShipListIterator.remove();
                        explosionList.add(
                                new Explosion(explosionTexture,
                                        new Rectangle(enemyShip.boundingBox),
                                        0.7f));
                        scoreTracker.addScore(100);
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }
    }

    public ScoreTracker getScoreTracker() {
        return this.scoreTracker;
    }

}
