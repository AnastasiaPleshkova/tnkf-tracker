package edu.java.scrapper.dto.dao;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private Long tgChatId;
    private OffsetDateTime createdAt;
    private String createdBy;

}
