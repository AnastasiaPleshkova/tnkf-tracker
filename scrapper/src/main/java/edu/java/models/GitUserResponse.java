package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitUserResponse(@JsonProperty("full_name") String name,
                              @JsonProperty("updated_at") OffsetDateTime updatedAt) {
}
