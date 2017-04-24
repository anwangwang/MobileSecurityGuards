package com.awwhome.mobilesecurityguards.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;

/**
 * 设置中心 点击条目View
 * Created by awwho on 2017/4/2.
 */
public class SettingClickView extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_desc;

    public SettingClickView(Context context) {
        this(context, null);
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 将布局文件转化为View对象
        // 第三个参数，需要将布局文件挂载到当前SettingItemView布局中
        View.inflate(context, R.layout.setting_click_view, this);
       /* View view = View.inflate(context, R.layout.setting_item_view, null);
        this.addView(view);*/
        // findViewById 这里可以直接使用findViewById获取控件，
        // 是因为已经将布局setting_item_view挂载到了当前的SettingItemView布局中
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
    }

    /**
     * 设置标题内容
     *
     * @param title 标题内容
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置描述内容
     *
     * @param desc 标题内容
     */
    public void setDesc(String desc) {
        tv_desc.setText(desc);
    }

}
