package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 查询归属地
 * Created by awwho on 2017/4/19.
 */
public class QueryPhoneAddressActivity extends Activity {

    private static final String TAG = "QueryPhoneAddressActivi";
    private EditText et_phone;
    private Button btn_query;
    private TextView tv_query_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_query_phone_address);

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

        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_query = (Button) findViewById(R.id.btn_query);
        tv_query_result = (TextView) findViewById(R.id.tv_query_result);

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
