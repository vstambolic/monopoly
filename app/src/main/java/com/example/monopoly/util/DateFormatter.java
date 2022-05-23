package com.example.monopoly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFormatter {

    private static final String timePattern = "HH'h' mm'min' ss's'";
    private static final String datePattern = "dd-MM-yyyy";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
    public static String
    formatTime(long milliseconds) {
        long hours   = ((milliseconds / (1000*60*60)) % 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%2dh %2dmin %2ds",hours,minutes,seconds);
    }
}
