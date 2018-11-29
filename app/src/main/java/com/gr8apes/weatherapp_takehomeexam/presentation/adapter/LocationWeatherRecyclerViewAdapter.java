package com.gr8apes.weatherapp_takehomeexam.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.presentation.activity.WeatherDetailsActivity;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ImageLoader;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocationWeatherRecyclerViewAdapter extends RecyclerView.Adapter<LocationWeatherRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CurrentWeatherData> mCurrentWeatherData;
    private Context mContext;

    public LocationWeatherRecyclerViewAdapter(ArrayList<CurrentWeatherData> mCurrentWeatherData, Context mContext) {
        this.mCurrentWeatherData = mCurrentWeatherData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CurrentWeatherData currentWeatherData = mCurrentWeatherData.get(position);

        if (position == mCurrentWeatherData.size() - 1) {
            holder.dividerView.setVisibility(View.GONE);
        }

        ImageLoader.loadMedia(mContext, holder.weatherIconImageView,
                "http://openweathermap.org/img/w/" + currentWeatherData.getWeatherArrayList().get(0).getIcon() + ".png");
        holder.cityTextView.setText(currentWeatherData.getName());
        holder.countryTextView.setText(currentWeatherData.getSys().getCountry());
        String degreeCelsius = String.format(Locale.US, "%.2f", currentWeatherData.getMain().getTemp() - 273.15);
        holder.locationDegreeCelsiusTextView.setText(degreeCelsius);
        holder.descriptionTextView.setText(currentWeatherData.getWeatherArrayList().get(0).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherDetailsActivity.newActivity(mContext, currentWeatherData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCurrentWeatherData.size();
    }

    public void updateList(ArrayList<CurrentWeatherData> currentWeatherData) {
        mCurrentWeatherData = currentWeatherData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.weatherIconImageView)
        AppCompatImageView weatherIconImageView;

        @BindView(R.id.cityTextView)
        AppCompatTextView cityTextView;

        @BindView(R.id.countryTextView)
        AppCompatTextView countryTextView;

        @BindView(R.id.locationDegreeCelsiusTextView)
        AppCompatTextView locationDegreeCelsiusTextView;

        @BindView(R.id.descriptionTextView)
        AppCompatTextView descriptionTextView;

        @BindView(R.id.dividerView)
        View dividerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
