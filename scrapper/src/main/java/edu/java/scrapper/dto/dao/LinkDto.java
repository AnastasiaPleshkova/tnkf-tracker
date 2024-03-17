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
    private OffsetDateTime lastCheckTime;
    private OffsetDateTime createdAt;
    private String createdBy;
}
