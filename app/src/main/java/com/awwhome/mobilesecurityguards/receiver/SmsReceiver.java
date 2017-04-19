package com.awwhome.mobilesecurityguards.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 短信接收者
 * Created by awwho on 2017/4/19.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive:接收到了广播，短信已接收成功");

        // 1.判断防盗总开关是否开启
        boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);
        if (open_security) {
            // 2.获取短信内容
            Object[] pduses = (Object[]) intent.getExtras().get("pdus");
            for (Object pduse : pduses) {
                SmsMessage fromPdu = SmsMessage.createFromPdu((byte[]) pduse);
                String originatingAddress = fromPdu.getOriginatingAddress();
                String messageBody = fromPdu.getMessageBody();

                Log.d(TAG, "onReceive: 获取到的发送地址为：" + originatingAddress);
                Log.d(TAG, "onReceive: 获取到的短信内容为：" + messageBody);

                // 3.判断短信内容是否包含关键字
                if (messageBody.contains("#*alarm*#")) {
                    // 4.播放报警音乐(MediaPlayer)
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                }
            }
        }
    }
}
