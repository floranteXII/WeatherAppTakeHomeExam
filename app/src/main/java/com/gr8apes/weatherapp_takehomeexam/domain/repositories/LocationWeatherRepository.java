package com.gr8apes.weatherapp_takehomeexam.domain.repositories;

import com.gr8apes.weatherapp_takehomeexam.data.rest.RestDataSource;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.data.room.RoomDataSource;
import com.gr8apes.weatherapp_takehomeexam.domain.usecases.LocationWeatherUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocationWeatherRepository extends BaseRepository implements LocationWeatherUseCase {

    @Inject
    public LocationWeatherRepository(RoomDataSource roomDataSource, RestDataSource restDataSource) {
        super(roomDataSource, restDataSource);
    }

    @Override
    public Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId) {
        return getRestDataSource().getLocationWeather(lat, lon, appId).compose(this.<CurrentWeatherData>applySchedulers());
    }

    public Flowable<ArrayList<CurrentWeatherData>> combineLocationsWeather(List<Flowable<CurrentWeatherData>> flowables) {
        return Flowable.zip(flowables, new Function<Object[], ArrayList<CurrentWeatherData>>() {
            @Override
            public ArrayList<CurrentWeatherData> apply(Object[] objects) throws Exception {
                ArrayList<CurrentWeatherData> currentWeatherData = new ArrayList<>();
                for (Object o : objects) {
                    currentWeatherData.add((CurrentWeatherData) o);
                }
                return currentWeatherData;
            }
        }).compose(this.<ArrayList<CurrentWeatherData>>applySchedulers());
    }
}
