package com.example.loganalyzer.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/batch")
public class LogJobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job logProcessingJob;

    @PostMapping("/upload")
    public String runJob(@RequestParam("file") MultipartFile file) {
        try {
            File fileDir = new File(System.getProperty("user.dir"),"logs");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File tempFile = new File(fileDir, file.getOriginalFilename());
            file.transferTo(tempFile);

            JobParameters params = new JobParametersBuilder()
                    .addString("filepath", tempFile.getAbsolutePath())
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execute = jobLauncher.run(logProcessingJob, params);
            if (execute.getStatus() == BatchStatus.COMPLETED) {
                tempFile.delete();
            }
            return "Batch started";
        } catch (Exception e) {
            return "Batch failed: " + e.getMessage();
        }
    }

}
