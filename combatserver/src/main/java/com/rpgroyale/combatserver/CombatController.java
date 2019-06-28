package com.rpgroyale.combatserver;

import com.rpgroyale.combatserver.server.CombatLoop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/combat")
public class CombatController {

    @GetMapping(value="/startCombat/{player}/{gameId}/{encounterId}")
    public void startCombat(@PathVariable String player, @PathVariable Integer gameId, @PathVariable Integer encounterId) {
        log.info("Creating new combat for player " + player + "...");
        CombatLoop combatLoop = new CombatLoop(player,gameId,encounterId);
        combatLoop.start();
    }

}
