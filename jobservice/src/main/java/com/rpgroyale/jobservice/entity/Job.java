package com.rpgroyale.jobservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Job {

    @Id
    private String name;

    @Column
    private String roles;

    @Column
    private String description;

    @Column
    private int max_level;           // current max level defined for this job

    public String getJobName() {
        return name;
    }

    public void setJobName(String jobName) {
        this.name = jobName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxLevel() {
        return max_level;
    }

    public void setMaxLevel(int maxLevel) {
        this.max_level = maxLevel;
    }
}
