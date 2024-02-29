package edu.java.scrapper.httpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.dto.request.LinkUpdateRequest;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 8090)
public class BotHttpClientTest {
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void correctRequest() throws URISyntaxException {
        String botUrl = "http://localhost:8090/";
        String stabUrl = "/updates";
        stubFor(WireMock.post(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")));

        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            100L, new URI("google.com"), "description", new Long[1]
        );
        BotHttpClient botHttpClient = new BotHttpClient(botUrl);

        assertDoesNotThrow(() -> botHttpClient.sendUpdate(linkUpdateRequest));

    }

    @Test
    void incorrectRequest() throws JsonProcessingException, URISyntaxException {
        String botUrl = "http://localhost:8090/";
        String stabUrl = "/updates";
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("desc",
            "400", "name", "mess", new String[0]
        );

        stubFor(WireMock.post(stabUrl)
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(apiErrorResponse))));

        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            100L, new URI("google.com"), "description", new Long[1]
        );

        BotHttpClient botHttpClient = new BotHttpClient(botUrl);

        assertThrows(RuntimeException.class, () -> botHttpClient.sendUpdate(linkUpdateRequest));

    }

}
