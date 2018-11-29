package com.gr8apes.weatherapp_takehomeexam.presentation.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Main;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LocationWeatherContract;
import com.gr8apes.weatherapp_takehomeexam.presentation.fragment.LocationWeatherListFragment;
import com.gr8apes.weatherapp_takehomeexam.presentation.fragment.RefreshButtonContainerFragment;
import com.gr8apes.weatherapp_takehomeexam.presentation.presenter.LocationWeatherPresenter;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.GeneralUtils;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ImageLoader;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements
        HasSupportFragmentInjector,
        LocationWeatherContract.View {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

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

    @BindView(R.id.latitudeTextView)
    AppCompatTextView mLatitudeTextView;

    @BindView(R.id.longitudeTextView)
    AppCompatTextView mLongitudeTextView;

    @BindView(R.id.windTextView)
    AppCompatTextView mWindTextView;

    @BindView(R.id.weatherIconImageView)
    AppCompatImageView mWeatherIconImageView;

    @BindView(R.id.currentLocationContainer)
    LinearLayout mCurrentLocationContainer;

    @BindView(R.id.loadingContainer)
    LinearLayout mLoadingContainer;

    @BindView(R.id.retryContainer)
    LinearLayout mRetryContainer;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    LocationWeatherPresenter mLocationWeatherPresenter;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient.ConnectionCallbacks mConnectionCallbacks;
    private GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener;
    private Location mLastLocation;

    public static void newActivity(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(R.color.colorTransparent, "");
        initFragments();
        initPresenters();
        checkRequiredPermissions();
    }

    private void initPresenters() {
        mLocationWeatherPresenter.bindView(this);
    }

    private void initFragments() {
        replaceFragment(R.id.listContainer, LocationWeatherListFragment.newInstance());
        replaceFragment(R.id.refreshContainer, RefreshButtonContainerFragment.newInstance());
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationWeatherPresenter.unBindView();
    }

    private void checkRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForPermissions(REQUEST_CODE_PERMISSION_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    protected void permissionGranted(int requestCode) {
        if (requestCode == REQUEST_CODE_PERMISSION_LOCATION) {
            initGoogleApiCLient();
        }
    }

    @Override
    protected void permissionDenied(String message) {
        super.permissionDenied(message);
        getCurrentLocationWeather(null);
    }

    private void initGoogleApiCLient() {
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.disconnect();
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    @SuppressLint("MissingPermission")
    protected synchronized void buildGoogleApiClient() {
        mConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(1000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                builder.setAlwaysShow(true);

                Log.d("asdasd", "connected na boi");

                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                Log.i(TAG, "All location settings are satisfied.");
                                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the result
                                    // in onActivityResult().
                                    status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                                break;
                        }
                    }
                });
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Connection suspended");
            }
        };

        mOnConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d(TAG, "Connection failed");
            }
        };
        mGoogleApiClient = null;
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(mConnectionCallbacks)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        try {
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            getCurrentLocationWeather(mLastLocation);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
        }
    }

    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged: " + location.toString());
            if (mLastLocation == null) {
                mLastLocation = location;
                getCurrentLocationWeather(mLastLocation);
                removeLocationUpdatesAndDisconnect();
            }
        }
    };

    private void removeLocationUpdatesAndDisconnect() {
        if (mGoogleApiClient != null) {

            mGoogleApiClient.unregisterConnectionCallbacks(mConnectionCallbacks);
            mGoogleApiClient.unregisterConnectionFailedListener(mOnConnectionFailedListener);

            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
            }

            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;

            System.gc();
        }
    }

    private void getCurrentLocationWeather(Location mLastLocation) {
        if (mLastLocation == null) {
            mCurrentLocationContainer.setVisibility(View.GONE);
            mLoadingContainer.setVisibility(View.GONE);
            mRetryContainer.setVisibility(View.VISIBLE);
            return;
        }
        String appId = "d5d3c9a2177f318e37537184413ecccd";
        mLocationWeatherPresenter.getLocationWeather(String.valueOf(mLastLocation.getLatitude()),
                String.valueOf(mLastLocation.getLongitude()),
                appId);
    }

    @Override
    public void onGetLocationWeatherSuccess(CurrentWeatherData currentWeatherData) {
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
        mLatitudeTextView.setText(Location.convert(mLastLocation.getLatitude(), Location.FORMAT_DEGREES));
        mLongitudeTextView.setText(Location.convert(mLastLocation.getLongitude(), Location.FORMAT_DEGREES));
        mWindTextView.setText(String.format(Locale.US, "%.2fKPH", currentWeatherData.getWind().getSpeed() * 3.6));
    }

    @Override
    public void onGetLocationsWeatherSuccess(ArrayList<CurrentWeatherData> currentWeatherDatas) {
        //Disregard
    }

    @Override
    public void showLoading() {
        mRetryContainer.setVisibility(View.GONE);
        mCurrentLocationContainer.setVisibility(View.GONE);
        mLoadingContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mRetryContainer.setVisibility(View.GONE);
        mCurrentLocationContainer.setVisibility(View.VISIBLE);
        mLoadingContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.retryContainer)
    void onClick() {
        checkRequiredPermissions();
    }

}
