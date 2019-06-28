package com.rpgroyale.combatserver.clients;

import com.rpgroyale.combatserver.entities.PlayerGameData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name="gamedataservice", path="/gamedata")
public interface GameDataClient {

    @RequestMapping(method=RequestMethod.GET, value="/{gameId}")
    List<PlayerGameData> getAllPlayerGameDataById(@PathVariable("gameId") int gameId);

    @RequestMapping(method=RequestMethod.POST, value="/update")
    PlayerGameData updateGameInfo(@RequestBody final PlayerGameData PlayerGameData);

    @RequestMapping(method=RequestMethod.GET, value="/player/{playerId}")
    PlayerGameData getPlayerGameDataByPlayerId(@PathVariable("playerId") String playerId);

    @RequestMapping(method=RequestMethod.GET, value="/{gameId}/{playerId}")
    PlayerGameData getPlayerGameDataByGameIdAndPlayerId(@PathVariable("gameId") int gameId, @PathVariable("playerId") String playerId);
}
