package io.github.kureung.logging.log.interceptor;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.github.kureung.logging.log.filter.ContentCachingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingInterceptorTest {
    private Logger logger;
    private MockFilterChain filterChain;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private LoggingInterceptor loggingInterceptor;

    @BeforeEach
    public void beforeEach() throws ServletException, IOException {
        loggingInterceptor = new LoggingInterceptor();
        logger = (Logger) LoggerFactory.getLogger(LoggingInterceptor.class);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        request.addHeader("Content-Type", "application/json");
        new ContentCachingFilter().doFilterInternal(request, response, filterChain);
    }

    @Test
    void logging_test() throws Exception {
        final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        loggingInterceptor.afterCompletion((HttpServletRequest) filterChain.getRequest(), (HttpServletResponse) filterChain.getResponse(), null, null);

        List<ILoggingEvent> logs = listAppender.list;
        assertThat(logs).hasSizeGreaterThan(0);
    }
}
