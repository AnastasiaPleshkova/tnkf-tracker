package edu.java.bot.dto.request.client;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkRequest(
    @NotNull
    URI link) {
}
