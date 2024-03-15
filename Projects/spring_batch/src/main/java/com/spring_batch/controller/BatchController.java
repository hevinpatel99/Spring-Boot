package com.spring_batch.controller;

import com.spring_batch.response.ApiResponse;
import com.spring_batch.service.BatchService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class BatchController {


    private final BatchService batchService;

    @PostMapping(value = "/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<ApiResponse> insertBatchData(@RequestPart MultipartFile file) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        return this.batchService.insertBatchData(file);
    }

    @GetMapping(value = "/export")
    public ResponseEntity<ApiResponse> exportBatchData(@RequestParam String filename) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, IOException {
        return this.batchService.exportBatchData(filename);
    }

    @GetMapping(value = "/exportCsv")
    public ResponseEntity<ApiResponse> exportCSV(HttpServletResponse response) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, IOException {
        return this.batchService.exportCSV(response);
    }


}
