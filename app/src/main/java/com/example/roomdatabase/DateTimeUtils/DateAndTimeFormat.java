package com.example.roomdatabase.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAndTimeFormat {

    public static String getFormattedDateTime(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("d MMM yyyy, hh:mm:ss a",
                Locale.getDefault());
        String dateStr = null;
        if (date != null) {
            dateStr = formater.format(date);
        }
        return dateStr;
    }
}
