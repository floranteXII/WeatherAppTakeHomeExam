package com.gr8apes.weatherapp_takehomeexam.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gr8apes.weatherapp_takehomeexam.data.rest.ApiService;
import com.gr8apes.weatherapp_takehomeexam.di.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ApiServiceModule {


    @Provides
    @AppScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(ApiService.BASE_URL)
                .build();
    }

    @Provides
    @AppScope
    public ApiService provideAvonApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }




}