package com.jbcomputers.enemyservice.entity;

public class ZoneCount implements Comparable<ZoneCount>{
    public int zone;
    public int encounters;

    @Override
    public int compareTo(ZoneCount o) {
        return encounters - o.encounters;
    }
}
