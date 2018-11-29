package com.gr8apes.weatherapp_takehomeexam.di.modules;


import android.content.Context;
import android.util.Log;


import com.gr8apes.weatherapp_takehomeexam.data.rest.interceptor.HeaderInterceptor;
import com.gr8apes.weatherapp_takehomeexam.data.rest.interceptor.ConnectivityInterceptor;
import com.gr8apes.weatherapp_takehomeexam.di.AppScope;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

    @Provides
    @AppScope
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("NetworkModule", "log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @AppScope
    public Cache provideCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    @AppScope
    public File provideFile(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @AppScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache, final Context context) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        /**
         * You can add additional headers here and pass to header interceptor
         * ex: headers.put(<HEADER_NAME>, <HEADER_VALUE>);
         */

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new ConnectivityInterceptor(context))
//                .addInterceptor(new AuthorizationInterceptor(context))
                .addInterceptor(new HeaderInterceptor(context, true, headers))
                .cache(cache)
                .build();
    }
}
