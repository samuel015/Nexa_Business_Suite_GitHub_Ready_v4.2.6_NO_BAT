package com.nexa.business.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RequestIdFilter extends OncePerRequestFilter {
    private static final Logger accessLog = LoggerFactory.getLogger("nexa.http.access");
    private static final String HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader(HEADER);
        if (requestId == null || !requestId.matches("[A-Za-z0-9._-]{4,64}")) {
            requestId = UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
        }
        long started = System.nanoTime();
        MDC.put("requestId", requestId);
        response.setHeader(HEADER, requestId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
            accessLog.info("remote={} method={} path={} status={} durationMs={}",
                    request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            MDC.remove("requestId");
        }
    }
}
