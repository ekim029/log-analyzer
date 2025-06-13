package com.example.loganalyzer.batch;


import com.example.loganalyzer.model.LogEntry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class LogFileReaderConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<LogEntry> logFileItemReader(@Value("#{jobParameters['filepath']}") String filepath) {
        FlatFileItemReader<LogEntry> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filepath));
        reader.setLineMapper(new LogLineMapper());
        return reader;
    }
}
