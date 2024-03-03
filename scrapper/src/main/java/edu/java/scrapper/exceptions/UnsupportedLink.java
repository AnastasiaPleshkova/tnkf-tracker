package edu.java.scrapper.exceptions;

public class UnsupportedLink extends RuntimeException {
    public static final String MSG = "Указанная ссылка не поддерживается";

    public UnsupportedLink() {
        super(MSG);
    }
}
