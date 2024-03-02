package edu.java.scrapper.exceptions;

public class ResourceNotFound extends RuntimeException {
    public static final String MSG = "Указанный ресурс не был найден";

    public ResourceNotFound() {
        super(MSG);
    }
}
