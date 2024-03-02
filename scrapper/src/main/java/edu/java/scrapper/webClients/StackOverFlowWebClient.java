package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.response.client.StackUserResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowWebClient implements StackClient {
    private final WebClient webClient;

    public StackOverFlowWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public StackUserResponse fetchQuestion(String id) {
        return this.webClient.get().uri("/questions/{id}?site=stackoverflow&filter=withbody", id)
            .retrieve().bodyToMono(StackUserResponse.class).block();
    }

}
