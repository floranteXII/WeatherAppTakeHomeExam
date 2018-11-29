package com.gr8apes.weatherapp_takehomeexam.data.room.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WeatherEntity;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LanarD on 29/11/2018.
 */
public class WeatherEntityConverter {

    @TypeConverter
    public static List<WeatherEntity> fromString(String screenshotsJson) {
        if (screenshotsJson == null) return null;

        return ModelUtil.fromJson(List.class, screenshotsJson);
    }

    @TypeConverter
    public static String toString(List<WeatherEntity> weatherEntity) {
        if (weatherEntity == null) return null;

        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(weatherEntity);
        return screenshotsJson;
    }
}
