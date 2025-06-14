package com.example.loganalyzer.repository;

import com.example.loganalyzer.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByBatchIdAndLevel(String batchId, String level);
}
