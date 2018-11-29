package com.gr8apes.weatherapp_takehomeexam.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.presentation.adapter.LocationWeatherRecyclerViewAdapter;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LocationWeatherContract;
import com.gr8apes.weatherapp_takehomeexam.presentation.event.Event;
import com.gr8apes.weatherapp_takehomeexam.presentation.presenter.LocationWeatherPresenter;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.GeneralUtils;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocationWeatherListFragment extends BaseFragment implements LocationWeatherContract.View {

    @BindView(R.id.locationWeatherRecyclerView)
    RecyclerView mLocationWeatherRecyclerView;

    @Inject
    LocationWeatherPresenter mLocationWeatherPresenter;

    private LocationWeatherRecyclerViewAdapter mLocationWeatherRecyclerViewAdapter;

    public static LocationWeatherListFragment newInstance() {
        Bundle args = new Bundle();
        LocationWeatherListFragment fragment = new LocationWeatherListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_locacation_weather_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenters();
        initAdapter();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Event.Refresh event) {
        if (event.isRefresh()) {
            initPresenters();
        }
    }

    private void initPresenters() {
        mLocationWeatherPresenter.bindView(this);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        String appId = getString(R.string.app_id);
        coordinates.add(new Coordinate(Double.parseDouble(getString(R.string.london_lat)), Double.parseDouble(getString(R.string.london_lon))));
        coordinates.add(new Coordinate(Double.parseDouble(getString(R.string.prague_lat)), Double.parseDouble(getString(R.string.prague_lon))));
        coordinates.add(new Coordinate(Double.parseDouble(getString(R.string.san_francisco_lat)), Double.parseDouble(getString(R.string.san_francisco_lon))));
        mLocationWeatherPresenter.getLocationsWeather(coordinates, appId);
    }


    private void initAdapter() {
        mLocationWeatherRecyclerViewAdapter = new LocationWeatherRecyclerViewAdapter(new ArrayList<CurrentWeatherData>(), mContext);
        GeneralUtils.setDefaultRecyclerView(mContext, mLocationWeatherRecyclerView, mLocationWeatherRecyclerViewAdapter);
    }

    @Override
    public void onGetLocationsWeatherSuccess(ArrayList<CurrentWeatherData> currentWeatherDatas) {
        int lastLocationid = Preferences.getInt(mContext, Preferences.LAST_LOC_ID);
        ArrayList<CurrentWeatherData> temp = new ArrayList<>();
        for (CurrentWeatherData currentWeatherData : currentWeatherDatas) {
            if (currentWeatherData.getId() != lastLocationid)
                temp.add(currentWeatherData);
        }
        mLocationWeatherRecyclerViewAdapter.updateList(temp);
    }

    @Override
    public void onGetLocationWeatherSuccess(CurrentWeatherData currentWeatherData) {
        //disregard
    }
}
