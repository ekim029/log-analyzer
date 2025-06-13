package com.example.loganalyzer.batch;

import com.example.loganalyzer.model.LogEntry;
import org.springframework.batch.item.ItemProcessor;


public class LogEntryProcessor implements ItemProcessor<LogEntry, LogEntry> {

    @Override
    public LogEntry process(LogEntry entry) throws Exception {
        entry.setLevel(entry.getLevel().trim().toUpperCase());
        entry.setMessage(entry.getMessage().trim());

        String[] names = entry.getServerName().split("\\.");
        String serverName = names[names.length - 1];
        entry.setServerName(serverName);

        return entry;
    }
}
