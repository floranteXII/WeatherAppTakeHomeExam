package com.gr8apes.weatherapp_takehomeexam.data.room.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WeatherEntity;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WindEntity;

import java.util.ArrayList;

/**
 * Created by LanarD on 29/11/2018.
 */
public class WindEntityConverter {
    @TypeConverter
    public static WindEntity fromString(String screenshotsJson) {
        if (screenshotsJson == null) return null;

        Gson gson = new Gson();
        WindEntity screenshots = gson.fromJson(screenshotsJson, WindEntity.class);
        return screenshots;
    }

    @TypeConverter
    public static String toString(WindEntity weatherEntity) {
        if (weatherEntity == null) return null;

        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(weatherEntity);
        return screenshotsJson;
    }
}
