package edu.java.scrapper.configuration.accessTypes;

import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.LinkUpdater;
import edu.java.scrapper.services.jdbc.JdbcChatService;
import edu.java.scrapper.services.jdbc.JdbcLinkService;
import edu.java.scrapper.services.jdbc.JdbcLinkUpdater;
import edu.java.scrapper.services.sendUpdates.SendUpdate;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {

    private final GitClient gitClient;
    private final StackClient stackClient;
    private final SendUpdate updateSendler;

    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository
    ) {
        return new JdbcLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService chatService(
        JdbcChatRepository chatRepository
    ) {
        return new JdbcChatService(chatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        JdbcLinkRepository linkRepository
    ) {
        return new JdbcLinkUpdater(linkRepository, gitClient, stackClient, updateSendler);
    }
}
