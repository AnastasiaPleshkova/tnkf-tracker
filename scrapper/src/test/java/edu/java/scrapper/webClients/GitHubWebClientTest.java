package edu.java.scrapper.webClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.dto.response.client.GitCommitsResponse;
import edu.java.scrapper.dto.response.client.GitUserResponse;
import java.time.OffsetDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class GitHubWebClientTest {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void fetchUserRepoCorrectResponse() throws JsonProcessingException {
        String user = "AnastasiaPleshkova";
        String repository = "tnkf-tracker";
        OffsetDateTime time = OffsetDateTime.parse("2024-02-16T11:42:20Z");
        GitUserResponse response = new GitUserResponse(user + "/" + repository, time);

        String stabUrl = "/repos/" + user + "/" + repository;
        stubFor(WireMock.get(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        String url = "http://localhost:8080";
        GitClient githubGitClient = new GitHubWebClient(url);

        GitUserResponse actual = githubGitClient.fetchUserRepo(user, repository);

        assertThat(actual)
            .extracting(GitUserResponse::name, GitUserResponse::updatedAt)
            .containsExactly(user + "/" + repository, time);
    }

    @Test
    void fetchUserCorrectResponse() throws JsonProcessingException {
        String user = "AnastasiaPleshkova";
        OffsetDateTime time = OffsetDateTime.parse("2024-02-16T11:42:20Z");
        GitUserResponse response = new GitUserResponse(user, time);

        String stabUrl = "/repos/" + user;
        stubFor(WireMock.get(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        String url = "http://localhost:8080";
        GitClient githubGitClient = new GitHubWebClient(url);

        GitUserResponse actual = githubGitClient.fetchUser(user);

        assertThat(actual)
            .extracting(GitUserResponse::name, GitUserResponse::updatedAt)
            .containsExactly(user, time);
    }

    @Test
    @SneakyThrows
    void fetchUserRepoCommitsCorrectResponse() {
        String user = "AnastasiaPleshkova";
        String repository = "tnkf-tracker";
        GitCommitsResponse[] expectedResponse =
            new GitCommitsResponse[] {new GitCommitsResponse(new GitCommitsResponse.Commit("first"), "url1"),
                                    new GitCommitsResponse(new GitCommitsResponse.Commit("second"), "url2"),
                                    new GitCommitsResponse(new GitCommitsResponse.Commit("third"), "url3"),
            };

        String stabUrl = "/repos/" + user + "/" + repository + "/commits";
        stubFor(WireMock.get(stabUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(objectMapper.writeValueAsString(expectedResponse))));

        String url = "http://localhost:8080";
        GitClient githubGitClient = new GitHubWebClient(url);

        GitCommitsResponse[] actualResponse = githubGitClient.fetchUserRepoCommits(user, repository);

        assertEquals(expectedResponse.length, actualResponse.length);
    }
}
