package com.gr8apes.weatherapp_takehomeexam.domain.repositories;

import com.gr8apes.weatherapp_takehomeexam.data.rest.RestDataSource;
import com.gr8apes.weatherapp_takehomeexam.data.room.RoomDataSource;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LanarD on 29/11/2018.
 */
public class BaseRepository {

    private RoomDataSource roomDataSource;
    private RestDataSource restDataSource;

    protected final FlowableTransformer flowableTransformer = new FlowableTransformer() {
        @Override
        public Publisher apply(Flowable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }
    };

    @Inject
    public BaseRepository(RoomDataSource roomDataSource, RestDataSource restDataSource) {
        this.roomDataSource = roomDataSource;
        this.restDataSource = restDataSource;
    }

    public RoomDataSource getRoomDataSource() {
        return roomDataSource;
    }

    public RestDataSource getRestDataSource() {
        return restDataSource;
    }

    @SuppressWarnings("unchecked")
    protected <T> FlowableTransformer<T,T> applySchedulers() {
        return (FlowableTransformer<T, T>) flowableTransformer;
    }


}
