package edu.java.bot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.MyBot;
import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import edu.java.bot.services.SendErrorService;
import java.net.URI;
import java.time.Duration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimitingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MyBot myBot;
    @MockBean
    TelegramBot telegramBot;
    @MockBean
    SendErrorService implSendUpdateService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    void updates_endpoint_is_restricted_to_10_requests_in_five_sec() {
        Thread.sleep(Duration.ofSeconds(5));
        LinkUpdateRequest linkUpdate = new LinkUpdateRequest(123L, new URI("example.com"),
            "desc", new Long[] {999L}
        );

        for (int i = 1; i < 11; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                    .content(objectMapper.writeValueAsString(linkUpdate))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .content(objectMapper.writeValueAsString(linkUpdate))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isTooManyRequests());

    }
}
