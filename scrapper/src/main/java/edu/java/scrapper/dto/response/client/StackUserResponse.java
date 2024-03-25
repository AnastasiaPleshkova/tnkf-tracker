package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackUserResponse(@JsonProperty("items") List<StackQuestion> items) {
}

