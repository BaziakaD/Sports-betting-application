package com.training.sportbetting.controller.api;

import com.training.sportbetting.controller.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {

        var status = INTERNAL_SERVER_ERROR;
        if (e instanceof ResourceNotFoundException) {
            status = NOT_FOUND;
        }

        HashMap<String, String> body = getErrorBody(status, e.getMessage(), request.getRequestURI());

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(body);
    }

    private HashMap<String, String> getErrorBody(HttpStatus status, String msg, String path) {
        var timestamp = DateTimeFormatter.ISO_ZONED_DATE_TIME
                .withLocale(Locale.US)
                .withZone(ZoneOffset.UTC)
                .format(ZonedDateTime.now());

        var body = new HashMap<String, String>();

        body.put("timestamp", timestamp);
        body.put("status", String.valueOf(status.value()));
        body.put("statusName", status.getReasonPhrase());
        body.put("message", msg);
        body.put("path", path);
        return body;
    }
}
