package io.github.kureung.logging.log.interceptor.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class BodyFormatter {
    private final ObjectMapper objectMapper;

    public String convertedString(byte[] body) throws IOException {
        return objectMapper.readTree(body).toString();
    }
}
