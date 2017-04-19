package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 高级工具
 * Created by awwho on 2017/4/19.
 */
public class AdvancedToolsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AdvancedToolsActivity";
    private TextView tv_query_phone_address;
    private TextView tv_query_common_phone;
    private TextView tv_sms_back;
    private TextView tv_app_lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advanced_tools);

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化UI
     */
    private void initView() {

        tv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
        tv_query_common_phone = (TextView) findViewById(R.id.tv_query_common_phone);
        tv_sms_back = (TextView) findViewById(R.id.tv_sms_back);
        tv_app_lock = (TextView) findViewById(R.id.tv_app_lock);

        tv_query_phone_address.setOnClickListener(this);
        tv_query_common_phone.setOnClickListener(this);
        tv_sms_back.setOnClickListener(this);
        tv_app_lock.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_query_phone_address:
                // 归属地查询
                showQueryPhoneAddress();
                break;
            case R.id.tv_query_common_phone:
                // 短信备份
                showSmsBack();
                break;
            case R.id.tv_sms_back:
                // 常用号码查询
                showQueryCommonPhone();
                break;
            case R.id.tv_app_lock:
                // 程序锁
                showAppLock();
                break;
        }
    }

    /**
     * 程序锁
     */
    private void showAppLock() {
    }

    /**
     * 查询常用号码
     */
    private void showQueryCommonPhone() {
    }

    /**
     * 短信备份
     */
    private void showSmsBack() {
    }

    /**
     * 查询归属地
     */
    private void showQueryPhoneAddress() {
        Intent intent = new Intent(this, QueryPhoneAddressActivity.class);
        startActivity(intent);
    }
}
