package com.example.loganalyzer.batch;

import com.example.loganalyzer.model.LogEntry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class LogEntryProcessor implements ItemProcessor<LogEntry, LogEntry> {

    @Value("#{jobParameters['batchId']}")
    private String batchId;

    @Override
    public LogEntry process(LogEntry entry) throws Exception {
        entry.setLevel(entry.getLevel().trim().toUpperCase());
        entry.setMessage(entry.getMessage().trim());

        String[] names = entry.getServerName().split("\\.");
        String serverName = names[names.length - 1];
        entry.setServerName(serverName);
        entry.setBatchId(batchId);

        return entry;
    }
}
