package com.gr8apes.weatherapp_takehomeexam.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LocationWeatherContract;
import com.gr8apes.weatherapp_takehomeexam.presentation.presenter.LocationWeatherPresenter;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.GeneralUtils;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;

public class WeatherDetailsActivity extends BaseActivity implements
        LocationWeatherContract.View {
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @BindView(R.id.degreeCelsiusTextView)
    AppCompatTextView mDegreeCelsiusTextView;

    @BindView(R.id.weatherDescriptiontTextView)
    AppCompatTextView mWeatherDescriptiontTextView;

    @BindView(R.id.minDegreeCelsiusTextView)
    AppCompatTextView mMinDegreeCelsiusTextView;

    @BindView(R.id.maxDegreeCelsiusTextView)
    AppCompatTextView mMaxDegreeCelsiusTextView;

    @BindView(R.id.cityTextView)
    AppCompatTextView mCityTextView;

    @BindView(R.id.countryTextView)
    AppCompatTextView mCountryTextView;

    @BindView(R.id.windTextView)
    AppCompatTextView mWindTextView;

    @BindView(R.id.weatherIconImageView)
    AppCompatImageView mWeatherIconImageView;

    @BindView(R.id.contentLinearLayout)
    LinearLayout mContentLinearLayout;

    @Inject
    LocationWeatherPresenter mLocationWeatherPresenter;

    private CurrentWeatherData mCurrentWeatherData;

    public static void newActivity(Context mContext, CurrentWeatherData currentWeatherData) {
        Intent intent = new Intent(mContext, WeatherDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, currentWeatherData);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        setToolbar(R.color.colorPrimaryDark, mCurrentWeatherData.getName());
        initPresenters();
    }

    private void initPresenters() {
        mLocationWeatherPresenter.bindView(this);
        onClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationWeatherPresenter.unBindView();
    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            mCurrentWeatherData = (CurrentWeatherData) getIntent().getSerializableExtra(EXTRA_DATA);
        }
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_weather_details;
    }

    private void initViews(CurrentWeatherData currentWeatherData) {
        if (currentWeatherData == null) return;
        String degreeCelsius = new DecimalFormat("#").format(currentWeatherData.getMain().getTemp() - 273.15);
        String minDegreeCelsius = String.format(Locale.US, "%.1f", currentWeatherData.getMain().getTempMin() - 273.15);
        String maxDegreeCelsius = String.format(Locale.US, "%.1f", currentWeatherData.getMain().getTempMax() - 273.15);

        ImageLoader.loadMedia(mContext, mWeatherIconImageView,
                GeneralUtils.parseIconUrl(currentWeatherData.getWeatherArrayList().get(0).getIcon()));

        mDegreeCelsiusTextView.setText(degreeCelsius);
        mMinDegreeCelsiusTextView.setText(minDegreeCelsius);
        mMaxDegreeCelsiusTextView.setText(maxDegreeCelsius);
        mCityTextView.setText(currentWeatherData.getName());
        mCountryTextView.setText(currentWeatherData.getSys().getCountry());
        mWeatherDescriptiontTextView.setText(currentWeatherData.getWeatherArrayList().get(0).getDescription());
        mWindTextView.setText(String.format(Locale.US, "%.2fKPH", currentWeatherData.getWind().getSpeed() * 3.6));
    }

    @OnClick(R.id.refreshButton)
    void onClick() {
        mLocationWeatherPresenter.getLocationWeather(mCurrentWeatherData, getString(R.string.app_id));
    }

    @Override
    public void onGetLocationWeatherSuccess(CurrentWeatherData currentWeatherData) {
        mCurrentWeatherData = currentWeatherData;
        initViews(mCurrentWeatherData);
    }

    @Override
    public void onGetLocationsWeatherSuccess(ArrayList<CurrentWeatherData> currentWeatherDatas) {
        //disregard
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mContentLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mContentLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(@NonNull String errorTag, String message) {
        super.showError(errorTag, message);
        mContentLinearLayout.setVisibility(View.GONE);
    }
}
