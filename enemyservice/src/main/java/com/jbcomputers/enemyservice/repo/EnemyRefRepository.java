package com.jbcomputers.enemyservice.repo;

import com.jbcomputers.enemyservice.entity.EnemyRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRefRepository extends JpaRepository<EnemyRef, Integer> {

    @Query("select e from EnemyRef e where e.enemyId = :#{#id}")
    EnemyRef getEnemyRefById(@Param("id") Integer id);
}
