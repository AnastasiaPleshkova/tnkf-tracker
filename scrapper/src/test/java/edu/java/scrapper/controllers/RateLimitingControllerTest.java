package edu.java.scrapper.controllers;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.services.ChatService;
import java.time.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimitingControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Test
    @SneakyThrows
    void register_chat_endpoint_is_restricted_to_10_requests_in_five_sec() {
        Thread.sleep(Duration.ofSeconds(5));

        for (int i = 1; i < 11; i++) {
            mockMvc.perform(post("/api/tg-chat/" + i)).andExpect(status().isOk());
        }

        mockMvc.perform(post("/api/tg-chat/9999")).andExpect(status().isTooManyRequests());
    }

    @Test
    @SneakyThrows
    void unregister_chat_endpoint_is_restricted_to_10_requests_in_five_sec() {
        Thread.sleep(Duration.ofSeconds(5));

        for (int i = 1; i < 11; i++) {
            mockMvc.perform(delete("/api/tg-chat/" + i)).andExpect(status().isOk());
        }

        mockMvc.perform(delete("/api/tg-chat/9999")).andExpect(status().isTooManyRequests());
    }

    @Test
    @SneakyThrows
    void link_endpoint_is_restricted_to_10_requests_in_five_sec() {
        Thread.sleep(Duration.ofSeconds(5));

        for (int i = 1; i < 11; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/links")
                    .header("Tg-Chat-Id", 123))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/api/links")
            .header("Tg-Chat-Id", 123)).andExpect(status().isTooManyRequests());
    }

}

