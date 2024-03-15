package com.spring_batch.config;

import com.spring_batch.entity.FinanceSector;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;


public class BatchItemProcessor implements ItemProcessor<FinanceSector, FinanceSector> {

    @Override
    public FinanceSector process(@NonNull FinanceSector financeSector) {
        return financeSector;
    }
}
