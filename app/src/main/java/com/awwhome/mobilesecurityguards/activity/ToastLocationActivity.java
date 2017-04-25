package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.awwhome.mobilesecurityguards.R;
import com.awwhome.mobilesecurityguards.utils.ConstantValue;
import com.awwhome.mobilesecurityguards.utils.SpUtil;

/**
 * 土司位置界面
 * Created by awwho on 2017/4/25.
 */
public class ToastLocationActivity extends Activity {

    private ImageView iv_drag;
    private Button btn_top;
    private Button btn_bottom;
    private WindowManager mWM;
    private int mScreenHeight;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_loaction);

        initView();
    }

    /**
     * 初始化UI
     */
    private void initView() {

        iv_drag = (ImageView) findViewById(R.id.iv_drag);
        btn_top = (Button) findViewById(R.id.btn_top);
        btn_bottom = (Button) findViewById(R.id.btn_bottom);

        int location_x = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_X, 0);
        int location_y = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_Y, 0);

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        // 获取屏幕的宽高
        mScreenHeight = mWM.getDefaultDisplay().getHeight();
        mScreenWidth = mWM.getDefaultDisplay().getWidth();

        // 左上角的坐标作用在iv_drag上
        // ImageView在相对布局中，所以其所在的位置的规则需要由相对布局提供
        // 指定宽高
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        // 设置iv_drag的所在位置坐标
        layoutParams.leftMargin = location_x;
        layoutParams.topMargin = location_y;

        // 默认显示位置
        if (location_y > mScreenHeight / 2) {
            btn_top.setVisibility(View.VISIBLE);
            btn_bottom.setVisibility(View.INVISIBLE);
        } else {
            btn_top.setVisibility(View.INVISIBLE);
            btn_bottom.setVisibility(View.VISIBLE);
        }

        // 将设置的规则作用在控件上
        iv_drag.setLayoutParams(layoutParams);

        // 注册触摸事件
        iv_drag.setOnTouchListener(new View.OnTouchListener() {

            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 所有触摸事件都在MotionEvent中
                // 按下（一次）移动（多次）抬起（一次）
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 移动
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        int disX = moveX - startX;
                        int disY = moveY - startY;

                        // 当前控件所在屏幕的左，上角的位置
                        int left = iv_drag.getLeft() + disX;
                        int top = iv_drag.getTop() + disY;

                        // 当前控件所在屏幕的由，下角的位置
                        int right = iv_drag.getRight() + disX;
                        int bottom = iv_drag.getBottom() + disY;

                        // 告知移动的控件，按计算出来的坐标去展示
                        iv_drag.layout(left, top, right, bottom);

                        // 重置一次其实起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        // 容错处理
                        // TODO: 2017/4/25
                        // 左边缘不能移出屏幕
                        if (left < 0) {
                            break;
                        }
                        // 上边缘不能移出屏幕(可显示区域)
                        if (top < 0) {
                            break;
                        }
                        // 右边缘不能移出屏幕
                        if (right > mScreenWidth) {
                            break;
                        }
                        // 下边缘不能移出屏幕
                        if (bottom > mScreenHeight - 22) {
                            break;
                        }

                        // 描述文字的所在位置
                        if (top > mScreenHeight / 2) {
                            btn_top.setVisibility(View.VISIBLE);
                            btn_bottom.setVisibility(View.INVISIBLE);
                        } else {
                            btn_top.setVisibility(View.INVISIBLE);
                            btn_bottom.setVisibility(View.VISIBLE);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        // 抬起
                        // 存储移动到的位置
                        SpUtil.putInt(getApplicationContext(), ConstantValue.LOCATION_X, iv_drag.getLeft());
                        SpUtil.putInt(getApplicationContext(), ConstantValue.LOCATION_Y, iv_drag.getTop());

                        break;
                }
                // 在当前的情况下返回false，不响应事件
                // 返回true，才会去相应事件
                return true;
            }
        });
    }
}
