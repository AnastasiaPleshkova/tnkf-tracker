package edu.java.bot.utils;

import java.net.URL;

public class UrlValidator {
    private UrlValidator() {}

    public static final String MESSAGE = """
        Данная ссылка не поддерживается.\s
        Убедитесь, что вы вводите корректный формат (Например, /track https://github.com/AnastasiaPleshkova/tnkf-tracker/)
        и что мы поддерживаем указанный вами ресурс.""";

    public static final String GIT = "https://github.com";
    public static final String SOF = "https://stackoverflow.com";

    public static void checkUrl(String url) {
        if (url.isEmpty() || isInvalidUrl(url) || (!url.startsWith(GIT) && !url.startsWith(SOF))) {
            throw new IllegalArgumentException(MESSAGE);
        }
    }

    private static boolean isInvalidUrl(String url) {
        try {
            new URL(url).toURI();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
