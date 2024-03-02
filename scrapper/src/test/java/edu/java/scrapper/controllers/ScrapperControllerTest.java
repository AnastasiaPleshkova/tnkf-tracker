package edu.java.scrapper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.dto.request.controller.LinkRequest;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ScrapperController.class)
public class ScrapperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void registerChat_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tg-chat/123"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteChat_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tg-chat/123"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllLinks_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/links")
                .param("Tg-Chat-Id", "123"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addLink_Successful() throws Exception {
        LinkRequest request = new LinkRequest(new URI("example.com"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/links")
                .param("Tg-Chat-Id", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeLink_Successful() throws Exception {
        LinkRequest request = new LinkRequest(new URI("example.com"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/links")
                .param("Tg-Chat-Id", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
