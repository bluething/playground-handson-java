package io.github.bluething.playground.handson.springbooturlshortener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Given Url Shortener API")
@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("When we generate new shorter url, then the system must return 301 Http status code")
    @Test
    void generateShorterUrlMustReturn301HttpStatus() throws Exception {
        mockMvc.perform(put("/api/v1/data/shorten")).andExpect(status().isMovedPermanently());
    }
}
