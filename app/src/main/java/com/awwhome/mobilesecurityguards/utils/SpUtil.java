package com.awwhome.mobilesecurityguards.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * Created by awwho on 2017/4/3.
 */
public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 写入Boolean类型的变量至sp中
     *
     * @param ctx   上下文
     * @param key   存储节点名称
     * @param value 节点值
     */
    public static void putBoolean(Context ctx, String key, boolean value) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value).commit();
    }

    /**
     * 获取Boolean类型的变量从sp中
     *
     * @param ctx      上下文
     * @param key      存储节点名称
     * @param defValue 默认节点值
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }


    /**
     * 写入string类型的变量至sp中
     *
     * @param ctx   上下文
     * @param key   存储节点名称
     * @param value 节点值
     */
    public static void putString(Context ctx, String key, String value) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value).commit();
    }

    /**
     * 获取string类型的变量从sp中
     *
     * @param ctx      上下文
     * @param key      存储节点名称
     * @param defValue 默认节点值
     */
    public static String getString(Context ctx, String key, String defValue) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 移除Sp中的节点
     *
     * @param ctx 上下文
     * @param key 节点KEY
     */
    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key).commit();
    }
}
