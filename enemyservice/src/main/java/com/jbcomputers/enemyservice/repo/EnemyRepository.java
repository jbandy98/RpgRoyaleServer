package com.jbcomputers.enemyservice.repo;

import com.jbcomputers.enemyservice.entity.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<Enemy, Integer> {
}
