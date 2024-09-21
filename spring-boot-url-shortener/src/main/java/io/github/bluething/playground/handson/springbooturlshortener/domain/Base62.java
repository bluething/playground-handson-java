package io.github.bluething.playground.handson.springbooturlshortener.domain;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Base62 implements Codec {
    private static final String BASE62_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char[] BASE62_CHARS = BASE62_STRING.toCharArray();
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
        long result = 0;
        long radixPower = 1L;
        for (int i = value.length()-1; i >= 0; i--) {
            result += radixPower * BASE62_STRING.indexOf(value.charAt(i));
            radixPower *= BASE62_RADIX;
        }

        return result;
    }
}
