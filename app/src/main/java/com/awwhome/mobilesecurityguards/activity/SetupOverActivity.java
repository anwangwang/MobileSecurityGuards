package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 手机防盗设置完成Activity
 * Created by awwho on 2017/4/8.
 */
public class SetupOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 根据设置是否完成，跳转到不同的Activity
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            // 如果设置已经完成，就停留在此Activity
            setContentView(R.layout.activity_setup_over);
        } else {
            // 如果设置未完成，跳转到第一个设置Activity
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();
        }

    }
}
