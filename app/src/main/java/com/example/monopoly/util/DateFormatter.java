package com.example.monopoly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {

    private static final String timePattern = "HH'h' mm'min' ss's'";
    private static final String datePattern = "dd-MM-yyyy";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
    public static String formatTime(Date date) {

         return timeFormat.format(date);
    }
}
