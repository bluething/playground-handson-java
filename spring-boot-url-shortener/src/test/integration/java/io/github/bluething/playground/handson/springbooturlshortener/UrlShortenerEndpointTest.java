package io.github.bluething.playground.handson.springbooturlshortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bluething.playground.handson.springbooturlshortener.config.UrlConfig;
import io.github.bluething.playground.handson.springbooturlshortener.infrastructure.rest.LongUrlPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    private UrlConfig urlConfig;

    @Sql (
            statements = "DELETE FROM url WHERE long_url = 'https://github.com/bluething'",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("When we generate new shorter url, then the system must return 201 Http status code")
    @Test
    void generateShorterUrlMustReturn201HttpStatus() throws Exception {
        LongUrlPayload url = new LongUrlPayload("https://github.com/bluething");
        mockMvc.perform(put("/api/v1/data/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isCreated());
    }

    @Sql (
            statements = "DELETE FROM url WHERE long_url = 'https://github.com/bluething'",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
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

    @Sql (
            statements = "DELETE FROM url WHERE long_url = 'https://github.com/bluething'",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("When we generate new shorter url, then a new row must be written in database")
    @Test
    void generateShorterUrlMustReturnShortenUrlWithBaseUrl() throws Exception {
        LongUrlPayload url = new LongUrlPayload("https://github.com/bluething");
        MvcResult result = mockMvc.perform(put("/api/v1/data/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andReturn();

        Assertions.assertEquals(0, result.getResponse().getContentAsString().indexOf(urlConfig.getBaseUrl()));
    }

    @Sql (
            statements = "INSERT INTO url VALUES(2009215674938, 'https://github.com/bluething', 'zn9edcu', current_timestamp, current_timestamp)",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql (
            statements = "DELETE FROM url WHERE long_url = 'https://github.com/bluething'",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("When we get long url, then the system must return 301 Http status code")
    @Test
    void whenWeGetLongUrlThenEndpointMustReturn301HttpStatusResponse() throws Exception {
        mockMvc.perform(get("/api/v1/zn9edcu"))
                .andExpect(status().isMovedPermanently());
    }
}
