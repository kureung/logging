package io.github.kureung.logging.log.interceptor.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingStrategy {
    void logging(final HttpServletRequest request, final HttpServletResponse response);
}
