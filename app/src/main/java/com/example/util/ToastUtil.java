package com.example.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ToastUtil {
    private static Toast toast ;

    public static  void  showSuccessToast(Context context ,String text){
        View  view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        ImageView imageView = view.findViewById(R.id.toast_image);
        TextView textView = view.findViewById(R.id.toast_text);
        textView.setText(text);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.success));

        toast = new Toast(context);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
