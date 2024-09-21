package io.github.bluething.playground.handson.springbooturlshortener.domain;

import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlEntity;
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
        long seq = urlRepository.getNextSequenceValue();
        String encodedUrl = codec.encode(seq);
        UrlEntity urlEntity = new UrlEntity(seq, longUrl, encodedUrl);
        urlRepository.save(urlEntity);
        return encodedUrl;
    }
}
