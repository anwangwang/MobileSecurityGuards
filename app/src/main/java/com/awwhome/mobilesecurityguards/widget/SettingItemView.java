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

    private CheckBox cb_box;
    private TextView tv_title;
    private TextView tv_desc;

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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        cb_box = (CheckBox) findViewById(R.id.cb_box);

    }

    /**
     * 返回当前SettingItemView是否为选中开启状态
     * 根据cb_box判断是否为选中 true 选中，false 关闭
     *
     * @return true 开启 false 关闭
     */
    public boolean isChecked() {
        return cb_box.isChecked();
    }

    /**
     * 设置选中状态
     *
     * @param isCheck 是否作为开启的变量，由点击过程去做传递
     */
    public void setCheck(boolean isCheck) {

        // 设置cb_box是否选中，根据isCheck
        cb_box.setChecked(isCheck);

        if (isCheck) {
            // 选中
            tv_desc.setText("自动更新已开启");
        } else {
            // 未选中
            tv_desc.setText("自动更新已关闭");
        }
    }


}
