package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;

import com.awwhome.mobilesecurityguards.R;

/**
 * Created by awwho on 2017/4/19.
 */
public class AdvancedToolsActivity extends Activity {

    private static final String TAG = "AdvancedToolsActivity";

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
    }

}
