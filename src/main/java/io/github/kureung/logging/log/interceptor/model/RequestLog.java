package io.github.kureung.logging.log.interceptor.model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
class RequestLog {
    private final String url;
    private final String method;
    private final byte[] body;
    private final Map<String, String> headers;

    public RequestLog(final HttpServletRequest request) {
        this((ContentCachingRequestWrapper) request);
    }

    public RequestLog(final ContentCachingRequestWrapper request) {
        this(requestUrl(request), request.getMethod(), requestBody(request), requestHeaders(request));
    }

    private static String requestUrl(final ContentCachingRequestWrapper request) {
        if (request.getQueryString() == null) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL()
                .append("?")
                .append(request.getQueryString())
                .toString();
    }

    private static byte[] requestBody(final ContentCachingRequestWrapper request) {
        if (request.getContentAsByteArray() == null) {
            return new byte[]{};
        }
        return request.getContentAsByteArray();
    }

    private static Map<String, String> requestHeaders(final ContentCachingRequestWrapper request) {
        final Map<String, String> result = new HashMap<>();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            result.put(headerName, request.getHeader(headerName));
        }
        return result;
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public String body(final BodyFormatter bodyFormatter) throws IOException {
        return bodyFormatter.convertedString(body);
    }

    public Map<String, String> headers() {
        return new HashMap<>(headers);
    }
}
