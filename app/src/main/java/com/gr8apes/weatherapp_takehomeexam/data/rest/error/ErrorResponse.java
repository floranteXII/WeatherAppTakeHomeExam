package com.gr8apes.weatherapp_takehomeexam.data.rest.error;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

