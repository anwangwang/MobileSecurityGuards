package com.awwhome.mobilesecurityguards.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义获得焦点TextView
 * Created by awwho on 2017/3/30.
 */
public class FocusTextView extends TextView {


    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 一直获取焦点
     *
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
