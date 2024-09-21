package io.github.bluething.playground.handson.springbooturlshortener.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Base62Test {
    private static Codec codec;

    @BeforeAll
    static void setup() {
        codec = new Base62();
    }

    @Test
    void givenLongValueReturnsStringWithBase62Encode() {
        Assertions.assertEquals("zn9edcu", codec.encode(2009215674938L));
    }

    @Test
    void givenEncodedStringReturnsLongWithBase62Decode() {
        Assertions.assertEquals(2009215674938L, codec.decode("zn9edcu"));
    }
}
