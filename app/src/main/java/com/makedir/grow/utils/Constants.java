package com.makedir.grow.utils;

import android.content.Context;

import com.dvinfosys.widgets.ToastView.ToastView;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Constants {

    public static String server = "Server Not Responding ";

    public static boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public static String getCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        String startDate = currentDay + "-" + (currentMonth + 1) + "-" + currentYear;
        return startDate;
    }

    public static void getSuccessToast(Context context, String text) {
        ToastView.success(context, text, ToastView.LENGTH_SHORT).show();
    }

    public static void getErrorToast(Context context, String text) {
        ToastView.error(context, text, ToastView.LENGTH_SHORT).show();
    }

}
