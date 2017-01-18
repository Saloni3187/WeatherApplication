package com.weatherapplication.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Saloni on 22/11/16.
 */

public class DateUtils {
    /**
     * Method will take string input and convert the input to timestamp
     * @param dateTime
     * @return
     */
    public String getTimeStamp(String dateTime) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(Long.parseLong(dateTime)*1000L);
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        return dayOfWeek;
    }
}
