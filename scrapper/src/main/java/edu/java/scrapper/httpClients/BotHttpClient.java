package edu.java.scrapper.httpClients;

import edu.java.scrapper.dto.request.LinkUpdateRequest;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotHttpClient implements BotClient {

    private final WebClient webClient;

    public BotHttpClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdate) {
        webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .block();
    }

}
