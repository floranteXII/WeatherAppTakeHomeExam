package com.gr8apes.weatherapp_takehomeexam.data.room.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CoordinateEntity;

/**
 * Created by LanarD on 29/11/2018.
 */
public class CoordinateEntityConverter {

    @TypeConverter
    public static CoordinateEntity fromString(String screenshotsJson) {
        if (screenshotsJson == null) return null;

        Gson gson = new Gson();
        CoordinateEntity screenshots = gson.fromJson(screenshotsJson, CoordinateEntity.class);
        return screenshots;
    }

    @TypeConverter
    public static String toString(CoordinateEntity weatherEntity) {
        if (weatherEntity == null) return null;

        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(weatherEntity);
        return screenshotsJson;
    }
}
