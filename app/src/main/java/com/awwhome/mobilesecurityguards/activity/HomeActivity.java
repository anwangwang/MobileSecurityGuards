package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;
import com.awwhome.mobilesecurityguards.utils.ToastUtil;

/**
 * Created by awwho on 2017/3/29.
 */
public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";
    private GridView gv_home;
    private int[] titleIcons;
    private String[] titleStrs;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: HomeActivity");
        setContentView(R.layout.activity_home);

        initView();
        initData();
        initListener();

    }

    /**
     * 初始化事件
     */
    private void initListener() {

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 点击手机防盗
                        // 弹出对话框
                        showDialog();
                        break;
                    case 8:// 点击设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 弹出对话框
     */
    private void showDialog() {
        // 根据是否设置(保存)过密码，来弹出不同的对话框
        // 对话框是依赖于Activity存在的，所以上下文环境为当前对话框所在的Activity
        String modile_safe_psd = SpUtil.getString(this, ConstantValue.MODILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(modile_safe_psd)) {
            // 设置密码
            showSetPsdDialog();
        } else {
            // 确认密码
            showConfirmPsdDialog();
        }

    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {

    }

    /**
     * 设置密码对话框
     */
    private void showSetPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        // 将xml布局转化为View对象
        View view = View.inflate(this, R.layout.dialog_set_psd, null);
        // 设置自定义弹出框
        dialog.setView(view);
        dialog.show();

        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
        final EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setPsd = et_set_psd.getText().toString();
                String confirmPsd = et_confirm_psd.getText().toString();
                Log.d(TAG, "showSetPsdDialog: setPsd：" + setPsd + ",confirmPsd：" + confirmPsd);
                if (!TextUtils.isEmpty(setPsd) && !TextUtils.isEmpty(confirmPsd)) {
                    if (setPsd.equals(confirmPsd)) {
                        SpUtil.putString(getApplicationContext(), ConstantValue.MODILE_SAFE_PSD, setPsd);
                        // 跳转到手机防盗的Activity
                        startMobileSafeActivity();
                    } else {
                        ToastUtil.showLong(getApplicationContext(), "确认密码错误，请重新输入！");
                    }
                } else {
                    ToastUtil.showLong(getApplicationContext(), "密码不能为空！");
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击取消，隐藏当前的dialog
                dialog.dismiss();
            }
        });
    }

    /**
     * 跳转到手机防盗Activity
     */
    private void startMobileSafeActivity() {
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(intent);
        // 开启新的Activity之后，关闭当前对话框
        dialog.dismiss();
    }

    /**
     * 格式化数据
     */
    private void initData() {

        // 准备数据，9张图片，9组文字
        titleStrs = new String[]{"手机防盗", "通信卫士", "软件管理", "进程管理",
                "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

        titleIcons = new int[]{
                R.mipmap.safe, R.mipmap.callmsgsafe, R.mipmap.app, R.mipmap.taskmanager, R.mipmap.netmanager,
                R.mipmap.trojan, R.mipmap.sysoptimize, R.mipmap.atools, R.mipmap.settings
        };

        gv_home.setAdapter(new MyAdapter());

    }


    /**
     * 格式化UI控件
     */
    private void initView() {
        gv_home = (GridView) findViewById(R.id.gv_home);


    }

    /**
     * 数据适配器
     */
    private class MyAdapter extends BaseAdapter {

        /**
         * 获取条目总数
         * 图片数 == 文字数
         * titleIcons ==titleStrs
         *
         * @return
         */
        @Override
        public int getCount() {
            return titleStrs.length;
        }

        /**
         * 获取条目所在位置
         *
         * @param position 位置
         * @return
         */
        @Override
        public Object getItem(int position) {
            return titleStrs[position];
        }

        /**
         * 获取所在位置条目ID
         *
         * @param position 位置
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 获取View
         *
         * @param position    位置
         * @param convertView 控件的复用
         * @param parent      获取到的控件被包含在此父控件内，一般为null
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 将布局转化为View对象
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_icon.setBackgroundResource(titleIcons[position]);
            tv_title.setText(titleStrs[position]);

            return view;
        }
    }
}
