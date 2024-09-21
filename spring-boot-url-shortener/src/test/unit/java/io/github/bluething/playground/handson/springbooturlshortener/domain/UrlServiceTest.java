package io.github.bluething.playground.handson.springbooturlshortener.domain;

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
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = new UrlService(urlRepository, codec);
    }

    @DisplayName("Given database sequence call, when we call generateShortenUrl it must return shorter url string")
    @Test
    void generateShortenUrlReturnShortenedUrlString() {
        given(urlRepository.getNextSequenceValue()).willReturn(2009215674938L);
        given(codec.encode(2009215674938L)).willReturn("zn9edcu");

        Assertions.assertEquals("zn9edcu", urlService.generateShortenUrl("https://github.com/bluething"));
    }

    @DisplayName("Given database sequence call, when we call generateShortenUrl it must call save method to database")
    @Test
    void generateShortenUrlMustCallSaveToDatabaseMethod() {
        given(urlRepository.getNextSequenceValue()).willReturn(2009215674938L);
        given(codec.encode(2009215674938L)).willReturn("zn9edcu");
        urlService.generateShortenUrl("https://github.com/bluething");
        UrlEntity urlEntity = new UrlEntity(2009215674938L, "https://github.com/bluething", "zn9edcu");
        verify(urlRepository, times(1)).save(urlEntity);
    }
}
