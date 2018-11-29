package com.gr8apes.weatherapp_takehomeexam.data.room.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.MainEntity;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WeatherEntity;

import java.util.ArrayList;

/**
 * Created by LanarD on 29/11/2018.
 */
public class MainEntityConverter {
    @TypeConverter
    public static MainEntity fromString(String screenshotsJson) {
        if (screenshotsJson == null) return null;

        Gson gson = new Gson();
        MainEntity screenshots = gson.fromJson(screenshotsJson, MainEntity.class);
        return screenshots;
    }

    @TypeConverter
    public static String toString(MainEntity mainEntity) {
        if (mainEntity == null) return null;

        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(mainEntity);
        return screenshotsJson;
    }
}
