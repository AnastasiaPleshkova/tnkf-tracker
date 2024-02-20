package edu.java.httpClients;

import edu.java.models.StackUserResponse;

public interface StackClient {
    StackUserResponse fetchUser(String user);
}
