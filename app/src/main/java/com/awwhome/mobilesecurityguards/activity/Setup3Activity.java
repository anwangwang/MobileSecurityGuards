package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.awwhome.mobilesecurityguards.R;

/**
 * 第三个设置Activity
 * Created by awwho on 2017/4/12.
 */
public class Setup3Activity extends Activity {

    private EditText et_safe_number;
    private Button btn_select_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        btn_select_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到联系人列表页面
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et_safe_number = (EditText) findViewById(R.id.et_safe_number);
        btn_select_contacts = (Button) findViewById(R.id.btn_select_contacts);
    }


    /**
     * 上一页
     *
     * @param view
     */
    public void prePage(View view) {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 下一页
     *
     * @param view
     */
    public void nextPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String phone = data.getStringExtra("phone");
            // 将电话号码去除特殊字符
            phone = phone.replace("-", "").replace(" ", "").trim();
            et_safe_number.setText(phone);
        }

    }
}
