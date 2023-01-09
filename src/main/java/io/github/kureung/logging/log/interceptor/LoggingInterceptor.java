package io.github.kureung.logging.log.interceptor;

import io.github.kureung.logging.log.interceptor.strategy.LoggingStrategyService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    private final LoggingStrategyService loggingStrategyService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (didRequestPassThroughWrappingFilter(request, response)) {
            loggingStrategyService.strategyExecution(request, response);
        }
    }

    private boolean didRequestPassThroughWrappingFilter(final HttpServletRequest request, final HttpServletResponse response) {
        return isNotSecurity(request) && isCastCashingRequestAndResponse(request, response);
    }

    private boolean isCastCashingRequestAndResponse(final HttpServletRequest request, final HttpServletResponse response) {
        return request instanceof ContentCachingRequestWrapper && response instanceof ContentCachingResponseWrapper;
    }

    private boolean isNotSecurity(final HttpServletRequest request) {
        return !request.getClass()
                .getName()
                .contains("SecurityContextHolderAwareRequestWrapper");
    }
}
