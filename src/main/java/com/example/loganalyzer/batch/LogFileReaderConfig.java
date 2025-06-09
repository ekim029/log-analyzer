package com.example.loganalyzer.batch;


import com.example.loganalyzer.model.LogEntry;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class LogFileReaderConfig {

    @Bean
    public FlatFileItemReader<LogEntry> logFileItemReader() {
        return new FlatFileItemReaderBuilder<LogEntry>()
                .name("logFileItemReader")
                .resource(new FileSystemResource("logs/server-log.txt"))
                .lineMapper(new LogLineMapper())
                .build();
    }
}
