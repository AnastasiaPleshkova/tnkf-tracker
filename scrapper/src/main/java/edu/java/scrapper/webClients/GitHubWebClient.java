package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.response.client.GitErrorResponse;
import edu.java.scrapper.dto.response.client.GitUserResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubWebClient implements GitClient {

    private final WebClient webClient;

    public GitHubWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public GitUserResponse fetchUser(String user) {
        return this.webClient.get().uri("/repos/{owner}", user)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(GitErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.message()))))
            .bodyToMono(GitUserResponse.class).block();
    }

    @Override
    public GitUserResponse fetchUserRepo(String user, String repo) {
        return this.webClient.get().uri("/repos/{owner}/{repo}", user, repo)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(GitErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.message()))))
            .bodyToMono(GitUserResponse.class).block();
    }
}

