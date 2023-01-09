package io.github.kureung.logging.log.interceptor.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kureung.logging.log.filter.ContentCachingFilter;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class DefaultLoggingStrategyTest {
    private Logger logger;
    private MockFilterChain filterChain;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws ServletException, IOException {
        logger = (Logger) LoggerFactory.getLogger(DefaultLoggingStrategy.class);
        filterChain = new MockFilterChain();

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Content-Type", "application/json");
        new ContentCachingFilter().doFilterInternal(mockRequest, new MockHttpServletResponse(), filterChain);
    }

    @Test
    void logging_strategy_test() throws Exception {
        final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        final DefaultLoggingStrategy defaultLoggingStrategy = new DefaultLoggingStrategy(objectMapper);
        defaultLoggingStrategy.execute((HttpServletRequest) filterChain.getRequest(), (HttpServletResponse) filterChain.getResponse());

        List<ILoggingEvent> logs = listAppender.list;
        assertThat(logs).hasSizeGreaterThan(0);
    }
}