package edu.java.scrapper.webClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 8090)
class BotWebClientTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
        BotWebClient botWebClient = new BotWebClient(botUrl, Retry.max(2));

        assertDoesNotThrow(() -> botWebClient.sendUpdate(linkUpdateRequest));

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

        BotWebClient botWebClient = new BotWebClient(botUrl,Retry.max(2));

        assertThrows(RuntimeException.class, () -> botWebClient.sendUpdate(linkUpdateRequest));

    }

}
