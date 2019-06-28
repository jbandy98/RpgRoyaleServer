package com.rpgroyale.heroservice.clients;

import com.rpgroyale.heroservice.entity.Job;
import com.rpgroyale.heroservice.entity.JobLevel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="jobservice", path="/jobs")
public interface JobClient {

    @GetMapping("/{job}")
    Job getJobInfo(@PathVariable("job") String job);

    @GetMapping("/{jobName}/{level}")
    JobLevel getJobLevelInfo(@PathVariable("jobName") String jobName, @PathVariable("level") int level);
}
