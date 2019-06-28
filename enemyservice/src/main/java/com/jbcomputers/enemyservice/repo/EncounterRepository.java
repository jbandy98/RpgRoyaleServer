package com.jbcomputers.enemyservice.repo;

import com.jbcomputers.enemyservice.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Integer> {

    @Query("delete from Encounter e where e.encounterId = :#{#id}")
    void deleteByIdAndGameId(@Param("id") Integer id);

    @Query("select count(e) from Encounter e where e.gameId = :#{#gameId}")
    Integer getEncounterCount(@Param("gameId") Integer gameId);

    @Query("select count(e) from Encounter e where e.gameId = :#{#gameId} and e.zone = :#{#zone}")
    Integer getEncounterCountByZone(@Param("gameId") Integer gameId, @Param("zone") Integer zone);

    @Query("select e from Encounter e where e.gameId = :#{#gameId}")
    List<Encounter> findAllByGameId(@Param("gameId") Integer gameId);

    Encounter findByGameIdAndEncounterId(Integer gameId, Integer encounterId);
}