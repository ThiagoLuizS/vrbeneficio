package br.com.vrbeneficio.exception;

public class ValidarCartaoException extends RuntimeException{
    public ValidarCartaoException(String msg) {
        super(msg);
    }

    public ValidarCartaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
