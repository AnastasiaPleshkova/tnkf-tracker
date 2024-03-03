package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.response.client.StackUserResponse;

public interface StackClient {
    StackUserResponse fetchQuestion(String id);
}
