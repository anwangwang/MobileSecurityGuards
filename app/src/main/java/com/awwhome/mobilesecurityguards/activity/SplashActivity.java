package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.utils.StreamUtil;
import com.awwhome.mobilesecurityguards.utils.ToastUtil;


import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 启动页面
 */
public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";

    private TextView tv_version_name;

    // 提示用户更新状态码
    private static final int PROMPT_UPDATE = 100;
    // 跳转到home页面状态码
    private static final int ENTER_HOME = 101;

    // 本地版本码
    private int localVersionCode;
    // 版本描述
    private String versionDesc;
    // 下载地址
    private String downloadUrl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case PROMPT_UPDATE:
                    Log.d(TAG, "handleMessage: 有新版本！！");
                    // 弹框，提示用户更新
                    ToastUtil.showShort(SplashActivity.this, "更新版本");
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    // 进入主页面
                    enterHome();
                    break;
                default:
                    break;
            }
        }
    };
    private RelativeLayout activity_splash;

    /**
     * 更新版本对话框
     */
    private void showUpdateDialog() {
        // 参数传this,不能为getApplicationContext()。
        // dialog是依赖activity存在的
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("更新版本");
        dialog.setMessage(versionDesc);
        dialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 下载apk
                downloadApk();
            }
        });
        dialog.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtil.showShort(SplashActivity.this, "稍后再说");
                enterHome();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "onCancel: 点击了取消按钮");
                enterHome();
            }
        });

        // 显示弹出框
        dialog.show();
    }

    /**
     * 下载APK
     */
    private void downloadApk() {

        // 下载需要的参数
        // 下载地址 downloadUrl
        // 存放的位置
        // SD卡是否挂载
        RequestParams params = new RequestParams(downloadUrl);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
            params.setSaveFilePath(Environment.getExternalStorageDirectory() + File.separator + "MobileSecurityGuards.apk");
            //自动为文件命名
            params.setAutoRename(true);
            x.http().post(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    //apk下载完成后，调用系统的安装方法
                    installApk(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.d(TAG, "onError: Error");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d(TAG, "onCancelled: Cancel");
                }

                @Override
                public void onFinished() {
                    Log.d(TAG, "onFinished: Finish");
                }

                //网络请求之前回调
                @Override
                public void onWaiting() {
                }

                //网络请求开始的时候回调
                @Override
                public void onStarted() {

                }

                //下载的时候不断回调的方法
                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    //当前进度和文件总大小
                    Log.d(TAG, "onLoading: current：" + current + "，total：" + total);
                }
            });
        }
    }

    /**
     * 安装apk
     *
     * @param file apk文件
     */
    private void installApk(File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 点击取消按钮后进入home页面
        if (requestCode == 0) {
            enterHome();
        }
    }

    /**
     * 跳转到home界面
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
        initAnimation();
        initDB();
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        initAddressDB("naddress.db");
    }

    /**
     * 初始化归属地数据库，将第三方资产库中的数据库文件拷贝到项目中
     *
     * @param dbName 数据库名称
     */
    private void initAddressDB(String dbName) {
        // 1，获取项目目录
//        getCacheDir(); // 获取缓存目录
//        Environment.getExternalStorageDirectory(); // 获取SD卡目录
        File filesDir = getFilesDir();
        File file = new File(filesDir, dbName);
        // 如果文件存在，不需要重新创建
        if (file.exists()) {
            return;
        }
        InputStream inputStream = null;
        FileOutputStream fos = null;
        // 2，读取第三方资产目录下面的文件
        try {
            inputStream = getAssets().open(dbName);
            // 3,将读取的内容写入到指定文件夹的指定目录中去
            fos = new FileOutputStream(file);

            // 模板代码
            byte[] bytes = new byte[1024];
            int temp = -1;
            while ((temp = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 添加淡入动画
     */
    private void initAnimation() {

        // 第一个参数为完全透明，第二个参数为完全显示
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        // 动画持续时间
        alphaAnimation.setDuration(3000);
        activity_splash.startAnimation(alphaAnimation);
    }

    /**
     * 初始化
     */
    public void initView() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        activity_splash = (RelativeLayout) findViewById(R.id.activity_splash);

    }

    /**
     * 初始化数据
     */
    public void initData() {

        // 1，获取应用的版本名称
        String versionName = getAppVersionName();
        // 2，将版本名称显示到控件上
        tv_version_name.setText("版本名称：" + versionName);

        // 获取自动更新是否开启
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        if (open_update) {
            // 开启
            // 3，检测新版本，如有新版本，提示用户更新
            checkAppVersion();
        } else {
            // 未开启
            // 发送消息完成后，延迟4秒进行处理
//            handler.sendMessageDelayed(msg,4000);
            handler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
        }


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
                        versionDesc = jsonObject.getString("versionDesc");
                        downloadUrl = jsonObject.getString("downloadUrl");

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
                    // 请求网络异常，进入home界面
                    enterHome();
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
