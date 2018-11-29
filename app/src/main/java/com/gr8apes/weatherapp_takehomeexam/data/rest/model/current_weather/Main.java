package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class Main implements Serializable {

    @SerializedName("temp")
    @Expose
    double temp;

    @SerializedName("pressure")
    @Expose
    double pressure;

    @SerializedName("humidity")
    @Expose
    double humidity;

    @SerializedName("temp_min")
    @Expose
    double tempMin;

    @SerializedName("temp_max")
    @Expose
    double tempMax;

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
