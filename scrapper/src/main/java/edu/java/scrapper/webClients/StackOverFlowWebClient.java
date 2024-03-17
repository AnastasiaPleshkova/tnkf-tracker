package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.response.client.StackErrorResponse;
import edu.java.scrapper.dto.response.client.StackUserResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverFlowWebClient implements StackClient {
    private final WebClient webClient;

    public StackOverFlowWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public StackUserResponse fetchQuestion(String id) {
        return this.webClient.get().uri("/questions/{id}?site=stackoverflow&filter=withbody", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(StackErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(
                    errorResponse.errorMessage() + errorResponse.errorName()))))
            .bodyToMono(StackUserResponse.class).block();
    }

}
