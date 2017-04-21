package com.awwhome.mobilesecurityguards.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 服务工具类
 * Created by awwho on 2017/4/21.
 */
public class ServiceUtil {

    /**
     * 根据服务名称判断服务是否在运行
     *
     * @param ctx         上下文环境
     * @param serviceName 服务名称
     * @return true 在运行 false 不在运行
     */
    public static boolean isRunning(Context ctx, String serviceName) {

        // 1.获取ActivityManager对象
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        // 2.获取运行的service(获取的最大数)
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        // 3.遍历集合
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            // 4.比对结果
            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
