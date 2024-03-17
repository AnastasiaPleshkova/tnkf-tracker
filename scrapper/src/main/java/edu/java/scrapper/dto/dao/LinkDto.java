package edu.java.scrapper.dto.dao;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
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
