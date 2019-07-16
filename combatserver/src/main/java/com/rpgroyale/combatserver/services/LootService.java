package com.rpgroyale.combatserver.services;

import com.rpgroyale.combatserver.entities.LootData;
import com.rpgroyale.combatserver.repositories.LootDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LootService {

    @Autowired
    LootDataRepository lootDataRepository;

    public LootData getCombatResults(String playerId, Integer gameId, Integer encounterId) {
        LootData lootData = lootDataRepository.findLoot(playerId, gameId, encounterId);
        return lootData;
    }

    public void saveCombatResults(LootData lootData) {
        lootDataRepository.save(lootData);
    }
}
