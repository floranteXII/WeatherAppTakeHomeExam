package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Main;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class MainEntity implements Serializable {

    @Expose
    public double temp;

    @Expose
    public double pressure;

    @Expose
    public double humidity;

    @Expose
    public double tempMin;

    @Expose
    public double tempMax;

    public MainEntity() {
    }

    public MainEntity(Main main) {
        this.temp = main.getTemp();
        this.pressure = main.getPressure();
        this.humidity = main.getHumidity();
        this.tempMin = main.getTempMin();
        this.tempMax = main.getTempMax();
    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
}
