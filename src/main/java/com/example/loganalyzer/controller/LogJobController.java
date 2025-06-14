package com.example.loganalyzer.controller;

import com.example.loganalyzer.model.LogEntry;
import com.example.loganalyzer.repository.LogEntryRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batch")
public class LogJobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job logProcessingJob;

    @Autowired
    private LogEntryRepository repository;

    @PostMapping("/upload")
    public ResponseEntity<?> runJob(@RequestParam("file") MultipartFile file) {
        try {
            File fileDir = new File(System.getProperty("user.dir"), "logs");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File tempFile = new File(fileDir, file.getOriginalFilename());
            file.transferTo(tempFile);

            String batchId = String.valueOf(System.currentTimeMillis());

            JobParameters params = new JobParametersBuilder()
                    .addString("filepath", tempFile.getAbsolutePath())
                    .addString("batchId", batchId)
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execute = jobLauncher.run(logProcessingJob, params);

            if (execute.getStatus() == BatchStatus.COMPLETED) {
                tempFile.delete();
            }

            List<LogEntry> entries = repository.findByBatchIdAndLevel(batchId, "ERROR");
            Map<String, Map<String, Object>> response = new HashMap<>();

            for (LogEntry entry : entries) {
                Map<String, Object> fields = new LinkedHashMap<>();
                fields.put("timestamp", entry.getTimestamp());
                fields.put("level", entry.getLevel());
                fields.put("message", entry.getMessage());
                fields.put("serverName", entry.getServerName());
                fields.put("batchId", entry.getBatchId());
                response.put(String.valueOf(entry.getId()), fields);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed: " + e.getMessage());
        }
    }

}
