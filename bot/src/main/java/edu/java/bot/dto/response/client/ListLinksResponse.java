package edu.java.bot.dto.response.client;

public record ListLinksResponse(LinkResponse[] links,
                                Integer size) {
}
