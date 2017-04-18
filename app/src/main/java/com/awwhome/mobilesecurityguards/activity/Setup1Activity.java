package com.awwhome.mobilesecurityguards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.awwhome.mobilesecurityguards.R;

/**
 * 手机防盗第一个设置Activity
 * Created by awwho on 2017/4/8.
 */
public class Setup1Activity extends Activity {

    private static final String TAG = "Setup1Activity";

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             * 监听手势的移动
             * @param e1 起始点
             * @param e2 结束点
             * @param velocityX 水平方向移动的速度
             * @param velocityY 垂直方向移动的速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > 0) {
                    // 由右向左滑动，滑动到下一页
                    Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
                    startActivity(intent);
                    finish();

                    // 开启平移动画
                    overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
                }
                if (e1.getX() - e2.getX() < 0) {
                    // 由左向右滑动，滑动到上一页

                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 下一页
     * 跳转到第二个设置页面
     *
     * @param view
     */
    public void nextPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();

        // 开启平移动画
        overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
    }

    // 监听屏幕上响应的时间类型
    // 按下(一次) 移动(多次) 抬起(一次)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
