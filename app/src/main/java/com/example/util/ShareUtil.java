package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtil {
    private static String xml_name = "USERANDPS";

    //键 值
    public static void putBoolean(Context mContext, String key, boolean isrem) {
        SharedPreferences sp = mContext.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, isrem).apply();
    }

    // 默认值
    public static boolean getBoolean(Context mContext, String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    // 值
    public static void putString(Context mContext, String key, String str) {
        SharedPreferences sp = mContext.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        sp.edit().putString(key, str).apply();
    }

    // 默认值
    public static String getString(Context mContext, String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
