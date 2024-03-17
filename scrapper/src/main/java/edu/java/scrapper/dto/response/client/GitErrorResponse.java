package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitErrorResponse(@JsonProperty("message") String message,
                               @JsonProperty("documentation_url") String documentation) {
}
