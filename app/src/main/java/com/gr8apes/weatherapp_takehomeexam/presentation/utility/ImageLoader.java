package com.gr8apes.weatherapp_takehomeexam.presentation.utility;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gr8apes.weatherapp_takehomeexam.R;

public class ImageLoader {

    public static void loadMedia(Context context, final ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .into(imageView);
    }

    public static void loadProfilePicture(Context context, final ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

    public static void loadMediaFitCenter(Context context, final ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .fitCenter()
                .into(imageView);
    }

    public static void loadMediaFromDrawableFitCenter(Context context, final ImageView imageView, int drawable) {
        Glide.with(context)
                .load(drawable)
                .asBitmap()
                .fitCenter()
                .into(imageView);
    }

    public static void loadMediaFromDrawable(Context context, final ImageView imageView, final Integer resourceId) {
        Glide.with(context)
                .load(resourceId)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

}
