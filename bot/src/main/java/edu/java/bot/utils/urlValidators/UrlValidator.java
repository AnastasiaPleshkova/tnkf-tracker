package edu.java.bot.utils.urlValidators;

public interface UrlValidator {
    String MESSAGE = "Данная ссылка не поддерживается. \n"
        + "Убедитесь, что вы вводите корректный формат (Например, https://github.com/sanyarnd/)\n"
        + "и что мы поддерживаем указанный вами ресурс.";

    void check(String url, long chatId);

    default void checkNext(String url, long chatId) {
        if (getNext() == null) {
            throw new IllegalArgumentException(MESSAGE);
        }
        getNext().check(url, chatId);
    }

    UrlValidator getNext();

    void setNext(UrlValidator validator);

}
