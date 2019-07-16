package com.rpgroyale.combatserver.server;

import com.rpgroyale.combatserver.clients.ClientUtil;
import com.rpgroyale.combatserver.entities.*;
import com.rpgroyale.combatserver.messagedata.CombatData;
import com.rpgroyale.combatserver.messagedata.CombatDataUnit;
import com.rpgroyale.combatserver.utils.PathFindingUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CombatLoop extends Thread {

    private String playerId;
    private Integer gameId;
    private Integer encounterId;
    private PlayerGameData playerData;
    private CombatGrid combatGrid;
    public List<CombatDataUnit> dataUnits;
    private int enemyCount;
    private int heroCount;
    private int gpEarned;
    private int xpEarned;
    private int apEarned;
    private String combatState;
    private PathFindingUtil pathFindingUtil;

    private boolean running;

    public CombatLoop(String playerId, Integer gameId, Integer encounterId) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.encounterId = encounterId;
        combatGrid = new CombatGrid();
        pathFindingUtil = new PathFindingUtil(combatGrid);
    }

    @Override
    public void run(){
        // start combat loop and perform AI commands
        mainCombatLoop();
    }

    public void mainCombatLoop() {
        long initialTime = System.nanoTime();
        int UPS = 1;
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

    public void initCombat() {
        // populate combat grid and unit data
        dataUnits = new ArrayList<>();
        log.info("Instantiated a new combat loop for " + playerId);
        Encounter encounter = ClientUtil.encounterClient.getEncounterDataForGame(gameId,encounterId);
        List<Enemy> enemies = encounter.enemies;
        this.playerData = ClientUtil.gameDataClient.getPlayerGameDataByPlayerId(playerId);
        List<Hero> playerHeroes = ClientUtil.heroClient.getHeroesByPlayer(playerId);
        int startingX = 9;
        int startingY = 3;
        int unitCount = 0;
        for(Hero hero: playerHeroes) {
            unitCount++;
            heroCount++;
            HeroUnit heroUnit = new HeroUnit(startingX,startingY,true,"HIGHEST_THREAT");
            heroUnit.hero = hero;
            heroUnit.setUnitId(unitCount);
            combatGrid.getCombatGridUnits().add(heroUnit);
            startingY--;
        }
        startingX = 0;
        startingY = 0;
        for(Enemy enemy: enemies) {
            unitCount++;
            enemyCount++;
            EnemyUnit enemyUnit = new EnemyUnit(startingX, startingY, false, "HIGHEST_THREAT");
            enemyUnit.enemy = enemy;
            enemyUnit.setUnitId(unitCount);
            combatGrid.getCombatGridUnits().add(enemyUnit);
            startingY++;
            if(startingY > 4) {
                startingY = 0;
                startingX++;
            }
        }
        log.info("Added " + combatGrid.getCombatGridUnits().size() + " units to the combat, including " + heroCount + " heroes and " + enemyCount + " enemies.");
        // initialize threat targets
        for(CombatUnit unit: combatGrid.getCombatGridUnits()) {
            unit.initializeThreatTargets(combatGrid.getCombatGridUnits());
        }

        combatGrid.populateMap();
        combatState = "combat";
    }

    public CombatData generateMessageData() {
        return new CombatData(playerId, gameId, encounterId, combatState, dataUnits);
    }

    private void runCombatRound()  {
        List<CombatUnit> unitsToRemove = new ArrayList<>();
        try {
            for (CombatUnit unit : combatGrid.getCombatGridUnits()) {
                if (!unit.isDead() && unit != null) {
                    unit.chooseTarget();
                    if (unit.currentTarget == -1) {
                        unit.currentTarget = combatGrid.chooseClosestOpponentAsTarget(unit.isHero(), unit.getLocX(), unit.getLocY(), combatGrid.getCombatGridUnits());
                        if (unit.currentTarget == null) {
                            unit.currentTarget = -1;
                        }
                    }
                    if (!unit.isMoving && !unit.isCasting && unit.currentTarget >= 0 && PathFindingUtil.getDistance(unit.getLocX(), unit.getLocY(), getUnitById(unit.getCurrentTarget()).getLocX(), getUnitById(unit.getCurrentTarget()).getLocY()) <= unit.getAttackRange()) {
                        unit.isAttacking = true;
                    }
                    if (unit.isAttacking && !unit.isDead()) {
                        unit.performNormalAttack(getUnitById(unit.getCurrentTarget()));
                        if (getUnitById(unit.getCurrentTarget()).isDead) {
                            unit.isAttacking = false;
                            unitsToRemove.add(getUnitById(unit.getCurrentTarget()));
                            unit.currentTarget = null;
                        }
                    } else if (unit.isMoving) {
                        unit.moveComplete = true;
                        combatGrid.moveUnitTo(unit, unit.getTargetX(), unit.getTargetY());
                    } else if (unit.getCurrentTarget() != null && !unit.isDead() && !unit.isMoving && !unit.isAttacking) {
                        combatGrid.chaseUnit(unit, getUnitById(unit.getCurrentTarget()));
                    }
                }
            }
        } catch (NullPointerException e) {
            // just ignore
            log.error("null pointer encountered during combat loop." + e.getMessage());
        }

        for(CombatUnit unit: unitsToRemove) {
            if(!unit.isHero()) {
                enemyCount--;
                gpEarned += unit.getGpValue();
                xpEarned += unit.getXpValue();
                apEarned += unit.getApValue();
            } else {
                heroCount--;
            }

            for(CombatUnit threatUnit: combatGrid.getCombatGridUnits()) {
                threatUnit.removeUnitFromThreatLevels(unit.getUnitId());
            }
            combatGrid.getCombatGridUnits().remove(unit);
        }

        combatGrid.setCombatGridUnits(combatGrid.getCombatGridUnits());
        combatGrid.populateMap();
        displayMapInLog();

        if (enemyCount <= 0) {
            log.info(playerId + "'s group has defeated the enemies!");
            // divvy out xp and loot
            playerData.setGold(playerData.getGold() + gpEarned);
            log.info("Player data saved.");
            ClientUtil.gameDataClient.updateGameInfo(playerData);
            for(CombatUnit unit: combatGrid.getCombatGridUnits()) {
                if (unit.isHero() && !unit.isDead()) {
                    HeroUnit heroUnit = (HeroUnit)unit;
                    Hero hero = heroUnit.hero;
                    log.info(hero.getHeroName() + "'s hp at end of battle is: " + hero.getCurrentHp());
                    hero.setAp(hero.getAp() + Math.round(apEarned / heroCount));
                    hero.setXp(hero.getXp() + Math.round(xpEarned / heroCount));
                    ClientUtil.heroClient.saveHero(hero);
                }
            }
            log.info("Hero data saved.");
            ClientUtil.encounterClient.deleteEncounterById(encounterId);
            LootData lootData = getCombatResults();
            ClientUtil.lootService.saveCombatResults(lootData);
            combatState = "won";
            log.info("Encounter removed from db and game");
            running = false;
        } else if (heroCount <= 0) {
            log.info(playerId + "'s group has been defeated...");
            combatState = "lost";
            running = false;
        }
        dataUnits.clear();
        for (CombatUnit unit: combatGrid.getCombatGridUnits()) {
            CombatDataUnit dataUnit = new CombatDataUnit(unit);
            dataUnits.add(dataUnit);
        }
        log.info("enemies remaining: " + enemyCount);
        CombatData combatData = generateMessageData();
        log.info("broadcasting combat data to clients...");
        WebSocketHandler.Instance().broadcastCombatData(combatData);

    }

    public CombatUnit getUnitById(Integer unitId) {
        for(CombatUnit unit: combatGrid.getCombatGridUnits()) {
            if(unit.getUnitId() == unitId) {
                return unit;
            }
        }
        return null;
    }

    public LootData getCombatResults() {
        LootData lootData = new LootData();
        lootData.setPlayerId(playerId);
        lootData.setGameId(gameId);
        lootData.setEncounterId(encounterId);
        lootData.setXpEarned(xpEarned);
        lootData.setGpEarned(gpEarned);
        lootData.setApEarned(apEarned);
        return lootData;
    }

    public void displayMapInLog() {
        for (int y = 4; y >= 0; y--) {
            StringBuilder mapLine = new StringBuilder();
            for (int x = 0; x < 10; x++) {
                GridLocation location = combatGrid.getLocationAt(x,y);
                String locInfo;
                if (location.isOccupied) {
                    if (getUnitById(location.unitId).isHero()) {
                        if (getUnitById(location.unitId).isDead()) {
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
