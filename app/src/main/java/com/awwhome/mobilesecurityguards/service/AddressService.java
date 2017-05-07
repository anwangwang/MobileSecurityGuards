package com.awwhome.mobilesecurityguards.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.engine.AddressDao;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 归属地悬浮框显示服务
 * Created by awwho on 2017/4/21.
 */
public class AddressService extends Service {

    private static final String TAG = "AddressService";
    private TelephonyManager telephonyManager;
    private MyPhoneStateListener myPhoneStateListener;

    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private WindowManager windowManager;
    private View toastView;
    private String address;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 更新UI
            tv_address.setText(address);
        }
    };
    private TextView tv_address;

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

        // 获取窗口管理者对象
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
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
                    if (windowManager != null && toastView != null) {
                        windowManager.removeView(toastView);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // 摘机状态
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // 响铃状态，显示土司
                    Log.d(TAG, "onCallStateChanged: 响铃状态，土司显示了。。。。。。");
                    showCustomToast(incomingNumber);
                    break;
            }
        }
    }

    /**
     * 显示自定义土司
     */
    private void showCustomToast(String incomingNumber) {

//        Toast.makeText()

        // XXX This should be changed to use a Dialog, with a Theme.Toast
        // defined that sets up the layout params appropriately.
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        // 在响铃的时候显示土司，和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 指定土司的所在位置
        params.gravity = Gravity.LEFT + Gravity.TOP;

        // 将布局文件转化为View对象
        toastView = View.inflate(this, R.layout.toast_view, null);
        tv_address = (TextView) toastView.findViewById(R.id.tv_address);

        // 获取土司的位置坐标
        params.x = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_X, 0);
        params.y = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_Y, 0);

        // 创建一个存储图片的数组
        int[] toastStyle = new int[]{
                R.drawable.call_locate_white,
                R.drawable.call_locate_orange,
                R.drawable.call_locate_blue,
                R.drawable.call_locate_gray,
                R.drawable.call_locate_green
        };

        // 从SP中获取选中的toast风格的索引
        int toast_style = SpUtil.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE, 0);

        // 5.改变土司的背景颜色
        tv_address.setBackgroundResource(toastStyle[toast_style]);

        windowManager.addView(toastView, mParams);

        // 查询号码归属地
        queryAddress(incomingNumber);
    }

    /**
     * 查询号码归属地
     *
     * @param incomingNumber 来电号码
     */
    private void queryAddress(final String incomingNumber) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                // 查询
                address = AddressDao.getAddress(incomingNumber);
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }
}
