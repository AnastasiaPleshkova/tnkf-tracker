package edu.java.bot.utils;

import org.springframework.stereotype.Component;

@Component
public class CommandRemover {
    public static final String ERROR_MSG = "Введена некорректная команда";

    public String removeCommand(String message) {
        String url = (message.replaceFirst("^/[A-Za-z]+\\s", "")).toLowerCase();
        if (url.equals(message)) {
            throw new IllegalArgumentException(ERROR_MSG);
        }
        return url;
    }
}
