package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Commit(@JsonProperty("message") String message) {
}


