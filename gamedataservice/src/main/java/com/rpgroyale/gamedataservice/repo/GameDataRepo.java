package com.rpgroyale.gamedataservice.repo;

import com.rpgroyale.gamedataservice.entity.PlayerGameData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDataRepo extends CrudRepository<PlayerGameData, String> {

    public PlayerGameData findByPlayerIdAndGameId(String playerId, int gameId);
    public List<PlayerGameData> findAllByGameId(int gameId);
    public PlayerGameData findByPlayerId(String playerId);
}
