package edu.java.scrapper.models;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private String url;
    private String questionId;
    private String ownerName;
    private String repositoryName;
    private Long answerCount;
    private OffsetDateTime lastCheckTime;
    private Long commitsCount;
    private OffsetDateTime createdAt;
    private String createdBy;
}
