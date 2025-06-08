package com.example.loganalyzer.batch;

import com.example.loganalyzer.model.LogEntry;
import org.springframework.batch.item.file.LineMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogLineMapper implements LineMapper<LogEntry> {

    @Override
    public LogEntry mapLine(String line, int LineNumber) {
        try {
            String[] parts = line.split(",", 4);
            if (parts.length != 4) {
                return null;
            }

            LocalDateTime timestamp = LocalDateTime.parse(parts[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String level = parts[1].trim();
            String serverName = parts[2].trim();
            String message = parts[3].trim();

            return new LogEntry(timestamp, level, serverName, message);

        } catch (Exception e) {
            return null;
        }
    }
}
