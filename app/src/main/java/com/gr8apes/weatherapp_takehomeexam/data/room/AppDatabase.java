package com.gr8apes.weatherapp_takehomeexam.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CurrentWeatherDataEntity;

/**
 * Created by LanarD on 29/11/2018.
 */

@Database(entities = {CurrentWeatherDataEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

//    public abstract BrochureDao brochureDao();
//
//    public abstract FeedDao feedDao();
//
//    public abstract LeadDao leadDao();
//
//    public abstract SalesRepDao salesRepDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "avon_data")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
