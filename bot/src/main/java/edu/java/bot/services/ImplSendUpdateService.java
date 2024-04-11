package edu.java.bot.services;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.MyBot;
import edu.java.bot.dto.request.controller.LinkUpdateRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImplSendUpdateService implements SendUpdateService {
    private final MyBot myBot;

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdate) {
        String message = linkUpdate.description();
        log.info(message);

        Arrays.stream(linkUpdate.tgChatIds())
            .map(id -> new SendMessage(id, message))
            .forEach(myBot::executeMessage);
    }

}
