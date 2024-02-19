package edu.java.bot.utils;

import io.micrometer.common.util.StringUtils;
import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlValidator {

    public static final String MESSAGE = """
        Данная ссылка не поддерживается.\s
        Убедитесь, что вы вводите корректный формат (Например, /track https://github.com/AnastasiaPleshkova/tnkf-tracker/)
        и что мы поддерживаем указанный вами ресурс.""";

    public static final String GIT = "https://github.com";
    public static final String SOF = "https://stackoverflow.com";

    public static void checkUrl(String url) {

        if (StringUtils.isBlank(url) || isInvalidUrl(url) || isNotSupported(url)) {
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

    private static boolean isNotSupported(String url) {
        return !url.startsWith(GIT) && !url.startsWith(SOF);
    }

}
