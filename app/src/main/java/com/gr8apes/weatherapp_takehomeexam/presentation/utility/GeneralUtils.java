package com.gr8apes.weatherapp_takehomeexam.presentation.utility;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gr8apes.weatherapp_takehomeexam.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {


    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static void hideKeyboard(Context mContext) {
        try {
            View view = ((Activity) mContext).getWindow().getCurrentFocus();
            if (view != null && view.getWindowToken() != null) {
                IBinder binder = view.getWindowToken();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binder, 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context mContext) {
        hideKeyboard(mContext);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static boolean isPhoneValid(String num) {
        if (num.length() < 7)
            return false;
        else
            return true;
    }


    public static Drawable resizeDrawable(Context mContext, Drawable image, int width, int height) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(mContext.getResources(), bitmapResized);
    }

    public static void updateBadgeHotCount(final TextView badgeCountTextView, final long count) {
        if (count == 0)
            badgeCountTextView.setVisibility(View.GONE);
        else {
            badgeCountTextView.setVisibility(View.VISIBLE);
            badgeCountTextView.setText(Long.toString(count));
        }
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayofMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayofWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDateString() {
        return String.format("%02d/%02d", (getMonth() + 1), getDayofMonth()) + "/" + getYear4digits();
    }

    public static String getDateMonthDayString() {
        return String.format("%02d/%02d", (getMonth() + 1), getDayofMonth());
    }

    public static String getTimeStamp() {
        return Calendar.getInstance().getTime().toString();
    }

    public static String getYear2digits() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String year = df.format(Calendar.getInstance().getTime());
        return year;
    }
    public static String getYear4digits() {
        DateFormat df = new SimpleDateFormat("yyyy"); // Just the year, with 2 digits
        String year = df.format(Calendar.getInstance().getTime());
        return year;
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getHourOfDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static long setDatePickerMaxDate(int dayToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dayToAdd);
        return calendar.getTimeInMillis();
    }

    public static long setTimePickerMinTime(int hourToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hourToAdd);
        return calendar.getTimeInMillis();
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        return dateFormat.format(Calendar.getInstance().getTime());
    }


    public static int getScrollViewHeight(ScrollView scrollView) {
        return scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
    }

    public static boolean isScrollViewScrollable(ScrollView scrollView) {
        View child = scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = (child).getHeight();
            return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }

    public static String resize(int size) {
        return "?width=" + size;
    }

    public static String convert24hourTo12Hour(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("hh:mm a").format(dateObj));
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String convertTimeStampToDate(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(dateObj));
            return new SimpleDateFormat("MM/dd/yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String convertTimeStampToHour(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("hh:mm a").format(dateObj));
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String convert12hourTo24Hour(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("HH:mm").format(dateObj));
            return new SimpleDateFormat("HH:mm").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String convertToDateFormatNoSeparation(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yyyy");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("yyyyMMdd").format(dateObj));
            return new SimpleDateFormat("yyyyMMdd").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String convertToDateFormatWithSeparation(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("MM/d/yyyy").format(dateObj));
            return new SimpleDateFormat("MM/d/yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String convertToWordDateFormat(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("MMM dd, yyyy").format(dateObj));
            return new SimpleDateFormat("MMM dd, yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static boolean isVersionAboveKitkat() {
        return Build.VERSION.SDK_INT > 19;
    }


    public static String convertDateWithTimeToDate(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("MMMM dd, yyyy").format(dateObj));
            return new SimpleDateFormat("MMMM dd, yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String convertDateWithTimeToDisplayDate(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("MMM dd, yyyy").format(dateObj));
            return new SimpleDateFormat("MMM dd, yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String convertDateWithTimeToTime(String date) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            final Date dateObj = sdf.parse(date);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("hh:mm a").format(dateObj));
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String getCurrentDayOfWeek(String date) {
        int dayOfWeek = -100;

        try {
            Date selectedDate = new SimpleDateFormat("MM/d/yyyy").parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(selectedDate);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            Log.e("getCurrentDayOfWeek: ", e.getMessage());
        }


        Log.i("getCurrentDay", "getCurrentDay: " + getDayofWeek() + " " + Calendar.MONDAY);
        if (dayOfWeek == Calendar.MONDAY) {
            return "MONDAY";
        } else if (dayOfWeek == Calendar.TUESDAY) {
            return "TUESDAY";
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            return "WEDNESDAY";
        } else if (dayOfWeek == Calendar.THURSDAY) {
            return "THURSDAY";
        } else if (dayOfWeek == Calendar.FRIDAY) {
            return "FRIDAY";
        } else if (dayOfWeek == Calendar.SATURDAY) {
            return "SATURDAY";
        } else if (dayOfWeek == Calendar.SUNDAY) {
            return "SUNDAY";
        } else
            return "";
    }

    public static Date stringToDate(String dateFormat, String dateStringToFormat) {
        Date openTimeDate = null;
        try {
            openTimeDate = new SimpleDateFormat(dateFormat).parse(dateStringToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(openTimeDate);
        return calendar1.getTime();
    }

    public static Calendar stringToCalendar(String dateFormat, String dateStringToFormat) {
        Date openTimeDate = null;
        try {
            openTimeDate = new SimpleDateFormat(dateFormat).parse(dateStringToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(openTimeDate);
        return calendar1;
    }

    public static void longLog(String tag, String str) {
        if (str.length() > 4000) {
            Log.d(tag, str.substring(0, 4000));
            longLog(tag, str.substring(4000));
        } else
            Log.d(tag, str);
    }

    public static String toTwoDecimalPlaces(double value) {
        return new Formatter().format("%.2f", value).toString();
    }

    public static String toPesoWithTwoDecimalPlaces(double value) {
        return "₱" + new Formatter().format("%,.2f", value).toString();
    }

    public static int getDrawableFromResource(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static void goToAppSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cn = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        boolean isNetworkAvailable;
        if (nf != null && nf.isConnected()) {
            isNetworkAvailable = true;
        } else {
            isNetworkAvailable = false;
        }

        nf = null;
        cn = null;
        context = null;

        return isNetworkAvailable;
    }

    public static void setDefaultRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter face) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(face);
        face.notifyDataSetChanged();
    }


    public static void setDefaultHorizontalRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter face) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(face);
        face.notifyDataSetChanged();
    }


    public static String toDistanceFormat(double distance) {
        return new DecimalFormat("#0.00").format(distance);
    }

    public static String getCurrentDeviceDay() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return dayFormat.format(calendar.getTime());
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }


    public static void backgroundTintPreLollipopSupport(Context context, View view, int color) { //for non button views
        view.getBackground().setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
    }

    public static void backgroundTintPreLollipopSupportStringParams(View view, String color) { //for non button views
        view.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
    }

    public static void imageTintPreLollipopSupport(Context context, View view, int color) { //for non button views
        if (view instanceof ImageView) {
            ((ImageView) view).setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }


    public static String getJSONObjectString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static boolean isViewsValidated(Context context, View... views) {
//        for (View view : views) {
//            if (view instanceof EditText) {
//                if (((EditText) view).getText().toString().isEmpty()) {
//                    GeneralUtils.displayWarning(context, "Please fill up required fields");
//                    return false;
//                }
//            } else if (view instanceof TextView) {
//                if (((TextView) view).getText().toString().isEmpty()) {
//                    GeneralUtils.displayWarning(context, "Please fill up required fields");
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    public static boolean isValidEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static String getMetadataValue(Context context, String key) {
        ApplicationInfo app = null;
        try {
            app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            return bundle.getString(key, "");
        } catch (Exception var4) {
            var4.printStackTrace();
            return "";
        }
    }

    public static boolean gotoAccessibilitySettings(Context context) {
        Intent settingsIntent = new Intent(
                Settings.ACTION_ACCESSIBILITY_SETTINGS);
        if (!(context instanceof Activity)) {
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        boolean isOk = true;
        try {
            context.startActivity(settingsIntent);
        } catch (ActivityNotFoundException e) {
            isOk = false;
        }
        return isOk;
    }

    public static boolean isHighTextContrastEnabled(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        Class clazz = am.getClass();
        Method m = null;
        try {
            m = clazz.getMethod("isHighTextContrastEnabled", null);
        } catch (NoSuchMethodException e) {
            Log.w("FAIL", "isHighTextContrastEnabled not found in AccessibilityManager");
        }

        Object result = null;
        try {
            result = m.invoke(am, null);
            if (result != null && result instanceof Boolean) {
                Boolean b = (Boolean) result;
                Log.d("result", "b =" + b);
                return b;
            }
        } catch (Exception e) {
            Log.d("fail", "isHighTextContrastEnabled invoked with an exception" + e.getMessage());
        }
        return false;
    }

    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }


    public static String getAppVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void startNewActivity(Context context, Class toActivityClass) {
        Intent intent = new Intent(context, toActivityClass);
        context.startActivity(intent);
    }

    public static boolean isValidCan(String can) {

        if (can.length() != 10) {
            return false;
        }

        int[] keys = {9, 5, 6, 3, 9, 4, 1, 3, 2, 8, 3, 7, 6, 5, 1};

        String realCan = "637805" + can;
        int canSum = 0;
        for (int i = 0; i < realCan.length() - 1; i++)
            canSum += Integer.parseInt(String.valueOf(realCan.charAt(i))) * keys[i];

        String checkDigit = String.valueOf(realCan.charAt(realCan.length() - 1));
        String canSumLastDigit = (canSum % 11 == 10) ? "0" : String.valueOf(canSum % 11);
        return canSumLastDigit.equalsIgnoreCase(checkDigit);
    }


//    public static void displayError(Context context, String error) {
//        SingleButtonDialog singleButtonDialog = SingleButtonDialog.build(CommonDialogFragment.NEGATIVE,
//                error,
//                "",
//                "Okay",
//                SingleButtonDialog.BUTTON_THEME_DARK);
//        singleButtonDialog.show(((FragmentActivity) context).getSupportFragmentManager(), context.getClass().getSimpleName());
//    }
//
//    public static void displayWarning(Context context, String error) {
//        SingleButtonDialog singleButtonDialog = SingleButtonDialog.build(CommonDialogFragment.WARNING,
//                error,
//                "",
//                "Okay",
//                SingleButtonDialog.BUTTON_THEME_DARK);
//        singleButtonDialog.setCancelable(false);
//        singleButtonDialog.show(((FragmentActivity) context).getSupportFragmentManager(), context.getClass().getSimpleName());
//    }
//
//    public static void displaySuccess(Context context, String message) {
//        SingleButtonDialog singleButtonDialog = SingleButtonDialog.build(CommonDialogFragment.POSITIVE,
//                message,
//                "",
//                "Okay",
//                SingleButtonDialog.BUTTON_THEME_DARK);
//        singleButtonDialog.show(((FragmentActivity) context).getSupportFragmentManager(), context.getClass().getSimpleName());
//    }

    public static boolean validateName(String name) {
        String allowed = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLKMNOPQRSTUVWXYZ.-ñÑ";
        String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLKMNOPQRSTUVWXYZ";

        for (char a : name.toCharArray()) {
            if (!allowed.contains(String.valueOf(a))) {
                return false;
            }
        }

        for (int i = 1; i < name.length(); i++) {
            if (name.charAt(i) == '-') {
                if (!alphabets.contains(String.valueOf(name.charAt(i - 1)))) {
                    return false;
                }
            }
            if (name.charAt(i) == '.') {
                if (!alphabets.contains(String.valueOf(name.charAt(i - 1)))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidHyphenCount(String name) {
        int ctr = 0;
        for (char a : name.toCharArray()) {
            if (String.valueOf(a).equals("-")) {
                ctr++;
            }
        }
        return ctr < 2;
    }

    public static boolean isValidPeriodCount(String name) {
        int ctr = 0;
        for (char a : name.toCharArray()) {
            if (String.valueOf(a).equals(".")) {
                ctr++;
            }
        }
        return ctr < 2;
    }

    public static boolean isAllowedSpecialCharsPlacedWell(String name) {

        if (name.startsWith("-")) {
            return false;
        }

        if (name.endsWith("-")) {
            return false;
        }

        if (name.startsWith(".")) {
            return false;
        }

        return true;
    }

//    public static Class getActivity(String name) {
//        Class<?> aClass = null;
//        try {
//            aClass = Class.forName(name);
//        } catch (ClassNotFoundException e) {
//            aClass = LandingPageActivity.class; //catch if no class found with the given name
//            e.printStackTrace();
//        }
//        return aClass;
//    }

    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open( filename + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getSecondsIntervalOfStringDateFromCurrentTime(String previousTime){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss:SSS a");

        Calendar prevCalendar = GeneralUtils.stringToCalendar("yyyy/MM/dd hh:mm:ss:SSS a", previousTime);
        Calendar currentCalendar = GeneralUtils.stringToCalendar("yyyy/MM/dd hh:mm:ss:SSS a", simpleDateFormat.format(Calendar.getInstance().getTime()));

        //Calculation of gap
        long elapsed = prevCalendar.getTime().getTime() - currentCalendar.getTime().getTime();
        int hours = (int) Math.floor(elapsed / 3600000);
        int minutes = (int) Math.floor((elapsed - hours * 3600000) / 60000);
        int seconds = (int) Math.floor((elapsed - hours * 3600000 - minutes * 60000) / 1000);

        return seconds;
    }

    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }


    public static JsonObject loadJsonObjectFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json/" + fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return generateJsonObjectFromString(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JsonObject generateJsonObjectFromString(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public static String parseLocationToPretty(double latitude, double longitude) {
        StringBuilder builder = new StringBuilder();

        if (latitude < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }

        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        builder.append(" ");

        if (longitude < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");

        return builder.toString();
    }

    public static String parseIconUrl(String iconName){
        return "http://openweathermap.org/img/w/" + iconName+ ".png";
    }

}
