package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;

/**
 * 第三个设置Activity
 * Created by awwho on 2017/4/12.
 */
public class Setup3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup3);
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

}
