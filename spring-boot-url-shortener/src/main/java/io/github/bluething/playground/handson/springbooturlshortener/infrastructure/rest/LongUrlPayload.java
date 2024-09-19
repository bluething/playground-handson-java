package io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LongUrlPayload(@JsonProperty("longUrl") String url) {
}
