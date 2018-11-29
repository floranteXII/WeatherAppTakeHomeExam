package com.gr8apes.weatherapp_takehomeexam.domain.repositories;

import android.util.Log;

import com.gr8apes.weatherapp_takehomeexam.data.rest.RestDataSource;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.data.room.RoomDataSource;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CurrentWeatherDataEntity;
import com.gr8apes.weatherapp_takehomeexam.domain.usecases.LocationWeatherUseCase;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ModelUtil;

import org.reactivestreams.Publisher;

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
        return getRestDataSource().getLocationWeather(lat, lon, appId).compose(this.<CurrentWeatherData>applySchedulers())
                .flatMap(new Function<CurrentWeatherData, Publisher<CurrentWeatherData>>() {
                    @Override
                    public Publisher<CurrentWeatherData> apply(CurrentWeatherData currentWeatherData) throws Exception {
                        return Flowable.just(new CurrentWeatherData(getRoomDataSource().getLocationWeather(currentWeatherData)));
                    }
                });
    }

    public Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId, final int locationId) {
        return getRestDataSource().getLocationWeather(lat, lon, appId).compose(this.<CurrentWeatherData>applySchedulers())
                .flatMap(new Function<CurrentWeatherData, Publisher<CurrentWeatherData>>() {
                    @Override
                    public Publisher<CurrentWeatherData> apply(CurrentWeatherData currentWeatherData) throws Exception {
                        return Flowable.just(new CurrentWeatherData(getRoomDataSource().getLocationWeather(currentWeatherData)));
                    }
                }).onErrorResumeNext(new Function<Throwable, Publisher<? extends CurrentWeatherData>>() {
                    @Override
                    public Publisher<? extends CurrentWeatherData> apply(Throwable throwable) throws Exception {
                        CurrentWeatherData temp = new CurrentWeatherData(getRoomDataSource().getLocationWeather(locationId));
                        return Flowable.just(temp);
                    }
                });
    }

    public Flowable<CurrentWeatherData> getLocationWeather(final CurrentWeatherData currentWeatherData, String appId) {
        String lat = String.valueOf(currentWeatherData.getCoordinate().getLat());
        String lon = String.valueOf(currentWeatherData.getCoordinate().getLon());
        return getRestDataSource().getLocationWeather(lat, lon, appId).compose(this.<CurrentWeatherData>applySchedulers())
                .flatMap(new Function<CurrentWeatherData, Publisher<CurrentWeatherData>>() {
                    @Override
                    public Publisher<CurrentWeatherData> apply(CurrentWeatherData currentWeatherData) throws Exception {
                        return Flowable.just(new CurrentWeatherData(getRoomDataSource().getLocationWeather(currentWeatherData)));
                    }
                }).onErrorResumeNext(new Function<Throwable, Publisher<? extends CurrentWeatherData>>() {
                    @Override
                    public Publisher<? extends CurrentWeatherData> apply(Throwable throwable) throws Exception {
                        CurrentWeatherData temp = new CurrentWeatherData(getRoomDataSource().getLocationWeather(currentWeatherData));
                        return Flowable.just(temp);
                    }
                });
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
        }).compose(this.<ArrayList<CurrentWeatherData>>applySchedulers())
                .onErrorResumeNext(new Function<Throwable, Publisher<? extends ArrayList<CurrentWeatherData>>>() {
                    @Override
                    public Publisher<? extends ArrayList<CurrentWeatherData>> apply(Throwable throwable) throws Exception {
                        ArrayList<CurrentWeatherData> currentWeatherData = new ArrayList<>();
                        for (CurrentWeatherDataEntity currentWeatherDataEntity : getRoomDataSource().getLocationWeathers()) {
                            currentWeatherData.add(new CurrentWeatherData(currentWeatherDataEntity));
                        }
                        return Flowable.just(currentWeatherData);
                    }
                });
    }
}
