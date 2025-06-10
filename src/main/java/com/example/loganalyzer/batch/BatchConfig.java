package com.example.loganalyzer.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job logProcessingJob(JobRepository jobRepository, Step logFileStep) {
        return new JobBuilder("logProcessingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(logFileStep)
                .build();
    }

}
