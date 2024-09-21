package io.github.bluething.playground.handson.springbooturlshortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest.LongUrlPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Given Url Shortener API")
@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("When we generate new shorter url, then the system must return 301 Http status code")
    @Test
    void generateShorterUrlMustReturn301HttpStatus() throws Exception {
        LongUrlPayload url = new LongUrlPayload("https://github.com/bluething");
        mockMvc.perform(put("/api/v1/data/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isMovedPermanently());
    }

    @DisplayName("When we generate new shorter url, then a new row must be written in database")
    @Test
    void generateShorterUrlMustWriteOneRowInDb() throws Exception {
        LongUrlPayload url = new LongUrlPayload("https://github.com/bluething");
        mockMvc.perform(put("/api/v1/data/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andReturn();

        Integer numOfRows = jdbcTemplate.queryForObject("select count(*) from url where long_url=?", Integer.class, url.url());
        Assertions.assertEquals(1, numOfRows);
    }
}
