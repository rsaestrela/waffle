package io.github.rsaestrela.waffle.exception;


public class WaffleException extends Exception {

    public WaffleException(String message) {
        super(message);
    }

    public WaffleException(Throwable cause) {
        super(cause);
    }
}
