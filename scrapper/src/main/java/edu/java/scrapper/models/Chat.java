package edu.java.scrapper.models;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    private Long id;
    private Long tgChatId;
    private OffsetDateTime createdAt;
    private String createdBy;

}
