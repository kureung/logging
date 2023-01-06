package io.github.kureung.logging.log.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentCachingFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String contentType = request.getHeader("Content-Type");
        if (contentType == null || contentType.toLowerCase().contains("multipart/form-data")) {
            filterChain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(wrappingRequest, wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }
}
