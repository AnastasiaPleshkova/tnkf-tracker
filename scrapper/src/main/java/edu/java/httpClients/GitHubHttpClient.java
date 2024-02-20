package edu.java.httpClients;

import edu.java.models.GitUserResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubHttpClient implements GitClient {

    private final WebClient webClient;

    public static final String DEFAULT_URL = "https://api.github.com";

    public GitHubHttpClient() {
        this(DEFAULT_URL);
    }

    public GitHubHttpClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public GitUserResponse fetchUser(String user) {
        return this.webClient.get().uri("/repos/{owner}", user)
            .retrieve().bodyToMono(GitUserResponse.class).block();
    }

    @Override
    public GitUserResponse fetchUserRepo(String user, String repo) {
        return this.webClient.get().uri("/repos/{owner}/{repo}", user, repo)
            .retrieve().bodyToMono(GitUserResponse.class).block();
    }
}

