package edu.java.scrapper.httpClients;

import edu.java.scrapper.dto.response.StackUserResponse;

public interface StackClient {
    StackUserResponse fetchQuestion(String id);
}
