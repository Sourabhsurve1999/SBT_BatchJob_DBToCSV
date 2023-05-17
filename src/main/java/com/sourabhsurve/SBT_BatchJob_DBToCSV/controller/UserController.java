package com.sourabhsurve.SBT_BatchJob_DBToCSV.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("exportPersonJob")
    private Job job;


    @GetMapping("/export")
    public ResponseEntity<String> exportPersonJob() throws JobExecutionException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        return ResponseEntity.ok("Data exported.....");


    }
}



