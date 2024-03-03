package edu.java.scrapper.dto.request.controller;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkRequest(
    @NotNull
    URI link) {
}
