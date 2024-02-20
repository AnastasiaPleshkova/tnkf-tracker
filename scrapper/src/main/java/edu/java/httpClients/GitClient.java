package edu.java.httpClients;

import edu.java.models.GitUserResponse;

public interface GitClient {
    GitUserResponse fetchUser(String user);

    GitUserResponse fetchUserRepo(String user, String repo);

}
