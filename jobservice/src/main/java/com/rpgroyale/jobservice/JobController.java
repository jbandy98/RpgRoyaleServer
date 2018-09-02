package com.rpgroyale.jobservice;

import com.rpgroyale.jobservice.entity.Job;
import com.rpgroyale.jobservice.entity.JobLevel;
import com.rpgroyale.jobservice.repo.JobLevelRepo;
import com.rpgroyale.jobservice.repo.JobRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private JobLevelRepo jobLevelRepo;

    @GetMapping()
    public ResponseEntity<List<Job>> getAllJobs() {
        log.info("Getting all jobs.");
        List<Job> allJobs = jobRepo.findAll();
        if (allJobs == null) {
            log.error("Couldn't get list of all jobs.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allJobs, HttpStatus.OK);
    }

    @GetMapping("/{job}")
    public ResponseEntity<Job> getJobInfo(@PathVariable String job) {
        log.info("Providing job information on " + job);
        Job jobObj = jobRepo.findByName(job);
        if (jobObj == null) {
            log.error("Job info returned null.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jobObj, HttpStatus.OK);
    }

    @GetMapping("/{job}/{level}")
    public ResponseEntity<JobLevel> getJobLevelInfo(@PathVariable String job, @PathVariable int level) {
        log.info("Providing job/level information on " + job + " at level " + level);
        JobLevel jobLevel = jobLevelRepo.findByNameAndLevel(job, level);
        if (jobLevel == null) {
            log.error("Job/level info returned null.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jobLevel, HttpStatus.OK);
    }
}
