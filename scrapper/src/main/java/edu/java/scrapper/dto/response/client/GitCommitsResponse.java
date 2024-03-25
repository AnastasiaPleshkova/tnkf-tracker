package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitCommitsResponse(Commit commit,
                                 @JsonProperty("html_url") String url) {
    public record Commit(String message) {
    }
}
