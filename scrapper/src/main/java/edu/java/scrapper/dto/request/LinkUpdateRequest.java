package edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.net.URI;

public record LinkUpdateRequest(@NotNull
                                @PositiveOrZero
                                Long id,
                                @NotNull
                                URI url,
                                String description,
                                @NotNull
                                Long[] tgChatIds) {

}
