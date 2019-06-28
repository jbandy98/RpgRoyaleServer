package com.rpgroyale.combatserver.clients;

import com.rpgroyale.combatserver.entities.Encounter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name="enemyservice", path="/enemy")
public interface EncounterClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}")
    List<Encounter> getAllEncounterDataForGame(@PathVariable("gameId") Integer gameId);

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{encounterId}")
    void deleteEncounterById(@PathVariable("encounterId") Integer encounterId);

    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}/{encounterId}")
    Encounter getEncounterDataForGame(@PathVariable("gameId") Integer gameId, @PathVariable("encounterId") Integer encounterId);

    @RequestMapping(method = RequestMethod.GET, value = "/createEncounters/{gameId}")
    List<Encounter> createEncounters(@PathVariable("gameId") Integer gameId);

    @RequestMapping("/moveEncounters/{gameId}")
    List<Encounter> moveEncounters(@PathVariable("gameId") Integer gameId);

}