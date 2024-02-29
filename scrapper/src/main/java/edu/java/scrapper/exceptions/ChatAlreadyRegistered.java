package edu.java.scrapper.exceptions;

public class ChatAlreadyRegistered extends RuntimeException {
    public static final String MSG = "Указанный вами чат уже зарегистрирован";

    public ChatAlreadyRegistered() {
        super(MSG);
    }
}
