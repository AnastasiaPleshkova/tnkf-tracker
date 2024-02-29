package edu.java.scrapper.exceptions;

public class ChatNotFound extends RuntimeException {
    public static final String MSG = "Указанный чат не был найден";

    public ChatNotFound() {
        super(MSG);
    }
}
