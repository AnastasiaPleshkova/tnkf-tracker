package edu.java.scrapper.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackQuestion(@JsonProperty("question_id") String questionId,
                            @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
                            @JsonProperty("answer_count") Integer answerCount) {
}
