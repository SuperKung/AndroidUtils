package com.anderson.categories;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.anderson.AndroidUtils;

import java.util.Map;


/**
 * Created by cesarferreira on 9/26/14.
 */
public class prefs {

    static prefs singleton = null;

    public SharedPreferences preferences;

    SharedPreferences.Editor editor;

    protected prefs() {
        preferences = PreferenceManager.getDefaultSharedPreferences(AndroidUtils.getContext());
        editor = preferences.edit();
    }

    public static prefs get() {
        if (singleton == null) {
            singleton = new prefs();
        }
        return singleton;
    }


    public static void save(String key, boolean value) {
        get().editor.putBoolean(key, value).apply();
    }

    public static void save(String key, String value) {
        get().editor.putString(key, value).apply();
    }

    public static void save(String key, int value) {
        get().editor.putInt(key, value).apply();
    }

    public static void save(String key, float value) {
        get().editor.putFloat(key, value).apply();
    }

    public static void save(String key, long value) {
        get().editor.putLong(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return get().preferences.getBoolean(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return get().preferences.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return get().preferences.getInt(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return get().preferences.getFloat(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return get().preferences.getLong(key, defaultValue);
    }

    public static void remove(@NonNull String... keys) {
        for (String key : keys) {
            get().editor.remove(key).apply();
        }
    }


    public static boolean contains(String key) {
        return get().preferences.contains(key);
    }


    public static Map<String, ?> getAll() {
        return get().preferences.getAll();
    }


}

