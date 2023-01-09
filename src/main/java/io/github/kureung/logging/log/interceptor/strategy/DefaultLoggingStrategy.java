package io.github.kureung.logging.log.interceptor.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kureung.logging.log.interceptor.model.BodyFormatter;
import io.github.kureung.logging.log.interceptor.model.CustomLog;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultLoggingStrategy implements LoggingStrategy {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final CustomLog customLog = new CustomLog(request, response);
        log.info("{}", customLog.log(bodyFormatter()));
    }

    private BodyFormatter bodyFormatter() {
        return new BodyFormatter(objectMapper);
    }
}
