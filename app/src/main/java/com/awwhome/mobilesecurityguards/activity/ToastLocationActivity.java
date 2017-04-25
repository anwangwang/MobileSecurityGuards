package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.awwhome.mobilesecurityguards.R;

/**
 * 土司位置界面
 * Created by awwho on 2017/4/25.
 */
public class ToastLocationActivity extends Activity {

    private ImageView iv_drag;
    private Button btn_top;
    private Button btn_bottom;

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

                        // 容错处理
                        break;
                    case MotionEvent.ACTION_UP:
                        // 抬起
                        break;
                }
                return false;
            }
        });
    }
}
