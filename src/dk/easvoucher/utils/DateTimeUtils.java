package dk.easvoucher.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String dateToDanishFormat(Date date){
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String timeToDanishFormat(Time time){
        return time.toString().substring(0, 5);
    }
}
