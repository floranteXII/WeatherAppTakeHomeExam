package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by LanarD on 29/11/2018.
 */

@Entity
public class CurrentWeatherDataEntity {
    @PrimaryKey
    @NonNull
    private int id;

    public CurrentWeatherDataEntity(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
