package life.happyholiday.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Color configuration
 *
 * Created by liuyang on 10/2/2017.
 */

public class ColorConfigHelper {
    private static final String PREFERENCE = "HAPPYHOLLIDAY";

    public static int getPrimaryColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return Color.parseColor(sharedpreferences.getString("PRIMARY_COLOR", "#4BBCF4"));
    }

    public static int getDarkPrimaryColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return Color.parseColor(sharedpreferences.getString("DARK_PRIMARY_COLOR", "#1da4e5"));
    }

    public static int getChatBgColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return Color.parseColor(sharedpreferences.getString("CHAT_BG_COLOR", "#66ffb6c1"));
    }

    public static String getPrimaryColorString(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedpreferences.getString("PRIMARY_COLOR", "#4BBCF4");
    }

    public static String getDarkPrimaryColorString(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedpreferences.getString("DARK_PRIMARY_COLOR", "#1da4e5");
    }

    public static String getChatBgColorString(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedpreferences.getString("CHAT_BG_COLOR", "#66ffb6c1");
    }

    public static void setPrimaryColor(Context context, String newColorString) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        sharedpreferences.edit().putString("PRIMARY_COLOR", newColorString).apply();
    }

    public static void setDarkPrimaryColor(Context context, String newColorString) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        sharedpreferences.edit().putString("DARK_PRIMARY_COLOR", newColorString).apply();
    }

    public static void setChatBgColor(Context context, String newColorString) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        sharedpreferences.edit().putString("CHAT_BG_COLOR", newColorString).apply();
    }
}
