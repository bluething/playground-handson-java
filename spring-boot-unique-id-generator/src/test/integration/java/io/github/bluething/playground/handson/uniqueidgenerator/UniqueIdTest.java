package io.github.bluething.playground.handson.uniqueidgenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Given Unique Id Generator API")
@SpringBootTest(properties = {"datacenterId=1", "machineId=1"})
@AutoConfigureMockMvc
class UniqueIdTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("When we call unique id API then the API must return id that fit into 64 bit integer")
    @Test
    void getUniqueIdThenReturnUniqueIdThatFitInto64BitInteger() throws Exception {
        mockMvc.perform(get("/unique-id")).andExpect(status().isOk())
                .andExpect(result -> {
                    long uniqueId = Long.parseLong(result.getResponse().getContentAsString());
                    AssertionErrors.assertTrue("", uniqueId >= Long.MIN_VALUE && uniqueId <= Long.MAX_VALUE);
                });
    }
}
