package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.utils.ToastUtil;
import com.awwhome.mobilesecurityguards.widget.SettingItemView;

/**
 * 第二个设置Activity
 * Created by awwho on 2017/4/12.
 */
public class Setup2Activity extends BaseActivity {

    private static final String TAG = "Setup2Activity";

    private SettingItemView siv_sim_bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup2);

        initView();
        initData();
    }

    @Override
    protected void initData() {
        // 数据回显
        String sim_number = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
        if (TextUtils.isEmpty(sim_number)) {
            // 没有绑定sim卡
            siv_sim_bound.setCheck(false);
        } else {
            // 已绑定sim卡
            siv_sim_bound.setCheck(true);
        }
        siv_sim_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: 条目被点击");

                boolean checked = siv_sim_bound.isChecked();
                siv_sim_bound.setCheck(!checked);
                if (!checked) {
                    // 存储sim序列号
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    // 获取sim序列号
                    String number = telephonyManager.getSimSerialNumber();
                    SpUtil.putString(getApplicationContext(), ConstantValue.SIM_NUMBER, number);
                } else {
                    // 移除sim序列号
                    SpUtil.remove(getApplicationContext(), ConstantValue.SIM_NUMBER);
                }
            }
        });
    }

    @Override
    protected void initView() {
        siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);
        finish();

        // 开启平移动画
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    protected void showNextPage() {
        // 根据SIM序列号决定跳转页面
        String sim_number = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
        if (!TextUtils.isEmpty(sim_number)) {
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);
            finish();
            // 开启平移动画
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
        } else {
            ToastUtil.showLong(this, "请绑定SIM卡");
        }

    }

}
