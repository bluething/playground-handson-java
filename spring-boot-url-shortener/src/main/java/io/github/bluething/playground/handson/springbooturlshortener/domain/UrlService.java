package io.github.bluething.playground.handson.springbooturlshortener.domain;

import io.github.bluething.playground.handson.springbooturlshortener.config.UrlConfig;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlEntity;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UrlService implements Url{
    private final UrlRepository urlRepository;
    private final Codec codec;
    private final UrlConfig urlConfig;

    @Override
    public String generateShortenUrl(String longUrl) {
        long seq = urlRepository.getNextSequenceValue();
        String encodedUrl = codec.encode(seq);
        UrlEntity urlEntity = new UrlEntity(seq, longUrl, encodedUrl);
        urlRepository.save(urlEntity);

        return urlConfig.getBaseUrl() + encodedUrl;
    }

    @Override
    @Cacheable("shortUrl")
    public String getLongUrl(String shortUrl) {
        return urlRepository.findUrlByShortUrl(shortUrl).getLongUrl();
    }
}
