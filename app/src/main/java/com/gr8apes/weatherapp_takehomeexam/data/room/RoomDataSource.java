package com.gr8apes.weatherapp_takehomeexam.data.room;

import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CurrentWeatherDataEntity;
import com.gr8apes.weatherapp_takehomeexam.domain.usecases.LocationWeatherUseCase;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class RoomDataSource implements LocationWeatherUseCase {

    private AppDatabase appDatabase;

    public RoomDataSource(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId) {
        return null;
    }

    public CurrentWeatherDataEntity getLocationWeather(CurrentWeatherData currentWeatherData) {
        appDatabase.currentWeatherDataDao().insertOrUpdateCurrentWeather(new CurrentWeatherDataEntity(currentWeatherData));
        return appDatabase.currentWeatherDataDao().getWeather(currentWeatherData.getId());
    }

    public CurrentWeatherDataEntity getLocationWeather(int locationId) {
        return appDatabase.currentWeatherDataDao().getWeather(locationId);
    }


    public List<CurrentWeatherDataEntity> getLocationWeathers() {
        return appDatabase.currentWeatherDataDao().getLocationCurrentWeatherList();
    }
}
