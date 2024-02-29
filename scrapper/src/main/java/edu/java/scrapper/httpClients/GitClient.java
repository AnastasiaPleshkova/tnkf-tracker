package edu.java.scrapper.httpClients;

import edu.java.scrapper.dto.response.GitUserResponse;

public interface GitClient {
    GitUserResponse fetchUser(String user);

    GitUserResponse fetchUserRepo(String user, String repo);

}
