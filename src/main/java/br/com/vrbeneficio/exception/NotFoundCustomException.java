package br.com.vrbeneficio.exception;

public class NotFoundCustomException extends RuntimeException{
    public NotFoundCustomException(String msg) {
        super(msg);
    }

    public NotFoundCustomException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
