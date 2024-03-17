package edu.java.scrapper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.dto.request.controller.LinkRequest;
import java.net.URI;
import java.time.OffsetDateTime;
import edu.java.scrapper.models.Link;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;

@WebMvcTest(ScrapperController.class)
public class ScrapperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;
    @MockBean
    private LinkService linkService;

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

    @Test
    void getAllLinks_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/links")
                .header("Tg-Chat-Id", 123))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addLink_Successful() throws Exception {
        long id = 123;
        URI uri = new URI("example.com");
        LinkRequest request = new LinkRequest(uri);
        Link link = new Link(id, uri.toString(), OffsetDateTime.now(), OffsetDateTime.now(),"");

        when(linkService.add(id, uri)).thenReturn(link);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/links")
                .header("Tg-Chat-Id", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeLink_Successful() throws Exception {
        long id = 123;
        URI uri = new URI("example.com");
        LinkRequest request = new LinkRequest(uri);
        Link link = new Link(id, uri.toString(), OffsetDateTime.now(), OffsetDateTime.now(),"");

        when(linkService.remove(id, uri)).thenReturn(link);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/links")
                .header("Tg-Chat-Id", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
