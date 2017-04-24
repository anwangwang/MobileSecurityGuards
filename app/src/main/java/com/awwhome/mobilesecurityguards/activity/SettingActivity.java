package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.service.AddressService;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.ServiceUtil;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.widget.SettingClickView;
import com.awwhome.mobilesecurityguards.widget.SettingItemView;

/**
 * 设置中心
 * Created by awwho on 2017/4/2.
 */
public class SettingActivity extends Activity {

    private SettingItemView siv_update;
    private SettingItemView siv_address;
    private SettingClickView scv_toast_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        initUpdateView();
        initUpdate();
//        initAddressView();
        initAddress();
//        initData();

        initToastStyle();
    }

    /**
     * 初始化土司的样式
     */
    private void initToastStyle() {
        scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
        scv_toast_style.setTitle("设置归属地显示风格");

        // 创建描述文字所在的数组
        String[] toastStyleDesc = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};

        // 获取存在SP中的索引值
        int toast_style = SpUtil.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE, 0);

        // 设置描述内容
        scv_toast_style.setDesc(toastStyleDesc[toast_style]);
    }

    /**
     * 初始化归属地开启
     */
    private void initAddress() {
        siv_address = (SettingItemView) findViewById(R.id.siv_address);

        // 存储siv_address是否为选中状态
        // 因为siv_address的逻辑都是在服务中运行的，所以如果存储在SP中，当手机内存不足时，有可能会杀死服务
        // 导致服务关闭之后，siv_address依然显示已选中，出现ui与数据不同步的情况
        // 监听服务是否在运行，根据服务是否运行来确定归属地显示是否开启
        boolean isRunning = ServiceUtil.isRunning(this, "com.awwhome.mobilesecurityguards.service.AddressService");
        siv_address.setCheck(isRunning);
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
