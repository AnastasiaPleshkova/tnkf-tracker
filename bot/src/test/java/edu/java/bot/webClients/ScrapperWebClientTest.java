package edu.java.bot.webClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.bot.dto.request.client.LinkRequest;
import edu.java.bot.dto.response.client.LinkResponse;
import edu.java.bot.dto.response.client.ListLinksResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@WireMockTest(httpPort = 8080)
class ScrapperWebClientTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void getLinksCorrectRequest() {
        String url = "http://localhost:8080/";
        String stabUrl = "/api/links";
        long id = 999;
        LinkResponse[] linkResponse = new LinkResponse[] {new LinkResponse(id, new URI("github.com"))};
        ListLinksResponse response =
            new ListLinksResponse(linkResponse, linkResponse.length);

        stubFor(WireMock.get(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperWebClient scrapperWebClient = new ScrapperWebClient(url);

        ListLinksResponse actualResponse = scrapperWebClient.getLinks(String.valueOf(999));
        assertThat(actualResponse.links()).isEqualTo(response.links());
    }

    @Test
    void addLinksCorrectRequest() throws JsonProcessingException, URISyntaxException {
        String url = "http://localhost:8080/";
        String stabUrl = "/api/links";
        URI uri = new URI("github.com");
        long id = 999;
        LinkRequest request = new LinkRequest(uri);
        LinkResponse response = new LinkResponse(id, uri);

        stubFor(WireMock.post(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperWebClient scrapperWebClient = new ScrapperWebClient(url);

        LinkResponse actualResponse = scrapperWebClient.addLinks("999", request);

        assertThat(actualResponse).isEqualTo(response);

    }

    @Test
    void deleteLinksCorrectRequest() throws JsonProcessingException, URISyntaxException {
        String url = "http://localhost:8080/";
        String stabUrl = "/api/links";
        long id = 999;
        URI uri = new URI("github.com");
        LinkRequest request = new LinkRequest(uri);
        LinkResponse response = new LinkResponse(id, uri);

        stubFor(WireMock.delete(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperWebClient scrapperWebClient = new ScrapperWebClient(url);

        LinkResponse actualResponse = scrapperWebClient.deleteLinks("999", request);

        assertThat(actualResponse).isEqualTo(response);

    }

    @Test
    void addChatCorrectRequest() {
        String url = "http://localhost:8080/";
        String stabUrl = "/api/tg-chat/999";

        stubFor(WireMock.post(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")));

        ScrapperWebClient scrapperWebClient = new ScrapperWebClient(url);

        assertDoesNotThrow(() -> scrapperWebClient.registerChat("999"));
    }

    @Test
    void deleteChatCorrectRequest() {
        String url = "http://localhost:8080/";
        String stabUrl = "/api/tg-chat/999";

        stubFor(WireMock.delete(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")));

        ScrapperWebClient scrapperWebClient = new ScrapperWebClient(url);

        assertDoesNotThrow(() -> scrapperWebClient.deleteChat("999"));
    }

}
