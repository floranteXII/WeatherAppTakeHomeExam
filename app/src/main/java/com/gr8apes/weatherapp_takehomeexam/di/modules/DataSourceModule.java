package com.gr8apes.weatherapp_takehomeexam.di.modules;

import com.gr8apes.weatherapp_takehomeexam.data.rest.ApiService;
import com.gr8apes.weatherapp_takehomeexam.data.rest.RestDataSource;
import com.gr8apes.weatherapp_takehomeexam.data.room.AppDatabase;
import com.gr8apes.weatherapp_takehomeexam.data.room.RoomDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LanarD on 29/11/2018.
 */

@Module
public class DataSourceModule {

    @Provides
    RestDataSource providesRestDataSource(ApiService apiService) {
        return new RestDataSource(apiService);
    }

    @Provides
    RoomDataSource providesRoomDataSource(AppDatabase appDatabase) {
        return new RoomDataSource(appDatabase);
    }
}
