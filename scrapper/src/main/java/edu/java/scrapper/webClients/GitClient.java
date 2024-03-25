package edu.java.scrapper.webClients;

import edu.java.scrapper.dto.response.client.GitCommitsResponse;
import edu.java.scrapper.dto.response.client.GitUserResponse;

public interface GitClient {
    GitUserResponse fetchUser(String user);

    GitUserResponse fetchUserRepo(String user, String repo);

    GitCommitsResponse[] fetchUserRepoCommits(String user, String repo);

}
