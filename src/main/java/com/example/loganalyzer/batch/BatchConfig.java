package com.example.loganalyzer.batch;

import com.example.loganalyzer.model.LogEntry;
import com.example.loganalyzer.repository.LogEntryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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

    @Bean
    public Step logFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            FlatFileItemReader<LogEntry> reader, LogEntryProcessor logEntryProcessor, RepositoryItemWriter<LogEntry> writer) {
        return new StepBuilder("log-file-step", jobRepository)
                .<LogEntry, LogEntry>chunk(100, transactionManager)
                .reader(reader)
                .processor(logEntryProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public LogEntryProcessor logEntryProcessor() {
        return new LogEntryProcessor();
    }

    @Bean
    public RepositoryItemWriter<LogEntry> writer(LogEntryRepository repository) {
        RepositoryItemWriter<LogEntry> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }

}
