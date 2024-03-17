package edu.java.bot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.MyBot;
import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import java.net.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;

@WebMvcTest(BotController.class)
public class BotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MyBot myBot;
    @MockBean
    TelegramBot telegramBot;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void sendUpdates_Successful() throws Exception {
        LinkUpdateRequest linkUpdate = new LinkUpdateRequest(123L, new URI("example.com"),
            "desc", new Long[] {999L}
        );

        when(myBot.getTelegramBot()).thenReturn(telegramBot);

        mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .content(objectMapper.writeValueAsString(linkUpdate))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void sendUpdates_InvalidRequest() throws Exception {
        LinkUpdateRequest invalidUpdate = new LinkUpdateRequest(-123L, new URI("example.com"),
            "desc", new Long[] {999L}
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .content(objectMapper.writeValueAsString(invalidUpdate))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
