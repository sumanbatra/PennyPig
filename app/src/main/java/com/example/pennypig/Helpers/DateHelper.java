package com.example.pennypig.Helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    Date date;

    public DateHelper(Date date) {
        this.date = date;
    }

    public DateHelper() {

    }

    public String getGMTDate() {
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        return gmtFormat.format(new Date());
    }
}
