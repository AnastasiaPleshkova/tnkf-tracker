package edu.java.bot.utils;

public class CommandRemover {
    private CommandRemover() {}

    public static final String ERROR_MSG = """
                Введена некорректная команда.\s
                Убедитесь, что вы вводите корректный формат\s
                (Например, /track https://github.com/AnastasiaPleshkova/tnkf-tracker/)""";

    public static String removeCommand(String message) {
        String url = (message.replaceFirst("^/[A-Za-z]+\\s", "")).toLowerCase();
        if (url.equals(message)) {
            throw new IllegalArgumentException(ERROR_MSG);
        }
        return url;
    }
}
