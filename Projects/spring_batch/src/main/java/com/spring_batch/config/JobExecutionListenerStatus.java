package com.spring_batch.config;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;


@Component
public class JobExecutionListenerStatus {

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(" --- JOB BEFORE --- ");
        if (jobExecution.getStatus() == BatchStatus.STARTING)
            System.out.println(" JOB STARTED :::  " + jobExecution.getStartTime());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        System.out.println(" --- JOB AFTER --- ");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
            System.out.println("JOB FINISHED :::  " + jobExecution.getEndTime());
    }
}
