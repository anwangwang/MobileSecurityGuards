package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.StreamUtil;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 启动页面
 */
public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";

    // 提示用户更新状态码
    private static final int PROMPT_UPDATE = 100;
    // 跳转到home页面状态码
    private static final int ENTER_HOME = 101;

    private TextView tv_version_name;
    private int localVersionCode;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case PROMPT_UPDATE:

                    Log.d(TAG, "handleMessage: 有新版本！！");
                    // 弹框，提示用户更新
                    Toast.makeText(SplashActivity.this, "更新版本", Toast.LENGTH_SHORT).show();
                    break;
                case ENTER_HOME:
                    // 进入主页面
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

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

        final Message message = Message.obtain();

        // 1.获取本地版本号
        localVersionCode = getAppLocalVersionCode();

        // 2.获取服务器版本号
        new Thread() {

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                super.run();
                // [1]请求服务器
                // 仅限于模拟器访问本地电脑的tomcat地址：http://10.0.2.2:8080
                String urlStr = "http://10.0.2.2:8080/serverAppVersion.json";
                try {
                    // [1.1]封装请求地址
                    URL url = new URL(urlStr);
                    // [1.2]开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // [1.2]1.3 设置请求头
                    // 请求超时
                    connection.setConnectTimeout(2000);
                    // 读取超时
                    connection.setReadTimeout(2000);
                    // 默认是get方式请求
//                    connection.setRequestMethod("POST");
                    // [1.4] 获取请求成功响应码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        // [1.5] 以流的形式获取数据
                        InputStream inputStream = connection.getInputStream();
                        // [1.6]将流转换为字符串
                        String json = StreamUtil.stream2String(inputStream);

                        Log.d(TAG, "run: 获取到的服务器的json字符串：" + json);

                        JSONObject jsonObject = new JSONObject(json);

                        String versionName = jsonObject.getString("versionName");
                        String versionCode = jsonObject.getString("versionCode");
                        String versionDesc = jsonObject.getString("versionDesc");
                        String downloadUrl = jsonObject.getString("downloadUrl");

                        Log.d(TAG, "run: 解析后的json字符串：versionName:" + versionName + ",versionCode:" +
                                versionCode + ",versionDesc" + versionDesc + ",downloadUrl" + downloadUrl);

                        // 3.将本地版本号与服务器版本号相比较
                        if (localVersionCode < Integer.parseInt(versionCode)) {
                            // 提示用户更新，弹框，子线程更新UI使用消息机制
                            message.what = PROMPT_UPDATE;
                        } else {
                            // 跳转到home页面
                            message.what = ENTER_HOME;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 4000) {
                        // 睡眠4秒钟后再发送消息
                        try {
                            Thread.sleep(4000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    // 发送消息
                    handler.sendMessage(message);
                }
            }
        }.start();


        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });*/

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
