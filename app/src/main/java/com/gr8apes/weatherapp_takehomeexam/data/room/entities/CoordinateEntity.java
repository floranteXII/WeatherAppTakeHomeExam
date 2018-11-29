package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;

import java.io.Serializable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class CoordinateEntity implements Serializable {

    double lat;

    double lon;

    public CoordinateEntity() {
    }

    public CoordinateEntity(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public CoordinateEntity(Coordinate coordinate) {
        this.lat = coordinate.getLat();
        this.lon = coordinate.getLon();
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
