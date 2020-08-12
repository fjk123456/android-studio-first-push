package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class util {
    private Context context;
    private EditText editText;
    private TimePickerBuilder builder;

    public util(Context context) {
        this.context = context;
        builder = new TimePickerBuilder(this.context,onTimeSelectListener);
    }
    public void setTime(){
        TimePickerView pickerView = builder.setType(new boolean[]{true, true, true, true, true, false})
                .setTitleSize(25)
                .setContentTextSize(20)//滚轮文字大小
                .setItemVisibleCount(5)
                .isDialog(true)
                .setDividerColor(Color.RED)
                .setDividerType(WheelView.DividerType.FILL)
                .build();
        pickerView.show();
    }
    public void setEditText(EditText editText) {
        this.editText = editText;
        setTime();
    }

    private OnTimeSelectListener onTimeSelectListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            editText.setText(getTime(date));
        }
    };

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

}
