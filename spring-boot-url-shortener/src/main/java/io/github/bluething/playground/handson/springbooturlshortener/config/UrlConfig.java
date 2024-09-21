package io.github.bluething.playground.handson.springbooturlshortener.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UrlConfig {
    @Value("${url.shortener.base.url}")
    private String baseUrl;
}
