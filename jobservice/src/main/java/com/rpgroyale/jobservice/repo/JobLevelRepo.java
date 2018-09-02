package com.rpgroyale.jobservice.repo;

import com.rpgroyale.jobservice.entity.JobLevel;
import org.springframework.data.repository.CrudRepository;

public interface JobLevelRepo extends CrudRepository<JobLevel, String> {

    public JobLevel findByNameAndLevel(String name, int level);
}
