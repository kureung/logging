package io.github.kureung.logging.log.interceptor.strategy;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingStrategyService {
    private final List<LoggingStrategy> loggingStrategies;

    public void strategyExecution(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        for (LoggingStrategy loggingStrategy : loggingStrategies) {
            loggingStrategy.execute(request, response);
        }
    }
}
