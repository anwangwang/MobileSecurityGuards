package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.service.AddressService;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.widget.SettingItemView;

/**
 * 设置中心
 * Created by awwho on 2017/4/2.
 */
public class SettingActivity extends Activity {

    private SettingItemView siv_update;
    private SettingItemView siv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        initUpdateView();
        initUpdate();
//        initAddressView();
        initAddress();
//        initData();
    }

    /**
     * 初始化归属地开启
     */
    private void initAddress() {
        siv_address = (SettingItemView) findViewById(R.id.siv_address);

        // 注册点击事件
        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = siv_address.isChecked();
                siv_address.setCheck(!checked);
                if (!checked) {
                    // 开启服务
                    startService(new Intent(getApplicationContext(), AddressService.class));
                } else {
                    // 关闭服务
                    stopService(new Intent(getApplicationContext(), AddressService.class));
                }
            }
        });


    }

    /**
     * 初始化自动更新
     */
    private void initUpdate() {
        siv_update = (SettingItemView) findViewById(R.id.siv_update);
        // 获取上一次存储的节点值
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        // 将节点值设置给siv_update控件
        siv_update.setCheck(open_update);

        // 自动更新点击事件
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前条目是否选中
                boolean checked = siv_update.isChecked();
                // 若选中，修改为为选中
                // 若未选中，修改为选中
                siv_update.setCheck(!checked);

                // 当控件点击后的状态发生改变时，存储新的节点值
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !checked);
            }
        });
    }

    /**
     * 初始化归属地开启UI
     */
    private void initAddressView() {
        siv_address = (SettingItemView) findViewById(R.id.siv_address);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // 获取上一次存储的节点值
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        // 将节点值设置给siv_update控件
        siv_update.setCheck(open_update);

        // 自动更新点击事件
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前条目是否选中
                boolean checked = siv_update.isChecked();
                // 若选中，修改为为选中
                // 若未选中，修改为选中
                siv_update.setCheck(!checked);

                // 当控件点击后的状态发生改变时，存储新的节点值
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !checked);
            }
        });
    }

    /**
     * 初始化自动更新UI
     */
    private void initUpdateView() {

        siv_update = (SettingItemView) findViewById(R.id.siv_update);
    }
}
