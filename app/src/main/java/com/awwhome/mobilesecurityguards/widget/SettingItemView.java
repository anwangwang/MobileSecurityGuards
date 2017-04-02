package com.awwhome.mobilesecurityguards.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 设置中心条目View
 * Created by awwho on 2017/4/2.
 */
public class SettingItemView extends RelativeLayout {

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 将布局文件转化为View对象
        // 第三个参数，需要将布局文件挂载到当前SettingItemView布局中
        View view = View.inflate(context, R.layout.setting_item_view, this);

        // findViewById 这里可以直接使用findViewById获取控件，
        // 是因为已经将布局setting_item_view挂载到了当前的SettingItemView布局中
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
        CheckBox cb_box = (CheckBox) findViewById(R.id.cb_box);


    }
}
