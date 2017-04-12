package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;

import com.awwhome.mobilesecurityguards.R;

/**
 * 联系人列表Activity
 * Created by awwho on 2017/4/13.
 */
public class ContactListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        initView();
        initData();
    }

    /**
     * 初始化数据
     * 获取系统的联系人信息
     */
    private void initData() {
    }

    /**
     * 初始化控件
     */
    private void initView() {
    }
}
