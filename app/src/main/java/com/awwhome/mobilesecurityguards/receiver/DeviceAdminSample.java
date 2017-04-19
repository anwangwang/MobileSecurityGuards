package com.awwhome.mobilesecurityguards.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 设备管理器广播接收者
 * Created by awwho on 2017/4/19.
 */
public class DeviceAdminSample extends DeviceAdminReceiver {

    private static final String TAG = "DeviceAdminSample";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
