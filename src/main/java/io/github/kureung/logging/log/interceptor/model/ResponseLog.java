package io.github.kureung.logging.log.interceptor.model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
class ResponseLog {
    private final byte[] body;
    private final int statusCode;

    public ResponseLog(final HttpServletResponse response) {
        this((ContentCachingResponseWrapper) response);
    }

    public ResponseLog(final ContentCachingResponseWrapper response) {
        this(response.getContentAsByteArray(), response.getStatus());
    }

    private static byte[] responseBody(final ContentCachingResponseWrapper response) {
        if (response.getContentAsByteArray() == null) {
            return new byte[]{};
        }
        return response.getContentAsByteArray();
    }

    public int statusCode() {
        return statusCode;
    }

    public String body(final BodyFormatter bodyFormatter) throws IOException {
        if (body == null) {
            return "";
        }
        return bodyFormatter.convertedString(body);
    }
}
