package com.rpgroyale.worldservice.repo;

import com.rpgroyale.worldservice.entity.WorldInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldInfoRepo extends CrudRepository<WorldInfo, String> {
    public WorldInfo findByGameId(int gameId);
}
