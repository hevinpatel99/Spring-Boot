package com.spring_batch.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;


@RequiredArgsConstructor
public class HeaderWriteImplementation implements FlatFileHeaderCallback {

    private final String header;

    @Override
    public void writeHeader(@NonNull Writer writer) throws IOException {
        writer.write(header);

    }
}


