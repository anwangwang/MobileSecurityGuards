package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;

import com.awwhome.mobilesecurityguards.R;

/**
 * 查询归属地
 * Created by awwho on 2017/4/19.
 */
public class QueryPhoneAddressActivity extends Activity {

    private static final String TAG = "QueryPhoneAddressActivi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_query_phone_address);
    }
}
