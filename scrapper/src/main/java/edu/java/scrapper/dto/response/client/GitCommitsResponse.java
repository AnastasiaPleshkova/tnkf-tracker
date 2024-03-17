package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;


public record GitCommitsResponse(@JsonProperty("commit") Commit commit,
                               @JsonProperty("html_url") String url) {
}
