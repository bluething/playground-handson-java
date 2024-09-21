package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest;

import io.github.bluething.playground.handson.springbooturlshortener.domain.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class UrlShortenerEndPoint {
    private final Url urlService;

    @PutMapping("/data/shorten")
    ResponseEntity<?> generateShorterUrl(@RequestBody LongUrlPayload longUrl) {
        String shortUrl = urlService.generateShortenUrl(longUrl.url());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }
}
