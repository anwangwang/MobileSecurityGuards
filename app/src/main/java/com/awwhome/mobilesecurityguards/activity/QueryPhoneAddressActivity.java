package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.engine.AddressDao;

/**
 * 查询归属地
 * Created by awwho on 2017/4/19.
 */
public class QueryPhoneAddressActivity extends Activity {

    private static final String TAG = "QueryPhoneAddressActivi";
    private EditText et_phone;
    private Button btn_query;
    private TextView tv_query_result;
    private String mAddress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 3.更新ui
            tv_query_result.setText(mAddress);
        }
    };

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

                String phone = et_phone.getText().toString();

                // 查询归属地
                queryAddress(phone);
            }
        });
    }

    /**
     * 查询归属地，耗时操作
     *
     * @param phone 号码
     */
    private void queryAddress(final String phone) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                // 1.查询归属地
                mAddress = AddressDao.getAddress(phone);
                // 2.消息机制
                // 发送一个空消息，告知主线程可以使用查询到的数据
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


}
