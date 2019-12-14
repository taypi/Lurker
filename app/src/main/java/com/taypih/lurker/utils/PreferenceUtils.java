package com.taypih.lurker.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String KEY_BOOLEAN_PREF = "pref_data_source";

    public static void saveListTypePref(Context context, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(KEY_BOOLEAN_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_BOOLEAN_PREF, value);
        editor.apply();
    }

    public static boolean getListTypePref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(KEY_BOOLEAN_PREF,
                Context.MODE_PRIVATE);
        return sharedPref.getBoolean(KEY_BOOLEAN_PREF, true);
    }
}
