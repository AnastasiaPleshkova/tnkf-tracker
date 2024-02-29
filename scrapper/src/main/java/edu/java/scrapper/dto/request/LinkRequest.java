package edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkRequest(
    @NotNull
    URI link) {
}
