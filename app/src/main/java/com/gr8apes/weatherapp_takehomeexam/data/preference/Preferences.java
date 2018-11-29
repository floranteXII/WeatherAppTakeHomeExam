package com.gr8apes.weatherapp_takehomeexam.data.preference;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by LanarD on 1/12/2018.
 */


public class Preferences {

    public static String MOBILE = "MOBILE";
    public final static String LANGUAGE = "LANGUAGE";
    public static final String API_KEY = "API_KEY";
    public static final String LAST_LOC_ID = "LAST_LOC_ID";

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
