package com.jbcomputers.enemyservice.clients;

import com.jbcomputers.enemyservice.entity.GameData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="gamedataservice", path="/gamedata")
public interface GameDataClient {

    @RequestMapping(method=RequestMethod.GET, value="/{gameId}")
    List<GameData> getAllGameDataById(@PathVariable("gameId") int gameId);

    @RequestMapping(method=RequestMethod.POST, value="/update")
    GameData updateGameInfo(@RequestBody final GameData gameData);

    @RequestMapping(method=RequestMethod.GET, value="/player/{playerId}")
    GameData getGameDataByPlayerId(@PathVariable("playerId") String playerId);

    @RequestMapping(method=RequestMethod.GET, value="/{gameId}/{playerId}")
    GameData getGameDataByGameIdAndPlayerId(@PathVariable("gameId") int gameId, @PathVariable("playerId") String playerId);
}
