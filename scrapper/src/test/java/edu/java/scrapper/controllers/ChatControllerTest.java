package edu.java.scrapper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void registerChat_Successful() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/tg-chat/123"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteChat_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tg-chat/123"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

