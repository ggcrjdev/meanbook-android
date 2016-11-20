package com.brilgo.meanbookandroidapp.api.response;

/**
 * Created by Gustavo on 20/11/2016.
 */

public class ErrorResponse {

    public final String type;
    public final String summary;
    public final String detail;
    public final Integer code;
    public final String cause;

    public ErrorResponse(String type, String summary, String detail, Integer code, String cause) {
        this.type = type;
        this.summary = summary;
        this.detail = detail;
        this.code = code;
        this.cause = cause;
    }
}
