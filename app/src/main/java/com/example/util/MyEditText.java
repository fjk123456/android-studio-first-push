package com.example.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyEditText extends android.support.v7.widget.AppCompatEditText {


    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.isFocusable()){
            if(canScrollVertically(0)| canScrollVertically(-1)){
                this.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.onTouchEvent(event);
    }
}
