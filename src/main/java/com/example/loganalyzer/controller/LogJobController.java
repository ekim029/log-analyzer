package com.example.loganalyzer.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class LogJobController {
    private JobLauncher jobLauncher;
    private Job logProcessingJob;

    @Autowired
    public LogJobController(JobLauncher jobLauncher, Job logProcessingJob) {
        this.jobLauncher = jobLauncher;
        this.logProcessingJob = logProcessingJob;
    }

    @PostMapping("/run")
    public String runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(logProcessingJob, params);
            return "Batch started";
        } catch (Exception e) {
            return "Batch failed: " + e.getMessage();
        }
    }

}
