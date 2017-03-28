package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.StreamUtil;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";

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

        // 3，检测新版本，如有新版本，提示用户更新
        checkAppVersion();

    }

    /**
     * 检测新版本
     * 将本地版本号与服务器版本号想比较，如果本地版本号小于服务器版本号，提示用户更新版本
     */
    private void checkAppVersion() {

        // 1.获取本地版本
        int localVersionCode = getAppLocalVersionCode();

        getAppVersionCode();
    }

    /**
     * 获取服务器版本,请求服务器，耗时操作，开启子线程
     *
     * @return
     */
    private int getAppVersionCode() {

        Log.d(TAG, "getAppVersionCode: 执行到此方法");

        new Thread() {

            @Override
            public void run() {
                super.run();

                // 1.请求服务器
                // 仅限于模拟器访问本地电脑的tomcat地址：http://10.0.2.2:8080
                String urlStr = "http://10.0.2.2:8080/serverAppVersion.json";
                try {
                    // 1.1 封装请求地址
                    URL url = new URL(urlStr);
                    // 1.2 开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // 1.3 设置请求头
                    // 请求超时
                    connection.setConnectTimeout(2000);
                    // 读取超时
                    connection.setReadTimeout(2000);
                    // 默认是get方式请求
//                    connection.setRequestMethod("POST");
                    // 1.4 获取请求成功响应码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        // 1.5 以流的形式获取数据
                        InputStream inputStream = connection.getInputStream();
                        // 1.6 将流转换为字符串
                        String json = StreamUtil.stream2String(inputStream);

                        Log.d(TAG, "run: 获取到的服务器的json字符串：" + json);

                        JSONObject jsonObject = new JSONObject(json);

                        String versionName = (String) jsonObject.get("versionName");
                        String versionCode = (String) jsonObject.get("versionCode");
                        String versionDesc = (String) jsonObject.get("versionDesc");
                        String downloadUrl = (String) jsonObject.get("downloadUrl");

                        Log.d(TAG, "run: 解析后的json字符串：versionName:" + versionName + ",versionCode:" +
                                versionCode + ",versionDesc" + versionDesc + ",downloadUrl" + downloadUrl);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });*/

        return 0;
    }

    /**
     * 获取本地版本
     *
     * @return 版本号。如果为null，表示异常
     */
    private int getAppLocalVersionCode() {

        // 通过清单文件获取包名称
        // 获取包管理者对象packageManager
        PackageManager packageManager = getPackageManager();
        try {
            // 2.2,通过包管理者对象获取包名,第二个参数传0代表获取最基本的应用信息
            PackageInfo packageInfo = packageManager.getPackageInfo("com.awwhome.mobilesecurityguards", 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

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
