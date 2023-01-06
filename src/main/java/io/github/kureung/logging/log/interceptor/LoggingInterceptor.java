package io.github.kureung.logging.log.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kureung.logging.log.interceptor.model.BodyFormatter;
import io.github.kureung.logging.log.interceptor.model.CustomLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class LoggingInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (didRequestPassThroughWrappingFilter(request, response)) {
            CustomLog customLog = new CustomLog(request, response);
            log.info("{}", customLog.log(bodyFormatter()));
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

    private BodyFormatter bodyFormatter() {
        return new BodyFormatter(objectMapper);
    }
}
