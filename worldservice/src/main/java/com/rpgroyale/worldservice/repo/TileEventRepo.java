package com.rpgroyale.worldservice.repo;

import com.rpgroyale.worldservice.entity.TileEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TileEventRepo extends CrudRepository<TileEvent, String> {
    public TileEvent findByGameIdAndXAndY(int gameId, int x, int y);
    public List<TileEvent> findAllByGameId(int gameId);
}
