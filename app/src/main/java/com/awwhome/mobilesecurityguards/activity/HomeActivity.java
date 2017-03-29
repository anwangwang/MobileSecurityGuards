package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.awwhome.mobilesecurityguards.R;

/**
 * Created by awwho on 2017/3/29.
 */
public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: HomeActivity");
        setContentView(R.layout.activity_home);
    }
}
