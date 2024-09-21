package io.github.bluething.playground.handson.springbooturlshortener.domain;

import org.springframework.stereotype.Component;

@Component
public class Base62 implements Codec {
    private static final char[] BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Integer BASE62_RADIX = BASE62_CHARS.length;

    @Override
    public String encode(long value) {
        StringBuilder result = new StringBuilder();
        while (value > 0) {
            int idx = (int) (value % BASE62_RADIX);
            result.append(BASE62_CHARS[(int) (value % BASE62_RADIX)]);
            value /= BASE62_RADIX;
        }

        return result.reverse().toString();
    }

    @Override
    public long decode(String value) {
        return 0;
    }
}
