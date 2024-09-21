package io.github.bluething.playground.handson.springbooturlshortener.domain;

import io.github.bluething.playground.handson.springbooturlshortener.config.UrlConfig;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlEntity;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.persistence.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @Mock
    private UrlRepository urlRepository;
    @Mock
    private Codec codec;
    @Mock
    private UrlConfig urlConfig;
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = new UrlService(urlRepository, codec, urlConfig);
    }

    @DisplayName("Given database sequence call, when we call generateShortenUrl it must return shorter url string")
    @Test
    void generateShortenUrlReturnShortenedUrlString() {
        given(urlRepository.getNextSequenceValue()).willReturn(2009215674938L);
        given(codec.encode(2009215674938L)).willReturn("zn9edcu");
        given(urlConfig.getBaseUrl()).willReturn("http://localhost:8080/api/v1/");

        Assertions.assertEquals("http://localhost:8080/api/v1/zn9edcu", urlService.generateShortenUrl("https://github.com/bluething"));
    }

    @DisplayName("Given database sequence call, when we call generateShortenUrl it must call save method to database")
    @Test
    void generateShortenUrlMustCallSaveToDatabaseMethod() {
        given(urlRepository.getNextSequenceValue()).willReturn(2009215674938L);
        given(codec.encode(2009215674938L)).willReturn("zn9edcu");
        given(urlConfig.getBaseUrl()).willReturn("http://localhost:8080/api/v1/");

        urlService.generateShortenUrl("https://github.com/bluething");
        UrlEntity urlEntity = new UrlEntity(2009215674938L, "https://github.com/bluething", "zn9edcu");
        verify(urlRepository, times(1)).save(urlEntity);
    }

    @DisplayName("Given database select call, when we call getLongUrl it must call the select method once")
    @Test
    void getLongUrlMustCallReadToDatabaseMethod() {
        UrlEntity urlEntity = new UrlEntity(2009215674938L, "https://github.com/bluething", "zn9edcu");
        given(urlRepository.findUrlByShortUrl(any())).willReturn(urlEntity);
        urlService.getLongUrl("zn9edcu");
        verify(urlRepository, times(1)).findUrlByShortUrl(any());
    }
}
