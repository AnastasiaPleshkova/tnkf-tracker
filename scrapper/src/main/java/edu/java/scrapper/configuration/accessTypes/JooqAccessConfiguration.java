package edu.java.scrapper.configuration.accessTypes;

import edu.java.scrapper.repositories.jooq.JooqChatRepository;
import edu.java.scrapper.repositories.jooq.JooqLinkRepository;
import edu.java.scrapper.services.ChatService;
import edu.java.scrapper.services.LinkService;
import edu.java.scrapper.services.LinkUpdater;
import edu.java.scrapper.services.jooq.JooqChatService;
import edu.java.scrapper.services.jooq.JooqLinkService;
import edu.java.scrapper.services.jooq.JooqLinkUpdater;
import edu.java.scrapper.services.sendUpdates.SendUpdate;
import edu.java.scrapper.webClients.GitClient;
import edu.java.scrapper.webClients.StackClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {

    private final GitClient gitClient;
    private final StackClient stackClient;
    private final SendUpdate updateSendler;

    @Bean
    public LinkService linkService(
        JooqLinkRepository linkRepository,
        JooqChatRepository chatRepository
    ) {
        return new JooqLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService chatService(
        JooqChatRepository chatRepository
    ) {
        return new JooqChatService(chatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        JooqLinkRepository linkRepository
    ) {
        return new JooqLinkUpdater(linkRepository, gitClient, stackClient, updateSendler);
    }
}

