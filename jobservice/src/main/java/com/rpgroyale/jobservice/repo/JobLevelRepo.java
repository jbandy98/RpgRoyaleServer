package com.rpgroyale.jobservice.repo;

import com.rpgroyale.jobservice.entity.JobLevel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobLevelRepo extends CrudRepository<JobLevel, String> {

    public JobLevel findByjobNameAndLevel(String jobName, int level);
    public List<JobLevel> findAll();
}
