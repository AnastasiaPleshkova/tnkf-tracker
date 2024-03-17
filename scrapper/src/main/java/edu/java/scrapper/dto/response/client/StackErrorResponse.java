package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackErrorResponse(@JsonProperty("error_id") String errorId,
                               @JsonProperty("error_message") String errorMessage,
                               @JsonProperty("error_name") String errorName) {
}
