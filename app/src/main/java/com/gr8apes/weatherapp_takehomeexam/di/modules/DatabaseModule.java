package com.gr8apes.weatherapp_takehomeexam.di.modules;

import android.content.Context;


import com.gr8apes.weatherapp_takehomeexam.data.room.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }
}
