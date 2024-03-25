package edu.java.scrapper.webClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.dto.response.client.StackQuestion;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8080)
class StackOverFlowWebClientTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnCorrectResponse() throws JsonProcessingException {
        String id = "33310960";
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        OffsetDateTime time = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1653594316), zoneId);

        StackQuestion question = new StackQuestion(id, time, 0);
        StackUserResponse response = new StackUserResponse(Collections.singletonList(question));

        String stabUrl = "/questions/" + id + "?site=stackoverflow&filter=withbody";
        stubFor(WireMock.get(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        String url = "http://localhost:8080";
        StackClient stackOverFlowHttpClient = new StackOverFlowWebClient(url);

        StackUserResponse actual = stackOverFlowHttpClient.fetchQuestion(id);

        assertThat(actual.items())
            .isNotNull()
            .element(0)
            .satisfies(q -> {
                assertThat(q.questionId()).isEqualTo(id);
                assertThat(q.lastActivityDate().atZoneSameInstant(zoneId).toOffsetDateTime())
                    .isEqualTo(time);
            });
    }
}
