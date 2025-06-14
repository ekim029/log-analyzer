package com.example.loganalyzer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDateTime timestamp;
    private String level;      // ERROR, INFO, etc.
    private String serverName;
    private String message;
    private String batchId;

    // Constructors
    public LogEntry() {}

    public LogEntry(LocalDateTime timestamp, String level, String serverName, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.serverName = serverName;
        this.message = message;
    }

    public LogEntry(LocalDateTime timestamp, String level, String serverName, String message, String batchId) {
        this.timestamp = timestamp;
        this.level = level;
        this.serverName = serverName;
        this.message = message;
        this.batchId = batchId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getLevel() {
        return level;
    }
    public String getServerName() {
        return serverName;
    }
    public String getMessage() {
        return message;
    }
    public String getBatchId() {
        return this.batchId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
