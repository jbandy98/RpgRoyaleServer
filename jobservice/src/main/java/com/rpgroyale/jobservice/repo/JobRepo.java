package com.rpgroyale.jobservice.repo;

import com.rpgroyale.jobservice.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepo extends CrudRepository<Job, String> {

    public Job findByName(String name);
    public List<Job> findAll();
}
