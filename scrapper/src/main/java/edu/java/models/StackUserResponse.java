package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackUserResponse(@JsonProperty("user_id") String userId,
                                @JsonProperty("last_modified_date") OffsetDateTime lastModifiedDate) {
}
