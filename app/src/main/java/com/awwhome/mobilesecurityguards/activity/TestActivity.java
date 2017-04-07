package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 测试Activity
 * Created by awwho on 2017/4/7.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("TestActivity");
        setContentView(textView);
    }
}
