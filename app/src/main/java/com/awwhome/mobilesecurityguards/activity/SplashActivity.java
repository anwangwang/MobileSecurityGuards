package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

public class SplashActivity extends Activity {

    private TextView tv_version_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    /**
     * 初始化
     */
    public void initView() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);

    }

    /**
     * 初始化数据
     */
    public void initData() {

        // 1，获取应用的版本名称
        String versionName = getAppVersionName();
        // 2，将版本名称显示到控件上
        tv_version_name.setText("版本名称：" + versionName);

    }

    /**
     * 获取应用的版本名称
     *
     * @return 应用版本名称。返回null代表异常
     */
    private String getAppVersionName() {

        // 通过清单文件获取包名称
        // 获取包管理者对象packageManager
        PackageManager packageManager = getPackageManager();
        try {
            // 2.2,通过包管理者对象获取包名,第二个参数传0代表获取最基本的应用信息
            PackageInfo packageInfo = packageManager.getPackageInfo("com.awwhome.mobilesecurityguards", 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
