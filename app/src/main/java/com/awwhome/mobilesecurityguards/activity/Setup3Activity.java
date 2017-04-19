package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.utils.ToastUtil;

/**
 * 第三个设置Activity
 * Created by awwho on 2017/4/12.
 */
public class Setup3Activity extends BaseActivity {

    private EditText et_safe_number;
    private Button btn_select_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initView();
        initData();
    }

    @Override
    protected void initData() {
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

    @Override
    protected void initView() {
        et_safe_number = (EditText) findViewById(R.id.et_safe_number);
        btn_select_contacts = (Button) findViewById(R.id.btn_select_contacts);

        // 回显联系人
        String phone = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, "");
        et_safe_number.setText(phone);
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();

        // 开启平移动画
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    protected void showNextPage() {
        String phone = et_safe_number.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
            startActivity(intent);
            finish();
            // 如果是用户手动输入的，再次存储到SP中
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
            // 开启平移动画
            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
        } else {
            ToastUtil.showLong(this, "请输入电话号码");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String phone = data.getStringExtra("phone");
            // 将电话号码去除特殊字符
            phone = phone.replace("-", "").replace(" ", "").trim();
            et_safe_number.setText(phone);

            // 存储联系人至SP中
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
        }

    }
}
