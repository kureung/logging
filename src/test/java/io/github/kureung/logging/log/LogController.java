package io.github.kureung.logging.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.*;

@RestController
@ConditionalOnMissingBean
@RequestMapping("/log")
public class LogController {
    @PostMapping("/hello")
    public LogRequest aa(@RequestBody LogRequest request) {
        if (request.gogo.equals("123")) {
            throw new IllegalArgumentException("zz");
        }
        return request;
    }

    @GetMapping("/hello")
    public String bb(@RequestParam String haha) {
        if (haha.equals("123")) {
            throw new IllegalArgumentException("zz");
        }
        return haha;
    }

    @GetMapping("/hello/{haha}")
    public String cc(@PathVariable String haha) {
        if (haha.equals("123")) {
            throw new IllegalArgumentException("zz");
        }
        return haha;
    }

    static class LogRequest {
        private String gogo;
        private String haha;

        public LogRequest(final String gogo, final String haha) {
            this.gogo = gogo;
            this.haha = haha;
        }

        public LogRequest() {
        }

        public String getGogo() {
            return gogo;
        }

        public String getHaha() {
            return haha;
        }
    }
}
