package com.gr8apes.weatherapp_takehomeexam.presentation.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocalizationUtils {
    private static Locale locale;

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        if(locale != null) {
            Locale.setDefault(locale);
        }
    }
    public static void setConfigChange(Context ctx){
        if(locale != null){
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale=locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }

    public static void changeLocale(Activity activity, String language)
    {
        final Resources res = activity.getResources();
        final Configuration conf = res.getConfiguration();
        if (language == null || language.length() == 0)
        {
            conf.locale = Locale.getDefault();
        }
        else
        {
            final int idx = language.indexOf('-');
            if (idx != -1)
            {
                final String[] split = language.split("-");
                conf.locale = new Locale(split[0], split[1].substring(1));
            }
            else
            {
                conf.locale = new Locale(language);
            }
        }

        res.updateConfiguration(conf, null);
    }
}
