package edu.java.bot.httpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.bot.dto.request.LinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@WireMockTest(httpPort = 8080)
public class ScrapperHttpClientTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getLinksCorrectRequest() throws JsonProcessingException, URISyntaxException {
        String url = "http://localhost:8080/";
        String stabUrl = "/links";
        URI uri = new URI("github.com");
        LinkResponse[] linkResponse = new LinkResponse[] {new LinkResponse(9999L, uri)};
        ListLinksResponse response =
            new ListLinksResponse(linkResponse, linkResponse.length);
        stubFor(WireMock.get(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperHttpClient scrapperHttpClient = new ScrapperHttpClient(url);
        ListLinksResponse actualResponse = scrapperHttpClient.getLinks(String.valueOf(999));

        assertThat(actualResponse.links()).isEqualTo(response.links());

    }

    @Test
    void addLinksCorrectRequest() throws JsonProcessingException, URISyntaxException {
        String url = "http://localhost:8080/";
        String stabUrl = "/links";
        URI uri = new URI("github.com");
        LinkRequest request = new LinkRequest(uri);
        LinkResponse response = new LinkResponse(999L, uri);

        stubFor(WireMock.post(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperHttpClient scrapperHttpClient = new ScrapperHttpClient(url);

        LinkResponse actualResponse = scrapperHttpClient.addLinks("999", request);

        assertThat(actualResponse).isEqualTo(response);

    }

    @Test
    void deleteLinksCorrectRequest() throws JsonProcessingException, URISyntaxException {
        String url = "http://localhost:8080/";
        String stabUrl = "/links";
        URI uri = new URI("github.com");
        LinkRequest request = new LinkRequest(uri);
        LinkResponse response = new LinkResponse(999L, uri);

        stubFor(WireMock.delete(stabUrl)
            .withHeader("Tg-Chat-Id", equalTo("999"))
            .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        ScrapperHttpClient scrapperHttpClient = new ScrapperHttpClient(url);

        LinkResponse actualResponse = scrapperHttpClient.deleteLinks("999", request);

        assertThat(actualResponse).isEqualTo(response);

    }

    @Test
    void addChatCorrectRequest() {
        String url = "http://localhost:8080/";
        String stabUrl = "/tg-chat/999";

        stubFor(WireMock.post(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")));

        ScrapperHttpClient scrapperHttpClient = new ScrapperHttpClient(url);

        assertDoesNotThrow(() -> scrapperHttpClient.registerChat("999"));
    }

    @Test
    void deleteChatCorrectRequest() {
        String url = "http://localhost:8080/";
        String stabUrl = "/tg-chat/999";

        stubFor(WireMock.delete(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")));

        ScrapperHttpClient scrapperHttpClient = new ScrapperHttpClient(url);

        assertDoesNotThrow(() -> scrapperHttpClient.deleteChat("999"));
    }

}
