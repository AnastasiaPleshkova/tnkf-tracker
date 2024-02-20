package edu.java.httpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.models.StackUserResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 80)
public class StackOverFlowHttpClientTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldReturnCorrectResponse() throws JsonProcessingException {
        String userID = "23435413";
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        OffsetDateTime time = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1707693603), zoneId);

        StackUserResponse response = new StackUserResponse(userID, time);

        String stabUrl = "/users/" + userID + "?site=stackoverflow";
        stubFor(WireMock.get(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        String url = "http://localhost:80";
        StackClient stackOverFlowHttpClient = new StackOverFlowHttpClient(url);

        StackUserResponse actual = stackOverFlowHttpClient.fetchUser(userID);

        assertThat(actual)
            .extracting(
                StackUserResponse::userId,
                x -> x.lastModifiedDate().toZonedDateTime().withZoneSameInstant(zoneId).toOffsetDateTime()
            )
            .containsExactly(userID, time);
    }
}
