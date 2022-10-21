package com.cpts.game.gamesystems;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cpts.game.Explosion;
import com.cpts.game.actors.Laser;
import com.cpts.game.LaserUtility;
import com.cpts.game.Life;
import com.cpts.game.actors.Actors;
import com.cpts.game.command.*;
import com.cpts.game.constants.config;
import com.cpts.game.decorators.Shield;
import com.cpts.game.factories.*;
import com.cpts.game.actors.Laser;
import com.cpts.game.constants.config;
import com.cpts.game.factories.PlayerFactory;
import com.cpts.game.factories.RegularEnemyFactory;
import com.cpts.game.factories.ShieldedPlayerFactory;
import com.cpts.game.command.CommandComponent;
import com.cpts.game.command.MoveDownCommand;
import com.cpts.game.command.MoveLeftCommand;
import com.cpts.game.command.MoveRightCommand;
import com.cpts.game.command.MoveUpCommand;
import com.cpts.game.observer.ScoreTracker;
import com.cpts.game.screens.GameOver;

import static com.cpts.game.constants.config.ENEMY_LASER_WIDTH;
import static com.cpts.game.constants.config.PLAYER_LASER_HEIGHT;
import static com.cpts.game.constants.config.PLAYER_LASER_WIDTH;
import static com.cpts.game.constants.config.PLAYER_LASER_MOVEMENT_SPEED;

public class GamePlayTest {
    public Shield shieldedPlayer;
    public LinkedList<Actors> enemies, intervalEnemy;
    public Actors enemy_MidBoss, enemy_FinalBoss;
    public LinkedList<Laser> playerLaserList, enemyLaserList;
    public LinkedList<Explosion> explosionList;
    public LaserUtility laserUtility;
    public LinkedList<Life> lifeList;
    public LinkedList<Life> rewardList;
    
    
    // Textures
    public TextureAtlas textureAtlas;
    public Texture explosionTexture;
    public TextureRegion playerShieldTextureRegion;
    public TextureRegion playerLaserTextureRegion, enemyALaserTextureRegion, enemyBLaserTextureRegion,
            midBLaserTextureRegion, finalBLaserTextureRegion, rewardTextureRegion;
    public Texture lifeTexture;
    public boolean gameState = true;
    

    // Movement Commands
    CommandComponent moveUp, moveDown, moveLeft, moveRight;

    ScoreTracker scoreTracker;

    long endTime = System.currentTimeMillis();
    Life actualLife;
    Queue<Wave> gameTimeLine;
    HashMap<Integer, LinkedList<Actors>> enemyWavesDictionary;
    LinkedList<Actors> enemyWavesList;
    Queue<Float> enemySpawnIntervals;
    HashMap<Actors, JSONObject> enemyWavesLaserDictionary;

