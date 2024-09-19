package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class UrlShortenerEndPoint {
    @PutMapping("/data/shorten")
    ResponseEntity<?> generateShorterUrl() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
    }
}
