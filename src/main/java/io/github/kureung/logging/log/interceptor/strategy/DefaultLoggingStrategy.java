package io.github.kureung.logging.log.interceptor.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kureung.logging.log.interceptor.model.BodyFormatter;
import io.github.kureung.logging.log.interceptor.model.CustomLog;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(DefaultLoggingStrategy.class)
public class DefaultLoggingStrategy implements LoggingStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CustomLog customLog = new CustomLog(request, response);
        log.info("{}", customLog.log(bodyFormatter()));
    }

    private BodyFormatter bodyFormatter() {
        return new BodyFormatter(objectMapper);
    }
}
