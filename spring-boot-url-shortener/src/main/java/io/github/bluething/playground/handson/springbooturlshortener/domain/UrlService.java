package io.github.bluething.playground.handson.springbooturlshortener.domain;

import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
final class UrlService implements Url{
    private final UrlRepository urlRepository;
    private final Codec codec;

    @Override
    public String generateShortenUrl(String longUrl) {
        String encodedUrl = codec.encode(urlRepository.getNextSequenceValue());
        return encodedUrl;
    }
}
