package com.nexa.business.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<?> bad(RuntimeException e, HttpServletRequest request) {
        String errorId = errorId();
        log.warn("Handled business error errorId={} method={} path={} message={}",
                errorId, request.getMethod(), request.getRequestURI(), e.getMessage());
        return body(HttpStatus.BAD_REQUEST, e.getMessage(), errorId, request.getRequestURI());
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<?> missing(NoSuchElementException e, HttpServletRequest request) {
        String errorId = errorId();
        log.warn("Resource not found errorId={} method={} path={} message={}",
                errorId, request.getMethod(), request.getRequestURI(), e.getMessage());
        return body(HttpStatus.NOT_FOUND, e.getMessage(), errorId, request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<?> denied(AccessDeniedException e, HttpServletRequest request) {
        String errorId = errorId();
        log.warn("Access denied errorId={} method={} path={}",
                errorId, request.getMethod(), request.getRequestURI());
        return body(HttpStatus.FORBIDDEN, "Akses ditolak untuk role akun ini.", errorId, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorId = errorId();
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .orElse("Data tidak valid.");
        log.warn("Validation error errorId={} method={} path={} message={}",
                errorId, request.getMethod(), request.getRequestURI(), message);
        return body(HttpStatus.BAD_REQUEST, message, errorId, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> unexpected(Exception e, HttpServletRequest request) {
        String errorId = errorId();
        log.error("Unhandled server error errorId={} method={} path={}",
                errorId, request.getMethod(), request.getRequestURI(), e);
        return body(HttpStatus.INTERNAL_SERVER_ERROR,
                "Terjadi kesalahan internal. Sertakan Error ID ini saat melapor: " + errorId,
                errorId,
                request.getRequestURI());
    }

    private String errorId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    private ResponseEntity<Map<String, Object>> body(HttpStatus status, String message, String errorId, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("errorId", errorId);
        body.put("path", path);
        return ResponseEntity.status(status).body(body);
    }
}
