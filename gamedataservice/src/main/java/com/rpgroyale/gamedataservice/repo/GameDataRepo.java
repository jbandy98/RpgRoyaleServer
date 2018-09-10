package com.rpgroyale.gamedataservice.repo;

import com.rpgroyale.gamedataservice.entity.GameData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDataRepo extends CrudRepository<GameData, String> {

    public GameData findByPlayerIdAndGameId(String playerId, int gameId);
    public List<GameData> findAllByGameId(int gameId);
    public GameData findByPlayerId(String playerId);
}
