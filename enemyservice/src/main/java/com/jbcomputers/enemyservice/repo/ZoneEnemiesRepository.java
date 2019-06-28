package com.jbcomputers.enemyservice.repo;

import com.jbcomputers.enemyservice.entity.ZoneEnemies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneEnemiesRepository extends JpaRepository<ZoneEnemies, Integer> {

    @Query("select e from ZoneEnemies e where e.worldId = :#{#world} and e.zone = :#{#zone}")
    ZoneEnemies findZoneData(@Param("world") int world, @Param("zone") int zone);

    List<ZoneEnemies> findAllByWorldId(Integer worldId);
}