    public GamePlayTest() {

        textureAtlas = new TextureAtlas("powerpuff.atlas");

        explosionTexture = new Texture("explosion.png");
        lifeTexture = new Texture("tree-plus.png");
        
        
        laserUtility = new LaserUtility();

        // Set up player
        shieldedPlayer = new ShieldedPlayerFactory(playerShieldTextureRegion, new PlayerFactory().spawnCharacter())
                .spawnDestroyable();

        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyALaserTextureRegion = textureAtlas.findRegion("laserRed03");
        enemyBLaserTextureRegion = textureAtlas.findRegion("laserBlue15");
        midBLaserTextureRegion = textureAtlas.findRegion("laserGreen11");
        finalBLaserTextureRegion = textureAtlas.findRegion("laserGreen14");
        rewardTextureRegion = textureAtlas.findRegion("laserRed12");

       

        // Setup Lasers
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        
       rewardList = new LinkedList<>();

        // Setup Explosions
        explosionList = new LinkedList<>();

        //
        enemyWavesList = new LinkedList<>();
        intervalEnemy = new LinkedList<>();
        enemySpawnIntervals = new LinkedList<>();


        scoreTracker = new ScoreTracker(shieldedPlayer);

        FileReader file = null;
        enemyWavesDictionary = new HashMap<>();
        enemyWavesLaserDictionary = new HashMap<>();
        try {
            file = new FileReader("/Users/eshwar/git/gitlab/teamrushabh/core/assets/JsonDesign.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            JSONArray waves = ((JSONArray) ((JSONObject) jsonObject.get("stage")).get("wave"));
            gameTimeLine = new LinkedList<>();
            for (Object eachWave : waves) {

                int waveId = ((Long) ((JSONObject) eachWave).get("waveId")).intValue();
                int numberOfEnemies = ((Long) ((JSONObject) eachWave).get("numberOfEnemies")).intValue();
                JSONObject timeLine = (JSONObject) ((JSONObject) eachWave).get("time");
                int startTime = ((Long) timeLine.get("startTime")).intValue();
                int endTime = ((Long) timeLine.get("endTime")).intValue();
                float spawnInterval = ((Double) timeLine.get("spawnInterval")).floatValue();
                LinkedList<Actors> waveEnemyShips = new LinkedList<>();
                JSONObject enemyCharacter = (JSONObject) ((JSONObject) eachWave).get("enemyCharacter");
                JSONObject lasers = (JSONObject) ((JSONObject) eachWave).get("enemyLaser");

                for (int i = 0; i < numberOfEnemies; i++) {
                    TextureRegion enemyTextureRegion = textureAtlas.findRegion((String) enemyCharacter.get("enemyTexture"));
                    TextureRegion enemyShieldTextureRegion = textureAtlas.findRegion((String) enemyCharacter.get("shieldTexture"));
                    float enemyWidth = ((Long) enemyCharacter.get("width")).floatValue();
                    float enemyHeight = ((Long) enemyCharacter.get("height")).floatValue();
                    float enemyXStart = ((Long) enemyCharacter.get("xstart")).floatValue();
                    float enemyYStart = ((Long) enemyCharacter.get("ystart")).floatValue();
                    float enemyMovementSpeed = ((Double) enemyCharacter.get("movementSpeed")).floatValue();
                    String enemyMovement = ((String) enemyCharacter.get("movement"));
                    Actors temp = new RegularEnemyFactory().spawnCharacter(enemyTextureRegion, enemyShieldTextureRegion, enemyXStart, enemyYStart, enemyWidth, enemyHeight, enemyMovementSpeed, enemyMovement);
                    waveEnemyShips.add(temp);
                    enemyWavesLaserDictionary.put(temp,lasers);
                    enemySpawnIntervals.add(spawnInterval);
                }

                Wave wave = new Wave(startTime, endTime, waveId, numberOfEnemies, spawnInterval);
                gameTimeLine.add(wave);
                enemyWavesDictionary.put(waveId, waveEnemyShips);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void rewardLife(SpriteBatch batch) {
    	detectCollisions(batch);
       	

        if(rewardList != null) {
        	ListIterator<Life> rewardListIterator = rewardList.listIterator();
        	
        	while(rewardListIterator.hasNext()) {
        		Life rewardDropLife = rewardListIterator.next();
        		rewardDropLife.draw(batch);
        		rewardDropLife.getBoundingBox().y -=  0.5f;
            	
            	if(shieldedPlayer.playerShip.intersects(rewardDropLife.getBoundingBox())) {
            		rewardDropLife.getBoundingBox().y -=  100f;
            		this.scoreTracker.updateLives(1);
            		this.scoreTracker.updatePower(1);
            	}
        	}
        	
        }
    }

    public void render(SpriteBatch batch, float deltaTime) {
        if ((Gdx.input.isKeyPressed(Input.Keys.ESCAPE) ))
        {
            pauseGame();
        }
        if (gameState)
        {
        config.TOTAL_GAME_TIME += deltaTime;
        // Exit Game
        if (scoreTracker.getLives() == 0) {
            //Set a Game over screen
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
        }
        
        rewardLife(batch);
        
        
//        || gameTimeLine.size() == 0

        //Detect Collisions
        detectCollisions(batch);

        //Spawn the Enemy waves
        if (!gameTimeLine.isEmpty() && config.TOTAL_GAME_TIME >= gameTimeLine.peek().getStartTime()) {
            Wave currentwave = gameTimeLine.remove();
            int waveid = currentwave.getWaveId();
            enemyWavesList.addAll(enemyWavesDictionary.get(waveid));
            enemyWavesDictionary.remove(waveid);

        }
        config.ENEMY_SPAWN_TIMER += deltaTime;
        if (!enemySpawnIntervals.isEmpty() && config.ENEMY_SPAWN_TIMER > enemySpawnIntervals.peek()) {
            float interval = enemySpawnIntervals.remove();
            if (!enemyWavesList.isEmpty())
                intervalEnemy.add(enemyWavesList.removeFirst());
            config.ENEMY_SPAWN_TIMER -= interval;
        }

        //Move enemy Ships


        for (Actors enemyShip : intervalEnemy) {
            //Define the movement Pattern using Composite Pattern
            moveEnemy(enemyShip, deltaTime);
            enemyShip.update(deltaTime);
            enemyShip.draw(batch);
        }

        //Detect Input for Players and Move the player ship
        detectInput(deltaTime);
        shieldedPlayer.playerShip.update(deltaTime);
        shieldedPlayer.playerShip.draw(batch);

        //Design the exit pattern later --  Harsh


        //Render Lasers
        renderLasers(batch, deltaTime);

        //Update And render Explosion
        updateAndRenderExplosions(batch, deltaTime);


    }}
    public void pauseGame() {
        if (gameState)
        {
            gameState = false;
        }
        else
        {
            gameState = true;
        }
    }

    private void detectInput(float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = 0.0f + shieldedPlayer.playerShip.boundingBox.x;
        downLimit = 0.0f + shieldedPlayer.playerShip.boundingBox.y;
        rightLimit = config.WORLD_WIDTH
                - shieldedPlayer.playerShip.boundingBox.width;
        upLimit = (float) config.WORLD_HEIGHT / 2
                - shieldedPlayer.playerShip.boundingBox.height;
         // Movement for player
         moveUp = new MoveUpCommand(shieldedPlayer.playerShip, upLimit, downLimit, rightLimit, leftLimit, shieldedPlayer.playerShip.boundingBox);
         moveDown = new MoveDownCommand(shieldedPlayer.playerShip, upLimit, downLimit, rightLimit, leftLimit, shieldedPlayer.playerShip.boundingBox);
         moveLeft = new MoveLeftCommand(shieldedPlayer.playerShip, upLimit, downLimit, rightLimit, leftLimit, shieldedPlayer.playerShip.boundingBox);
         moveRight = new MoveRightCommand(shieldedPlayer.playerShip, upLimit, downLimit, rightLimit, leftLimit, shieldedPlayer.playerShip.boundingBox);
        
         if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(32)) && rightLimit > 0) {
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
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(32)) && rightLimit > 0) {
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
        if ((Gdx.input.isKeyPressed(Input.Keys.B) || Gdx.input.isKeyPressed(30)) ) {
        	if(scoreTracker.getPower()>4) {
        		scoreTracker.updatePower(-scoreTracker.getPower());
        		enemyLaserList.clear();
        	}
        	
//        	tempDeltaTime = deltaTime+0.1f;
//        	shieldedPlayer.playerShip.boundingBox.setSize(50, 50);
        }
    }

    private void moveEnemy(Actors enemyShip, float deltaTime) {
        enemyShip.movement(deltaTime);
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



        for (Actors enemyShip : intervalEnemy) {
            if (enemyShip.canFireLaser()) {
                enemyShip.timeSinceLastShot = 0;
                float laserWidth = ((Double) enemyWavesLaserDictionary.get(enemyShip).get("width")).floatValue();
                float laserHeight = ((Double) enemyWavesLaserDictionary.get(enemyShip).get("height")).floatValue();
                TextureRegion laserTextureRegion = textureAtlas.findRegion((String) enemyWavesLaserDictionary.get(enemyShip).get("color"));

                Laser[] lasers = laserUtility.getEnemyLasers(enemyShip.boundingBox, laserWidth,
                        laserHeight, 60, laserTextureRegion);
                enemyLaserList.addAll(Arrays.asList(lasers));
            }
        }

//        if (enemy_MidBoss.canFireLaser()) {
//            enemy_MidBoss.timeSinceLastShot = 0;
//
//            Laser[] lasers = laserUtility.getEnemyLasers(enemy_MidBoss.boundingBox, enemy_MidBoss.laserWidth,
//                    enemy_MidBoss.laserHeight, enemy_MidBoss.laserMovementSpeed, midBLaserTextureRegion);
//            enemyLaserList.addAll(Arrays.asList(lasers));
//        }

		// Draw the lasers and remove the old lasers for player
		ListIterator<Laser> iterator = playerLaserList.listIterator();
		while (iterator.hasNext()) {
			Laser laser = iterator.next();
			laser.draw(batch);
			laser.getBoundingBox().y += laser.movementSpeed * deltaTime;
			if (laser.getBoundingBox().y > config.WORLD_HEIGHT) {
				iterator.remove();
			}
		}
		// Draw the lasers and remove the old lasers for Enemies
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

	private Life detectCollisions(SpriteBatch batch) {

		// Player hitting enemies
		ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
		while (laserListIterator.hasNext()) {
			Laser laser = laserListIterator.next();
			ListIterator<Actors> enemyShipListIterator = intervalEnemy.listIterator();
//            ListIterator<Life> rewardListIterator = rewardList.listIterator();
			while (enemyShipListIterator.hasNext()) {
				Actors enemyShip = enemyShipListIterator.next();

				if (enemyShip.intersects(laser.getBoundingBox())) {
					// contact with enemy ship
					if (enemyShip.hitAndCheckDestroyed()) {

						// here
						actualLife = new Life.LaserBuilder()
								.setxCentre(enemyShip.boundingBox.x + enemyShip.boundingBox.width * 0.62f)
								.setyBottom(enemyShip.boundingBox.y + enemyShip.boundingBox.height * 0.45f).setWidth(2)
								.setHeight(2).setMovementSpeed(10).setTextureRegion(rewardTextureRegion).builder();
						rewardList.add(actualLife);
						enemyShipListIterator.remove();
						explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));
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
					explosionList.add(new Explosion(explosionTexture,
							new Rectangle(shieldedPlayer.playerShip.boundingBox), 1.6f));
					shieldedPlayer.setShieldStrength(10);
					shieldedPlayer.playerShip.decrementLives();

					enemyLaserList = new LinkedList<>();
					endTime = System.currentTimeMillis() + 5000;
					scoreTracker.updateLives(-1);
//                    return true;
				}
				laserListIterator.remove();
			}
		}

		return actualLife;
	}

	public ScoreTracker getScoreTracker() {
		return this.scoreTracker;
	}

}
