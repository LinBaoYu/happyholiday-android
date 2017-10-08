package life.happyholiday.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Formatting string
 * <p>
 * Created by liuyang on 10/8/2017.
 */

public class StringHelper {

    public static String getDateString(Date date) {
        return DateFormat.getDateInstance().format(date);
    }

    public static String getDateHourMinuteString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static Date getDateFromString(String str) {
        try {
            return DateFormat.getDateInstance().parse(str);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String updateIntString(String intStr, int addValue) {
        int intVal = getIntFromString(intStr) + addValue;

        if (intVal < 0) intVal = 0;

        return "" + intVal;
    }

    public static int getIntFromString(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (Exception e) {
            return 0;
        }
    }
}
