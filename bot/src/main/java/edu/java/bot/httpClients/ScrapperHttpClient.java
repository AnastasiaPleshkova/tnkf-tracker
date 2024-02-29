package edu.java.bot.httpClients;

import edu.java.bot.dto.request.LinkRequest;
import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperHttpClient implements ScrapperClient {
    public static final String LINKS = "/links";
    public static final String CHAT_ID = "/tg-chat/{id}";
    public static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;

    public ScrapperHttpClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public ListLinksResponse getLinks(String chatId) {
        return webClient.get()
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLinks(String chatId, LinkRequest linkRequest) {
        return webClient.post()
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkRequest)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLinks(String chatId, LinkRequest linkRequest) {
        return this.webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(TG_CHAT_ID_HEADER, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(linkRequest)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public void registerChat(String chatId) {
        webClient.post()
            .uri(CHAT_ID, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public void deleteChat(String chatId) {
        webClient.delete()
            .uri(CHAT_ID, chatId)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(String.valueOf(errorResponse)))))
            .bodyToMono(Void.class)
            .block();
    }

}
