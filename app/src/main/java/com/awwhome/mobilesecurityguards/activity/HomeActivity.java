package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

/**
 * Created by awwho on 2017/3/29.
 */
public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";
    private GridView gv_home;
    private int[] titleIcons;
    private String[] titleStrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: HomeActivity");
        setContentView(R.layout.activity_home);

        initView();
        initData();

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
