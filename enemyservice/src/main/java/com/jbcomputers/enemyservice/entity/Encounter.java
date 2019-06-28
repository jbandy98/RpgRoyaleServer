package com.jbcomputers.enemyservice.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="encounter")
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer encounterId;

    @Column(name="game_id")
    public Integer gameId;

    @Column(name="zone")
    public Integer zone;

    @Column(name="x_loc")
    public Integer xLoc;

    @Column(name="y_loc")
    public Integer yLoc;

    @ElementCollection
    public List<Enemy> enemies;
}
