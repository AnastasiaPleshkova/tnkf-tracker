package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackUserResponse(@JsonProperty("items") List<Question> items) {
    public record Question(
        @JsonProperty("question_id") String questionId,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate
    ) {
    }
}

