package com.spring_batch.config;


import com.spring_batch.entity.FinanceSector;
import com.spring_batch.repository.FinanceSectorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hpsf.Array;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.batch.item.file.transform.DelimitedLineTokenizer.DELIMITER_COMMA;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {


    private final FinanceSectorRepository financeSectorRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;


    @Bean
    public BatchItemProcessor processor() {
        System.out.println(" ---BATCH ITEM PROCESSOR --- ");
        return new BatchItemProcessor();
    }

    //    For import data form Excel/CSV to Mysql
    public ItemReader<FinanceSector> reader(MultipartFile multipartFile) {
        System.out.println("FILE NAME : " + multipartFile.getOriginalFilename());
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty()) {
            if (multipartFile.getOriginalFilename().endsWith(".xlsx") || multipartFile.getOriginalFilename().endsWith(".xls")) {
                System.out.println(" --- Excel FILE READ --- ");
                PoiItemReader<FinanceSector> reader = new PoiItemReader<>();
                reader.setResource(multipartFile.getResource());
                reader.setRowMapper(excelRowMapper());
                reader.setLinesToSkip(1);
                return reader;
            } else if (multipartFile.getOriginalFilename().endsWith(".csv")) {
                System.out.println(" --- CSV FILE READ --- ");
                FlatFileItemReader<FinanceSector> reader = new FlatFileItemReader<>();
                reader.setResource(multipartFile.getResource());
                reader.setLineMapper(getLineMapper());
                reader.setLinesToSkip(1);
                return reader;
            }
        }
        return null;
    }


    public RowMapper<FinanceSector> excelRowMapper() {
        System.out.println("--- EXCEL ROW MAPPER --- ");

        return new RowMapper<FinanceSector>() {
            @Override
            public FinanceSector mapRow(RowSet rowSet) throws Exception {
                FinanceSector financeSector = new FinanceSector();
                financeSector.setStorage(rowSet.getCurrentRow()[0]);
                financeSector.setOrganization(rowSet.getCurrentRow()[1]);
                return financeSector;
            }
        };
    }

    private LineMapper<FinanceSector> getLineMapper() {
        System.out.println("--- CSV LINE MAPPER --- ");
        DefaultLineMapper<FinanceSector> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setDelimiter(DELIMITER_COMMA);
        delimitedLineTokenizer.setNames("storage", "organization");

        BeanWrapperFieldSetMapper<FinanceSector> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(FinanceSector.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }


    @Bean
    public RepositoryItemWriter<FinanceSector> batchWriter() {
        System.out.println(" --- BATCH WRITER PROCESS --- ");
        RepositoryItemWriter<FinanceSector> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(financeSectorRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }


    public Job importDataJob(MultipartFile multipartFile) {
        System.out.println(" --- IMPORT DATA JOB --- ");
        return new JobBuilder("importDataJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobExecutionListenerStatus())
                .flow(step1(multipartFile))
                .end()
                .build();
    }

    public Step step1(MultipartFile multipartFile) {
        System.out.println(" --- STEP BUILDER --- ");
        return new StepBuilder("step1", jobRepository)
                .<FinanceSector, FinanceSector>chunk(10, platformTransactionManager)
                .reader(reader(multipartFile))
                .processor(processor())
                .writer(batchWriter())
                .build();
    }

    // For Data write from DB to CSV

    @Bean
    public RepositoryItemReader<FinanceSector> jpaCursorItemReader() {
        System.out.println(" --- JPA CURSOR ITEM READER --- ");
        RepositoryItemReader<FinanceSector> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(financeSectorRepository);
        repositoryItemReader.setMethodName("findAll");
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        repositoryItemReader.setSort(sorts);
        return repositoryItemReader;
    }

    public FlatFileItemWriter<FinanceSector> writer(String fileName) {
        System.out.println(" --- FLAT FILE ITEM WRITER --- " + fileName);
        FlatFileItemWriter<FinanceSector> writer = new FlatFileItemWriter<FinanceSector>();
        writer.setResource(new FileSystemResource("./src/main/resources/" + fileName));
        writer.setLineAggregator(getDelimitedLineAggregator());
        writer.setHeaderCallback(new HeaderWriteImplementation("Id, storage, organization"));
        return writer;
    }


    private DelimitedLineAggregator<FinanceSector> getDelimitedLineAggregator() {
        System.out.println(" --- DELIMITED LINE AGGREGATOR --- ");
        BeanWrapperFieldExtractor<FinanceSector> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<FinanceSector>();
        beanWrapperFieldExtractor.setNames(new String[]{"id", "storage", "organization"});
        DelimitedLineAggregator<FinanceSector> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(beanWrapperFieldExtractor);
        return aggregator;
    }

    public Job exportDataJob(String fileName) throws IOException {
        System.out.println(" --- EXPORT DATA JOB --- ");
        return new JobBuilder("exportDataJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobExecutionListenerStatus())
                .flow(exportStep(fileName))
                .end()
                .build();
    }

    public Step exportStep(String fileName) throws IOException {
        System.out.println(" --- EXPORT STEP BUILDER --- ");
        return new StepBuilder("exportStep", jobRepository)
                .<FinanceSector, FinanceSector>chunk(1, platformTransactionManager)
                .reader(jpaCursorItemReader())
                .processor(processor())
                .writer(writer(fileName))
                .build();
    }


}
