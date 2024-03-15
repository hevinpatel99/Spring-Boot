package com.spring_batch.service.serviceImpl;


import com.spring_batch.config.BatchConfig;
import com.spring_batch.entity.FinanceSector;
import com.spring_batch.repository.FinanceSectorRepository;
import com.spring_batch.response.ApiResponse;
import com.spring_batch.service.BatchService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final JobLauncher jobLauncher;


    private final BatchConfig batchConfig;

    private final FinanceSectorRepository financeSectorRepository;

    @Override
    public ResponseEntity<ApiResponse> insertBatchData(MultipartFile file) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        System.out.println(" --- INSERT BATCH DATA --- ");
        batchConfig.reader(file);
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
        JobExecution run = jobLauncher.run(batchConfig.importDataJob(file), jobParameters);
        System.out.println(run.getStatus());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, "INSERT DATA SUCCESSFULLY", run.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponse> exportBatchData(String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, IOException {
        System.out.println(" --- EXPORT BATCH DATA --- ");
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
        JobExecution run = jobLauncher.run(batchConfig.exportDataJob(fileName), jobParameters);
        System.out.println(run.getStatus());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, "EXPORT DATA SUCCESSFULLY", run.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponse> exportCSV(HttpServletResponse response) throws IOException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Sample.csv";
        response.setHeader(headerKey, headerValue);
        PrintWriter writer = response.getWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        List<FinanceSector> financeSectorList = financeSectorRepository.findAll();
        csvPrinter.printRecord("id", "storage", "Organization");
        for (FinanceSector financeSector : financeSectorList) {
            csvPrinter.printRecord(financeSector.getId(), financeSector.getStorage(), financeSector.getOrganization());
        }
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, "EXPORT DATA SUCCESSFULLY", csvPrinter));

    }



}
