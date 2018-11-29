package com.gr8apes.weatherapp_takehomeexam.presentation.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.MediaStore;

public class ApplicationUtil {

    private final Context context;

    public ApplicationUtil(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public boolean isCameraAvailable() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public boolean isFrontCameraAvailable() {
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isInAirportMode() {
        return false;
    }

    public boolean isVersionAboveKitkat() {
        return Build.VERSION.SDK_INT > 19;
    }
}
