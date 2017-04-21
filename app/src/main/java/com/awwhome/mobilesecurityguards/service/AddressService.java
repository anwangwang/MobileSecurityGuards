package com.awwhome.mobilesecurityguards.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 归属地悬浮框显示服务
 * Created by awwho on 2017/4/21.
 */
public class AddressService extends Service {

    private static final String TAG = "AddressService";
    private TelephonyManager telephonyManager;
    private MyPhoneStateListener myPhoneStateListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 当服务第一次开启时，显示土司
        // 监听电话的状态，当电话处于响铃状态时，显示土司，处于空闲状态时，不显示土司
        // 1.获取电话管理者对象
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        myPhoneStateListener = new MyPhoneStateListener();
        // 2.监听电话状态
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (telephonyManager != null && myPhoneStateListener != null) {
            // 当服务关闭时，取消对电话状态的监听
            telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    // 自定义PhoneStateListener，手动实现onCallStateChanged方法
    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            // 3.电话状态发生改变时，调用此方法
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // 空闲状态，不显示土司
                    Log.d(TAG, "onCallStateChanged: 空闲状态，土司不显示了。。。。");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // 摘机状态
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // 响铃状态，显示土司
                    Log.d(TAG, "onCallStateChanged: 响铃状态，土司显示了。。。。。。");
                    break;
            }
        }
    }
}
