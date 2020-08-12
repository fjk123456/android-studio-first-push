package com.example.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.myapplication.R;

public class DrawableEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener {
    private Drawable mDrawable;    //需要展示的图
    private int drawableId = R.drawable.calendar;
    private DrawableClick drawClick;
    public  interface  DrawableClick{
        void drawableClick(View view);
    }
    public DrawableEditText(Context context) {
        super(context);
        init(context);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDrawable = context.getDrawable(drawableId);   //默认的图片，日历
        setBackground(null);
        showDrawableEdit();
    }
    public void setDrawable(int resId){
        Drawable drawable = getResources().getDrawable(resId);
        mDrawable = drawable;
        showDrawableEdit();
    }
    public void setDrawableClick(DrawableClick ddd){
        drawClick = ddd;
    }

    private void showDrawableEdit() {
       // mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth()*4/5, mDrawable.getIntrinsicHeight()/2);
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCompoundDrawables()[2] != null && drawClick!=null) {
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight(); // 起始位置
                int end = getWidth() - getPaddingRight(); // 结束位置
                boolean available =((event.getX() > start) && (event.getX() < end));
                if (available) {
                   // setFocusable(false);
                    closeSoftInput();
                    setCursorVisible(false);
                    drawClick.drawableClick(this);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }
    private void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus &&  getText().toString().length()>0){
            setSelection(getText().toString().length());
        }
    }
}

