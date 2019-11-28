package com.example.pennypig.Helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    Date date;

    public DateHelper(Date date) {
        this.date = date;
    }

    public DateHelper() {

    }

    public String getGMTDate() {
        DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        return gmtFormat.format(new Date());
    }

    public static Date GMTtoLocalTime(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
        inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        Date gmtDate = new Date();

        try {
            gmtDate = inputFormat.parse(date);
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return gmtDate;
    }
}
