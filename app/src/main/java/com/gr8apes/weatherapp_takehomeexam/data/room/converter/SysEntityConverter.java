package com.gr8apes.weatherapp_takehomeexam.data.room.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.MainEntity;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.SysEntity;

/**
 * Created by LanarD on 29/11/2018.
 */
public class SysEntityConverter {
    @TypeConverter
    public static SysEntity fromString(String screenshotsJson) {
        if (screenshotsJson == null) return null;

        Gson gson = new Gson();
        SysEntity screenshots = gson.fromJson(screenshotsJson, SysEntity.class);
        return screenshots;
    }

    @TypeConverter
    public static String toString(SysEntity mainEntity) {
        if (mainEntity == null) return null;

        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(mainEntity);
        return screenshotsJson;
    }
}
