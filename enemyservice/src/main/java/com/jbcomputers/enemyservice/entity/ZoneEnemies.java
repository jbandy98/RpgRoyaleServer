package com.jbcomputers.enemyservice.entity;

import javax.persistence.*;

/*
 * This table will have one entry per zone in a world. So about 5-10 entries per world in table.
 */

@Entity
@Table(name="zone_enemies")
public class ZoneEnemies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="zone_enemy_id")
    public Integer zoneEnemyId;

    @Column(name="world_id")
    public Integer worldId;

    // zones are ordered from 1 on the outside in
    @Column()
    public Integer zone;

    // percentage size of this zone overall
    @Column(name="start_percent")
    public Integer startPercent;

    @Column(name="end_percent")
    public Integer endPercent;

    // comma delimited collection of enemy ids for this zone
    @Column(name="enemy_ids")
    public String enemyIds;

}
