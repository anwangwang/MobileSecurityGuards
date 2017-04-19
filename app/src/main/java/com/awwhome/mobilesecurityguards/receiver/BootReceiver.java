package com.awwhome.mobilesecurityguards.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 重启广播
 * Created by awwho on 2017/4/19.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: 重啓手机成功，并且成功接收了广播");

        // 1.获取开机后的手机的序列号
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();

        // 2.获取存储在SP中的手机的序列号
        String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, "");
        // 3.两者比较
        if (!sim_number.equals(simSerialNumber)) {
            String contact_phone = SpUtil.getString(context, ConstantValue.CONTACT_PHONE, "");

            // 4.不相同
            SmsManager smsManager = SmsManager.getDefault();
            // 发短信 第一个参数，短信接收目标
            smsManager.sendTextMessage(contact_phone, null, "sim changed!!!", null, null);
        }
    }
}
