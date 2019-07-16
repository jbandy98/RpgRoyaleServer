package com.rpgroyale.combatserver;

import com.rpgroyale.combatserver.entities.LootData;
import com.rpgroyale.combatserver.messagedata.CombatData;
import com.rpgroyale.combatserver.server.CombatLoop;
import com.rpgroyale.combatserver.services.LootService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/combat")
public class CombatController {

    @Autowired
    LootService lootService;

    @GetMapping(value="/startCombat/{player}/{gameId}/{encounterId}")
    public ResponseEntity<CombatData> startCombat(@PathVariable String player, @PathVariable Integer gameId, @PathVariable Integer encounterId) {
        log.info("Creating new combat for player " + player + "...");
        CombatLoop combatLoop = new CombatLoop(player,gameId,encounterId);
        combatLoop.initCombat();
        CombatData combatData = combatLoop.generateMessageData();
        combatLoop.start();

        return new ResponseEntity<>(combatData, HttpStatus.OK);
    }

    @GetMapping(value="/endCombat/{player}/{gameId}/{encounterId}")
    public ResponseEntity<LootData> endCombat(@PathVariable String player, @PathVariable Integer gameId, @PathVariable Integer encounterId) {
        log.info("Ending combat and passing combat results / loot to player " + player);
        LootData lootData = lootService.getCombatResults(player, gameId, encounterId);

        return new ResponseEntity<>(lootData, HttpStatus.OK);
    }

}
