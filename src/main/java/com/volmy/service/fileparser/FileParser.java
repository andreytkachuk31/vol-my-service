package com.volmy.service.fileparser;

import io.micronaut.http.multipart.CompletedFileUpload;

import java.util.List;

public interface FileParser<T> {

    List<T> parse(CompletedFileUpload file, String importId);
}
