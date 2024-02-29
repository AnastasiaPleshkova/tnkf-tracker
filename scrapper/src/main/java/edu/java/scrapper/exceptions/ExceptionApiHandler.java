package edu.java.scrapper.exceptions;

import edu.java.scrapper.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(ChatNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse chatNotFoundException(ChatNotFound ex) {

        return new ApiErrorResponse(
            "Чат не существует",
            HttpStatus.NOT_FOUND.toString(),
            "Chat doesn't exist",
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );
    }

    @ExceptionHandler(ChatAlreadyRegistered.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse chatAlreadyRegisteredException(ChatAlreadyRegistered ex) {

        return new ApiErrorResponse(
            "Чат уже зарегистрирован",
            HttpStatus.CONFLICT.toString(),
            "Chat already exists",
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );
    }

    @ExceptionHandler(LinkNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse linkNotFoundException(LinkNotFound ex) {

        return new ApiErrorResponse(
            "Ссылка не найдена",
            HttpStatus.NOT_FOUND.toString(),
            "Link doesn't exist",
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );
    }

    @ExceptionHandler(LinkAlreadyTracked.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse linkAlreadyRegisteredException(LinkAlreadyTracked ex) {

        return new ApiErrorResponse(
            "Ссылка уже зарегистрирована",
            HttpStatus.CONFLICT.toString(),
            "Link has been already tracked",
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );
    }

    @ExceptionHandler(UnsupportedLink.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse unsupportedLinkException(UnsupportedLink ex) {
        return new ApiErrorResponse(
            "Ссылка не поддерживается",
            HttpStatus.BAD_REQUEST.toString(),
            "Link doesn't supported",
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {

        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            ex.getStatusCode().toString(),
            ex.getBody().getTitle(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        );

    }
}
