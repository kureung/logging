package io.github.kureung.logging.log.interceptor.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLog {
    private final RequestLog requestLog;
    private final ResponseLog responseLog;

    public CustomLog(final HttpServletRequest request, final HttpServletResponse response) {
        this(new RequestLog(request), new ResponseLog(response));
    }

    public CustomLog(final RequestLog requestLog, final ResponseLog responseLog) {
        this.requestLog = requestLog;
        this.responseLog = responseLog;
    }

    public String log(final BodyFormatter bodyFormatter) throws IOException {
        return responseLog.statusCode() + "," +
                requestLog.method() + "," +
                requestLog.url() + "," +
                "body:" + requestLog.body(bodyFormatter) + "," +
                "headers:" + requestLog.headers() + "," +
                "response:" + responseLog.body(bodyFormatter);
    }
}
