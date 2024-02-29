package edu.java.scrapper.exceptions;

public class LinkAlreadyTracked extends RuntimeException {
    public static final String MSG = "Указанная ссылка уже отслеживается";

    public LinkAlreadyTracked() {
        super(MSG);
    }
}
