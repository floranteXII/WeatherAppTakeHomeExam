package com.gr8apes.weatherapp_takehomeexam.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CurrentWeatherDataEntity;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by LanarD on 29/11/2018.
 */
@Dao
public abstract class CurrentWeatherDataDao {
    @Insert(onConflict = REPLACE)
    public abstract void insertOrUpdateCurrentWeather(List<CurrentWeatherDataEntity> brochureEntity);

    @Insert(onConflict = REPLACE)
    public abstract void insertOrUpdateCurrentWeather(CurrentWeatherDataEntity currentWeatherDataEntity);

    @Query("DELETE FROM CurrentWeatherDataEntity")
    public abstract void deleteAll();

    //TODO Create a pagination for this
    @Query("SELECT * FROM CurrentWeatherDataEntity")
    public abstract List<CurrentWeatherDataEntity> getLocationCurrentWeatherList();

    @Query("SELECT * FROM CurrentWeatherDataEntity WHERE id LIKE :id ")
    public abstract CurrentWeatherDataEntity getWeather(int id);

    @Transaction
    public List<CurrentWeatherDataEntity> insertCurrentWeatherDatas(List<CurrentWeatherDataEntity> brochureEntityList) {
        insertOrUpdateCurrentWeather(brochureEntityList);
        return getLocationCurrentWeatherList();
    }

}
