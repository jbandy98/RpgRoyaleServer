package com.rpgroyale.combatserver.repositories;

import com.rpgroyale.combatserver.entities.LootData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LootDataRepository extends JpaRepository<LootData, Integer> {

    @Query("select l from LootData l where l.playerId = :#{#playerId} and l.gameId = :#{#gameId} and l.encounterId = :#{#encounterId}")
    LootData findLoot(@Param("playerId") String playerId, @Param("gameId") Integer gameId, @Param("encounterId") Integer encounterId);
}
