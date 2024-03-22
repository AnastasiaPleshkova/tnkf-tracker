package edu.java.bot.webClients;

import edu.java.bot.dto.request.client.LinkRequest;
import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.dto.response.client.LinkResponse;
import edu.java.bot.dto.response.client.ListLinksResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperWebClient implements ScrapperClient {
    public static final String LINKS = "api/links";
    public static final String CHAT_ID = "api/tg-chat/{id}";
    public static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;

    public ScrapperWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public ListLinksResponse getLinks(String chatId) {
        return webClient.get()
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.description()))))
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLinks(String chatId, @Valid LinkRequest linkRequest) {
        return webClient.post()
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.description()))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLinks(String chatId, @Valid LinkRequest linkRequest) {
        return this.webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.description()))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public void registerChat(String chatId) {
        webClient.post()
            .uri(CHAT_ID, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public void deleteChat(String chatId) {
        webClient.delete()
            .uri(CHAT_ID, chatId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .block();
    }

}
