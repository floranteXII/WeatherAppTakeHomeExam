package com.gr8apes.weatherapp_takehomeexam.presentation.event;

/**
 * Created by LanarD on 29/11/2018.
 */
public class Event {

    public static class Refresh {
        boolean isRefresh;

        public Refresh(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        public boolean isRefresh() {
            return isRefresh;
        }
    }
}
