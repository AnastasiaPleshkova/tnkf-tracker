package edu.java.scrapper.exceptions;

public class LinkNotFound extends RuntimeException {
    public static final String MSG = "Указанная ссылка не была найдена";

    public LinkNotFound() {
        super(MSG);
    }
}
