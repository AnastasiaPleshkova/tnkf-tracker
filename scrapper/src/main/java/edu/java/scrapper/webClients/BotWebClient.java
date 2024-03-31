package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.request.client.LinkUpdateRequest;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class BotWebClient implements BotClient {

    private final WebClient webClient;
    private final Retry retry;

    public BotWebClient(String url, Retry retry) {
        this.webClient = WebClient.builder().baseUrl(url).build();
        this.retry = retry;
    }

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdate) {
        webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .retryWhen(retry)
            .block();
    }

}
