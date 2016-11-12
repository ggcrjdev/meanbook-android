package com.brilgo.meanbookandroidapp.api;

public class RequestApiException extends RuntimeException {

    public RequestApiException(String message) {
        super(message);
    }

    public RequestApiException(String message, Throwable e) {
        super(message, e);
    }
}
