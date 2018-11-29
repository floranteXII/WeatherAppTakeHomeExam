package com.gr8apes.weatherapp_takehomeexam.presentation.utility;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by LanarD on 1/12/2018.
 */


public class Preferences {

    public static String MOBILE = "MOBILE";
    public final static String FIRST_TIME_OPEN = "FIRST_TIME_OPEN";
    public final static String API_KEY = "API_KEY";
    public final static String FCM_TOKEN = "FCM_TOKEN";
    public final static String PIN_TIMESTAMP = "PIN_TIMESTAMP";
    public final static String PIN_TRIES = "PIN_TRIES";
    public final static String LOCK_PHONE = "LOCK_PHONE";
    public final static String OTP_BLACKLIST_TIMESTAMP = "OTP_BLACKLIST_TIMESTAMP";
    public final static String OTP_BLACKLIST_LOCK = "OTP_BLACKLIST_LOCK";
    public final static String BASE_URL = "BASE_URL";


    public Preferences() {
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.getBoolean(key, false);
    }

    public static float getFloat(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.getFloat(key, 0.0F);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.getInt(key, 0);
    }

    public static boolean clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        return sharedPreferences.edit().clear().commit();
    }

}
