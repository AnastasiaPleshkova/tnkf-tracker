package edu.java.httpClients;

import edu.java.models.StackUserResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowHttpClient implements StackClient {
    private final WebClient webClient;

    public static final String DEFAULT_URL = "https://api.stackexchange.com/2.3";

    public StackOverFlowHttpClient() {
        this(DEFAULT_URL);
    }

    public StackOverFlowHttpClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public StackUserResponse fetchUser(String userId) {
        return this.webClient.get().uri("/users/{userId}?site=stackoverflow", userId)
            .retrieve().bodyToMono(StackUserResponse.class).block();
    }

}
