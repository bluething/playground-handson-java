package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Log4j2
class RestErrorHandler {
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateKeyError(Throwable ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
