package com.gr8apes.weatherapp_takehomeexam.data.rest.interceptor;

import android.content.Context;


import com.gr8apes.weatherapp_takehomeexam.data.rest.error.NoConnectivityException;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ApplicationUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!ApplicationUtil.isNetworkAvailable(mContext)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}