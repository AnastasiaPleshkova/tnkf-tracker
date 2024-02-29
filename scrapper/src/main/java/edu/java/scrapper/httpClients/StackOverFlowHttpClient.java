package edu.java.scrapper.httpClients;

import edu.java.scrapper.dto.response.StackUserResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowHttpClient implements StackClient {
    private final WebClient webClient;

    public StackOverFlowHttpClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public StackUserResponse fetchQuestion(String id) {
        return this.webClient.get().uri("/questions/{id}?site=stackoverflow&filter=withbody", id)
            .retrieve().bodyToMono(StackUserResponse.class).block();
    }

}
