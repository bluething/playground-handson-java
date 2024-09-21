package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest;

import io.github.bluething.playground.handson.springbooturlshortener.domain.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{shortUrl}")
    ResponseEntity<?> getLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", longUrl).build();
    }
}
