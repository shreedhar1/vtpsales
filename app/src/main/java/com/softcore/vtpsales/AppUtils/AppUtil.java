package com.softcore.vtpsales.AppUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppUtil {
    public static void showTost(Context context, String message) {

        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
    }
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(View view, String message) {
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void saveStringData(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringData(Context context, String key, String defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultValue);
    }

    public static String convertTo12HourFormat(String time) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.US);
            Date dateObj = sdf.parse(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a", Locale.US);
            return sdf2.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormat(String inputDate) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date date = inputFormat.parse(inputDate);
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return "";
    }

    public static int calculateAge(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Return 0 if parsing fails
        }

        Calendar birthDate = Calendar.getInstance();

        birthDate.setTime(date);

        Calendar currentDate = Calendar.getInstance();
      //  currentDate.set(2078, Calendar.MARCH, 15);  //test Date

        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
    public static boolean isToday(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);


        cal2.setTime(new Date());
      //  cal2.set(2024, Calendar.MARCH, 19); //Test Date

        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    public static String convertMonthYearFormat(String monthYear) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yy");

        try {
            Date date = inputFormat.parse(monthYear);



            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
