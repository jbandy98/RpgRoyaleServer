package com.jbcomputers.enemyservice.services;

import com.jbcomputers.enemyservice.clients.GameDataClient;
import com.jbcomputers.enemyservice.clients.WorldClient;
import com.jbcomputers.enemyservice.entity.*;
import com.jbcomputers.enemyservice.repo.EncounterRepository;
import com.jbcomputers.enemyservice.repo.EnemyRefRepository;
import com.jbcomputers.enemyservice.repo.EnemyRepository;
import com.jbcomputers.enemyservice.repo.ZoneEnemiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class GameEnemyManager {

    @Autowired
    ZoneEnemiesRepository zoneEnemiesRepository;

    @Autowired
    EnemyRefRepository enemyRefRepository;

    @Autowired
    EnemyRepository enemyRepository;

    @Autowired
    EncounterRepository encounterRepository;

    @Autowired
    WorldClient worldClient;

    @Autowired
    GameDataClient gameDataClient;

    int gameEncountersToManage;
    List<Encounter> encounters;

    public GameEnemyManager(int encounters) {
        this.gameEncountersToManage = encounters;
        this.encounters = new ArrayList<Encounter>();
    }

    public GameEnemyManager() {
        this.gameEncountersToManage = 100;
        this.encounters = new ArrayList<Encounter>();
    }

    public Encounter getGameEncounter(Integer gameId, Integer encounterId) {
        Encounter encounter = encounterRepository.findByGameIdAndEncounterId(gameId, encounterId);
        return encounter;
    }

    public List<Encounter> getGameEncounterList(Integer gameId) {
        List<Encounter> encounterList = encounterRepository.findAllByGameId(gameId);
        return encounterList;
    }

    public List<Encounter> updateEncounterList(int gameId) {
        log.info("updating encounters for game: " + gameId);
        // hardcoded world for now
        Random rand = new Random();

        // get the current list of encounters so we can add to it
        List<Encounter> encounterList = encounterRepository.findAllByGameId(gameId);

        int world = 1;
        int currentEncounterCount = encounterRepository.getEncounterCount(gameId);
        int encountersToAdd = this.gameEncountersToManage - currentEncounterCount;

        WorldInfo worldInfo = worldClient.getWorldInfoForGame(gameId);
        int xSize = worldInfo.getWidth();
        int ySize = worldInfo.getHeight();

        // get encounter counts per zone
        List<ZoneEnemies> zoneEnemies = zoneEnemiesRepository.findAllByWorldId(world);
        List<ZoneCount> zoneCounts = new ArrayList<>();
        for(ZoneEnemies zoneEnemy: zoneEnemies) {
            ZoneCount zoneCount = new ZoneCount();
            zoneCount.zone = zoneEnemy.zone;
            zoneCount.encounters = encounterRepository.getEncounterCountByZone(gameId, zoneEnemy.zone);
            zoneCounts.add(zoneCount);
        }

        for(int n = 0; n <= encountersToAdd; n++) {
            // pick the zone with the least encounters
            Collections.sort(zoneCounts);
            // calculate zone boundaries
            // TODO: current algorithm doesn't work - bottom left is easiest, top right is hardest
            int zoneMinX = (int)(xSize * getZoneEnemyFromZoneId(zoneEnemies, zoneCounts.get(0).zone).startPercent * .01);
            int zoneMaxX = (int)(xSize * getZoneEnemyFromZoneId(zoneEnemies, zoneCounts.get(0).zone).endPercent * .01);
            int zoneMinY = (int)(ySize * getZoneEnemyFromZoneId(zoneEnemies, zoneCounts.get(0).zone).startPercent * .01);
            int zoneMaxY = (int)(ySize * getZoneEnemyFromZoneId(zoneEnemies, zoneCounts.get(0).zone).endPercent * .01);

            // calculate a random location in the zone to spawn the encounter at
            int spawnX = rand.nextInt(zoneMaxX - zoneMinX + 1) + zoneMinX;
            int spawnY = rand.nextInt(zoneMaxY - zoneMinY + 1) + zoneMinY;

            Encounter newEncounter = generateEncounter(gameId, spawnX, spawnY, zoneCounts.get(0).zone, world);

            // save encounter to db
            encounterRepository.save(newEncounter);

            // need to assign the encounter id to the enemies in the encounter,
            // now that we have it from the save to the db
            newEncounter = assignEncounterIdToEnemies(newEncounter);
            encounterRepository.save(newEncounter);

            encounterList.add(newEncounter);

            // increment zone count for next iteration
            zoneCounts.get(0).encounters++;
        }

        return encounterList;
    }

    ZoneEnemies getZoneEnemyFromZoneId(List<ZoneEnemies> zoneEnemies, Integer zone) {
        for(ZoneEnemies zoneEnemy: zoneEnemies) {
            if(zoneEnemy.zone == zone) {
                return zoneEnemy;
            }
        }
        return null;
    }

    public void removeEncounterFromGame(int encounterId) {
        log.info("deleting encounter from game: " + encounterId);
        encounterRepository.deleteByIdAndGameId(encounterId);
    }


    public Encounter generateEncounter(int gameId, int x, int y, int zone, int world) {
        log.info("Generating encounter for game " + gameId + " at " + x + " , " + y);
        Encounter encounter = new Encounter();
        encounter.gameId = gameId;
        encounter.xLoc = x;
        encounter.yLoc = y;
        encounter.zone = zone;
        ZoneEnemies zoneData = zoneEnemiesRepository.findZoneData(world,zone);
        List<Integer> enemyIdsToUse = parseEnemyIds(zoneData);
        List<EnemyRef> enemiesToPickFrom = new ArrayList<>();
        for(Integer enemyId: enemyIdsToUse) {
            EnemyRef enemyRef = enemyRefRepository.getEnemyRefById(enemyId);
            enemiesToPickFrom.add(enemyRef);
        }

        Random rand = new Random();
        int numberOfEnemies = rand.nextInt(5)+1;
        List<Enemy> encounterEnemies = new ArrayList<>();
        for(int n = 0; n <= numberOfEnemies; n++) {
            int choice = rand.nextInt(enemiesToPickFrom.size());
            EnemyRef chosenEnemyRef = enemiesToPickFrom.get(choice);
            Enemy chosenEnemy = convertEnemyRefToEnemy(chosenEnemyRef, gameId);

            enemyRepository.save(chosenEnemy);

            encounterEnemies.add(chosenEnemy);
        }

        encounter.enemies = encounterEnemies;

        return encounter;
    }

    List<Integer> parseEnemyIds(ZoneEnemies zoneData) {
        String[] values = zoneData.enemyIds.split(",");
        List<Integer> enemyIds = new ArrayList<>();
        for(String value: values) {
            Integer enemyId = Integer.parseInt(value);
            enemyIds.add(enemyId);
        }
        return enemyIds;
    }

    Enemy convertEnemyRefToEnemy(EnemyRef enemyRef, int gameId) {
        Enemy enemy = new Enemy();
        enemy.enemyId = enemyRef.enemyId;
        // TODO: need to get encounter id somehow
        enemy.gameId = gameId;
        enemy.enemyName = enemyRef.enemyName;
        enemy.level = enemyRef.level;
        enemy.xpValue = enemyRef.xpValue;
        enemy.gpValue = enemyRef.gpValue;
        enemy.apValue = enemyRef.apValue;
        enemy.strength = enemyRef.strength;
        enemy.dexterity = enemyRef.dexterity;
        enemy.speed = enemyRef.speed;
        enemy.endurance = enemyRef.endurance;
        enemy.spirit = enemyRef.spirit;
        enemy.intelligence = enemyRef.intelligence;
        enemy.willpower = enemyRef.willpower;
        enemy.charisma = enemyRef.charisma;
        enemy.hp = enemyRef.hp;
        enemy.currentHp = enemyRef.hp;
        enemy.sp = enemyRef.sp;
        enemy.currentSp = enemyRef.sp;
        enemy.attackRange = enemyRef.getAttackRange();
        enemy.attackType = enemyRef.getAttackType();

        return enemy;
    }

    Encounter assignEncounterIdToEnemies(Encounter existingEncounter) {
        for(Enemy enemy: existingEncounter.enemies) {
            enemy.setEncounterId(existingEncounter.encounterId);
            enemyRepository.save(enemy);
        }
        return existingEncounter;
    }

    public List<Encounter> moveEncounters(Integer gameId) {
        List<Encounter> encounters = encounterRepository.findAllByGameId(gameId);
        List<GameData> gamePlayers = gameDataClient.getAllGameDataById(gameId);
        log.info("game players objects after pull is: " + gamePlayers.size());
        Random rand = new Random();

        log.info("running through the movement routine for enemies in game " + gameId);
        for(Encounter encounter: encounters) {
            boolean moved = false;
            // if player is close, enemy should chase
            for(GameData gamePlayer: gamePlayers) {

                if (playerDistance(gamePlayer, encounter.xLoc, encounter.yLoc) < 5) {
                    moved = true;
                    log.info("Closing in on player! - Encounter: " + encounter.encounterId);
                    if (gamePlayer.getLocX() > encounter.xLoc) {
                        encounter.xLoc++;
                    } else if (gamePlayer.getLocX() < encounter.xLoc) {
                        encounter.xLoc--;
                    } else if (gamePlayer.getLocY() > encounter.yLoc) {
                        encounter.yLoc++;
                    } else if (gamePlayer.getLocY() < encounter.yLoc) {
                        encounter.yLoc--;
                    }
                }
            }
                if (!moved)
                { // otherwise, player should move randomly
                    int choice = rand.nextInt(5) + 1;
                    switch (choice) {
                        case 1:
                            //move up
                            encounter.yLoc++;
                            break;
                        case 2:
                            //move down
                            encounter.yLoc--;
                            break;
                        case 3:
                            //move left
                            encounter.xLoc--;
                            break;
                        case 4:
                            //move right
                            encounter.xLoc++;
                            break;
                        case 5:
                            //do not move
                            break;
                        default:
                            break;
                    }
                }
                encounterRepository.save(encounter);
            }

        return encounters;
    }

    Double playerDistance(GameData player, int xLoc, int yLoc) {
        double distance = Math.sqrt((xLoc - player.getLocX())*(xLoc - player.getLocX()) + (yLoc - player.getLocY()) * (yLoc - player.getLocY()));
//        log.info("Distance for player: " + player.getLocX() + ", " + player.getLocY() + " enemy loc: " + xLoc + ", " + yLoc + " distance: " + distance);
        return distance;
    }
}
