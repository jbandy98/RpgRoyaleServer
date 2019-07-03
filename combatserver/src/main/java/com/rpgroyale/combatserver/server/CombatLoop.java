package com.rpgroyale.combatserver.server;

import com.rpgroyale.combatserver.clients.ClientUtil;
import com.rpgroyale.combatserver.entities.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CombatLoop extends Thread {

    String playerName;
    Integer gameId;
    Integer encounterId;
    PlayerGameData playerData;
    List<Hero> playerHeroes;
    List<Enemy> enemies;
    Encounter encounter;
    List<HeroUnit> heroUnits;
    List<EnemyUnit> enemyUnits;
    List<CombatUnit> allUnits;
    CombatGrid combatGrid;
    List<CombatUnit> unitsToRemove;
    int gpEarned;
    int xpEarned;
    int apEarned;

    int UPS = 1;
    boolean running;

    public CombatLoop(String playerName, Integer gameId, Integer encounterId) {
        this.playerName = playerName;
        this.gameId = gameId;
        this.encounterId = encounterId;
        heroUnits = new ArrayList<>();
        enemyUnits = new ArrayList<>();
        allUnits = new ArrayList<>();
        combatGrid = new CombatGrid();

    }

    @Override
    public void run() {

        // create the websocket connection

        // populate combat grid and unit data
        this.playerName = playerName;
        log.info("Instantiated a new combat loop for " + playerName);
        this.encounter = ClientUtil.encounterClient.getEncounterDataForGame(gameId,encounterId);
        this.enemies = encounter.enemies;
        this.playerData = ClientUtil.gameDataClient.getPlayerGameDataByPlayerId(playerName);
        this.playerHeroes = ClientUtil.heroClient.getHeroesByPlayer(playerName);
        log.info("Encounter location: " + encounter.xLoc + ", " + encounter.yLoc + "\nPlayer info: " + playerData.getPlayerId());
        log.info("Enemies: " + enemies.get(0).getEnemyName());
        log.info("Hero info: " + playerHeroes.get(0).getHeroName() + " hero attack range: " + playerHeroes.get(0).getAttackRange());
        int startingX = 9;
        int startingY = 3;
        for(Hero hero: playerHeroes) {
            HeroUnit heroUnit = new HeroUnit(startingX,startingY,true,AIStrategy.HIGHEST_THREAT);
            heroUnit.hero = hero;
            heroUnits.add(heroUnit);
            allUnits.add(heroUnit);
            startingY--;
        }
        log.info("after adding all heroes, the hero list has " + heroUnits.size() + " items and the all units list has " + allUnits.size());
        startingX = 0;
        startingY = 0;
        for(Enemy enemy: enemies) {
            EnemyUnit enemyUnit = new EnemyUnit(startingX, startingY, false, AIStrategy.HIGHEST_THREAT);
            enemyUnit.enemy = enemy;
            enemyUnits.add(enemyUnit);
            allUnits.add(enemyUnit);
            startingY++;
            if(startingY > 4) {
                startingY = 0;
                startingX++;
            }
        }
        log.info("after adding all enemies, the enemy list has " + enemyUnits.size() + " items and the all units list has " + allUnits.size());

        // initialize threat targets
        for(CombatUnit heroUnit: heroUnits) {
            heroUnit.initializeEnemyThreatTargets(enemyUnits);
        }
        for(CombatUnit enemyUnit: enemyUnits) {
            enemyUnit.initializeHeroThreatTargets(heroUnits);
        }

        combatGrid.setCombatGridUnits(allUnits);
        combatGrid.populateMap();

        // set reference to combat grid on units
        for(CombatUnit heroUnit: heroUnits) {
            heroUnit.setCombatGrid(combatGrid);
        }
        for(CombatUnit enemyUnit: enemyUnits) {
            enemyUnit.setCombatGrid(combatGrid);
        }

        displayMapInLog();

        // start combat loop and perform AI commands
        mainCombatLoop();
        // read in player input through websocket and perform actions

        // on deaths of enemies provide + xp, gp, ap

        // when no enemies left, transition out of combat
    }

    public void mainCombatLoop() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / UPS;
        double deltaU = 0;
        long currentTime;
        running = true;

        while(running) {
            currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            initialTime = currentTime;

            if (deltaU >= 2) {
                deltaU = 0;
                runCombatRound();
            }
        }
    }

    private void runCombatRound() {
        unitsToRemove = new ArrayList<>();
        for(CombatUnit unit: allUnits) {
            unit.chooseTarget();
            unit.tryToStartAttacking(unit.currentTarget);
            if (unit.isAttacking) {
                unit.performNormalAttack(unit.currentTarget);
                if (unit.currentTarget.isDead) {
                    unit.isAttacking = false;
                    unitsToRemove.add(unit.currentTarget);
                    unit.currentTarget = null;
                }
            } else if (unit.isMoving) {
                unit.moveComplete = true;
                unit.moveUnitTo(unit.movingTo);
            } else if (!unit.isMoving && !unit.isAttacking) {
                unit.chaseUnit(unit.currentTarget);
                //log.info(unit.getUnitName() + " is moving towards " + unit.currentTarget.getUnitName());
            }
        }
        for(CombatUnit unit: unitsToRemove) {
            allUnits.remove(unit);
            if(!unit.isHero) {
                gpEarned += unit.getGpValue();
                xpEarned += unit.getXpValue();
                apEarned += unit.getApValue();
                enemyUnits.remove(unit);
                for(CombatUnit heroUnit: heroUnits) {
                    heroUnit.removeUnitFromThreatLevels(unit);
                }
            } else {
                heroUnits.remove(unit);
                for(CombatUnit enemyUnit: enemyUnits) {
                    enemyUnit.removeUnitFromThreatLevels(unit);
                }
            }
        }

        combatGrid.setCombatGridUnits(allUnits);
        combatGrid.populateMap();
        displayMapInLog();
        if (enemyUnits.size() == 0) {
            log.info(playerName + "'s group has defeated the enemies!");
            // divvy out xp and loot
            playerData.setGold(playerData.getGold() + gpEarned);
            log.info("Player data saved.");
            ClientUtil.gameDataClient.updateGameInfo(playerData);
            for(Hero hero: playerHeroes) {
                log.info(hero.getHeroName() + "'s hp at end of battle is: " + hero.getCurrentHp());
                hero.setAp(hero.getAp() + Math.round(apEarned / playerHeroes.size()));
                hero.setXp(hero.getXp() + Math.round(xpEarned / playerHeroes.size()));
                ClientUtil.heroClient.saveHero(hero);
            }
            log.info("Hero data saved.");
            ClientUtil.encounterClient.deleteEncounterById(encounterId);
            log.info("Encounter removed from db and game");
            running = false;
        } else if (heroUnits.size() == 0) {
            log.info(playerName + "'s group has been defeated...");
            running = false;
        }
    }

    public void displayMapInLog() {
        for (int y = 4; y >= 0; y--) {
            StringBuilder mapLine = new StringBuilder();
            for (int x = 0; x < 10; x++) {
                GridLocation location = combatGrid.getLocationAt(x,y);
                String locInfo;
                if (location.isOccupied) {
                    if (location.unit.isHero) {
                        if (location.unit.isDead) {
                            locInfo = "x";
                        } else {
                            locInfo = "H";
                        }
                    } else {
                        locInfo = "E";
                    }
                } else {
                    locInfo = ".";
                }
                mapLine.append(locInfo + " ");
            }
            log.info(mapLine.toString());
        }
    }

}
