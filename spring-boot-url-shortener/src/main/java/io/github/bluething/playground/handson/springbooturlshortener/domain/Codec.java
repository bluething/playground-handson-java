package io.github.bluething.playground.handson.springbooturlshortener.domain;

public interface Codec {
    String encode(long value);
    long decode(String value);
}
