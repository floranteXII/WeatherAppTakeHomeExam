package com.gr8apes.weatherapp_takehomeexam.data.rest.error;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}