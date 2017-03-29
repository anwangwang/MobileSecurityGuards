package com.awwhome.mobilesecurityguards.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 土司工具类
 * Created by awwho on 2017/3/29.
 */
public class ToastUtil {

    /**
     * 短土司
     *
     * @param ctx  上下文环境
     * @param text 内容
     */
    public static void showShort(Context ctx, String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长土司
     *
     * @param ctx  上下文环境
     * @param text 内容
     */
    public static void showLong(Context ctx, String text) {

        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
}
