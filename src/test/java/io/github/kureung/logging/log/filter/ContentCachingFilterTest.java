package io.github.kureung.logging.log.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentCachingFilterTest {
    private MockFilterChain filterChain;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void beforeEach() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void request_content_caching_Test() throws ServletException, IOException {
        request.addHeader("Content-Type", "application/json");
        new ContentCachingFilter().doFilterInternal(request, response, filterChain);
        assertTrue(filterChain.getRequest() instanceof ContentCachingRequestWrapper);
    }

    @Test
    void response_content_caching_test() throws ServletException, IOException {
        request.addHeader("Content-Type", "application/json");
        new ContentCachingFilter().doFilterInternal(request, response, filterChain);
        assertTrue(filterChain.getResponse() instanceof ContentCachingResponseWrapper);
    }
}
