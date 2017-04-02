package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.widget.SettingItemView;

/**
 * Created by awwho on 2017/4/2.
 */
public class SettingActivity extends Activity {

    private SettingItemView siv_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdateView();
        initListener();
    }

    /**
     * 初始化事件
     */
    private void initListener() {

        // 自动更新点击事件
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前条目是否选中
                boolean checked = siv_update.isChecked();
                // 若选中，修改为为选中
                // 若未选中，修改为选中
                siv_update.setCheck(!checked);
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
