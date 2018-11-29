package com.gr8apes.weatherapp_takehomeexam.presentation.presenter;

import com.gr8apes.weatherapp_takehomeexam.data.rest.error.ErrorResponse;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.domain.repositories.LocationWeatherRepository;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LocationWeatherContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocationWeatherPresenter extends BasePresenter<LocationWeatherContract.View> implements LocationWeatherContract.Presenter {

    private LocationWeatherRepository mLocationWeatherRepository;

    @Inject
    public LocationWeatherPresenter(LocationWeatherRepository mLocationWeatherRepository) {
        this.mLocationWeatherRepository = mLocationWeatherRepository;
    }

    @Override
    public void getLocationWeather(String lat, String lon, String appId) {
        view.showLoading();
        unsubscribeOnUnbindView(mLocationWeatherRepository.getLocationWeather(lat, lon, appId)
                .subscribe(new Consumer<CurrentWeatherData>() {
                    @Override
                    public void accept(CurrentWeatherData currentWeatherData) throws Exception {
                        view.hideLoading();
                        view.onGetLocationWeatherSuccess(currentWeatherData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.hideLoading();
                        ErrorResponse errorResponse = getErrorResponse(throwable);
                        view.showError(String.valueOf(errorResponse.getStatus()), errorResponse.getMessage());
                    }
                }));

    }


    @Override
    public void getLocationsWeather(ArrayList<Coordinate> coordinates, String appId) {
        view.showLoading();

        List<Flowable<CurrentWeatherData>> flowables = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            flowables.add(mLocationWeatherRepository.getLocationWeather(String.valueOf(coordinate.getLat()), String.valueOf(coordinate.getLon()), appId));
        }

        unsubscribeOnUnbindView(mLocationWeatherRepository.combineLocationsWeather(flowables)
                .subscribe(new Consumer<ArrayList<CurrentWeatherData>>() {
                    @Override
                    public void accept(ArrayList<CurrentWeatherData> currentWeatherData) throws Exception {
                        view.hideLoading();
                        view.onGetLocationsWeatherSuccess(currentWeatherData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.hideLoading();
                        ErrorResponse errorResponse = getErrorResponse(throwable);
                        view.showError(String.valueOf(errorResponse.getStatus()), errorResponse.getMessage());
                    }
                }));
    }
}
