package com.gr8apes.weatherapp_takehomeexam.data.rest.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gr8apes.weatherapp_takehomeexam.data.preference.Preferences;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lanard on 1/17/2018.
 */

public class HeaderInterceptor implements Interceptor {

    private static final String TAG = HeaderInterceptor.class.getSimpleName();
    private static String PREFS_CUSTOM_HEADERS = "PREFS_CUSTOM_HEADERS";
    private static String PREFS_INCLUDE_ADDITIONAL_HEADERS = "PREFS_INCLUDE_ADDITIONAL_HEADERS";

    private Context mContext;
    private boolean mWithAuthorization;
    private HashMap<String, String> mCustomHeaders;

    public HeaderInterceptor(final Context context, final boolean withAuthorization, final HashMap<String, String> customHeaders) {
        mContext = context;
        mWithAuthorization = withAuthorization;
        mCustomHeaders = customHeaders;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final String authKey = Preferences.getString(mContext, Preferences.API_KEY);
//        final String fcmToken = Preferences.getFcmToken();
        final String osVersion = Build.VERSION.RELEASE;

        Log.d(TAG, "token " + authKey);
//        Log.d(TAG, "fcmkey " + fcmToken);

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
//                .addHeader("X-Device-App-Version", String.format("%s+%s", BuildConfig.BUILD_VERSION,BuildConfig.BUILD_NUMBER))
                .addHeader("X-Device-OS", "android")
                .addHeader("X-Device-OS-Version", osVersion)
                .addHeader("X-Device-Manufacturer", "" + Build.MANUFACTURER)
                .addHeader("X-Device-Model", "" + Build.MODEL)
                .addHeader("X-Device-UDID", Settings.System.getString(mContext.getContentResolver(), "android_id"));
//                .addHeader("X-Device-FCM-Token", fcmToken);

        HashMap<String, String> headers = new HashMap<>(getCustomHeaders(mContext));

        if (mCustomHeaders != null) {
            headers.putAll(mCustomHeaders);
        }

        if (includeCustomHeaders(mContext)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.addHeader(key, value);
            }
        }

        if (mWithAuthorization) {
            requestBuilder.addHeader("Authorization", "" + authKey);
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }


    private static boolean includeCustomHeaders(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.getBoolean(PREFS_INCLUDE_ADDITIONAL_HEADERS, true);
    }


    private static HashMap<String, String> getCustomHeaders(Context context) {
        HashMap<String, String> headers = new HashMap<>();

        // get Headers from prefs
        String jsonPrefs = Preferences.getString(context, PREFS_CUSTOM_HEADERS);
        if (jsonPrefs.isEmpty() || jsonPrefs.contains("{}"))
            return headers;

        JsonObject jsonObjectPrefs = new JsonParser().parse(jsonPrefs).getAsJsonObject();
        JsonArray jsonArray = jsonObjectPrefs.get("headers").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            headers.put(jsonObject.get("key").getAsString(), jsonObject.get("value").getAsString());
        }
        return headers;
    }
}
