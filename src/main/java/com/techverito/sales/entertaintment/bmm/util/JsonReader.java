package com.techverito.sales.entertaintment.bmm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techverito.sales.entertaintment.bmm.exception.InputOutputException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public enum JsonReader {

    JSON_READER;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readJson(File file, Class<T> clazz) {
        try {
            return objectMapper.readValue(file,clazz);
        } catch (IOException e) {
            throw new InputOutputException(e);
        }
    }
}
