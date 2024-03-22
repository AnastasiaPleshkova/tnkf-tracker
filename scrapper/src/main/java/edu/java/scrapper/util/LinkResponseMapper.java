package edu.java.scrapper.util;

import edu.java.scrapper.dto.response.controller.LinkResponse;
import edu.java.scrapper.models.Link;
import java.net.URI;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkResponseMapper {

    @SneakyThrows
    public LinkResponse mapToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), new URI(link.getUrl()));
    }
}
