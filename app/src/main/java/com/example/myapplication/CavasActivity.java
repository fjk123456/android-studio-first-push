package com.example.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class CavasActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout layout;
    private Button save;
    private Button redo;
    private Button recover;
    private Button undo;
    private Button eraser;
    private MyView myView;
    private Button choose_style_btn;
    //画笔设置
    private int paint_size ;
    private int paint_color ;
    private int flag =0; //画笔模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavas);
        init_view();
    }

    private void init_view() {
        layout = findViewById(R.id.main);
        save = findViewById(R.id.save);
        redo = findViewById(R.id.redo);
        recover = findViewById(R.id.recover);
        undo = findViewById(R.id.undo);
        eraser = findViewById(R.id.eraser);
        choose_style_btn = findViewById(R.id.choose_style_btn);
        save.setOnClickListener(this);
        redo.setOnClickListener(this);
        recover.setOnClickListener(this);
        undo.setOnClickListener(this);
        eraser.setOnClickListener(this);
        choose_style_btn.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        myView = new MyView(CavasActivity.this,height,width);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(myView,layoutParams);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.redo:
                myView.redo();
                break;
            case R.id.recover:
                myView.recover();
                break;
            case R.id.save:
                myView.savetoSD();
                break;
            case R.id.undo:
                myView.undo();
                break;
            case R.id.eraser:
                if(flag==0){
                    myView.selectPaintstyle(10);
                    eraser.setText("画笔");
                    flag =1;
                }else {
                    myView.selectPaintstyle(1);
                    eraser.setText("橡皮檫");
                    flag=0;
                }
                break;
            case R.id.choose_style_btn:
                show_choose_diaalog();
                break;
        }
    }

    private void show_choose_diaalog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view= layoutInflater.inflate(R.layout.alert_title,null);
        SeekBar seekBar = view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(CavasActivity.this,"当前进度为："+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(5);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                paint_size=seekBar.getProgress();
            }
        });
        final String [] array = {"黑色","红色","灰色","绿色","蓝色","黄色"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CavasActivity.this);
        builder.setTitle("画笔样式设置");
        builder.setCustomTitle(view);
        builder.setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                paint_color = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myView.selectPaintstyle(paint_size,paint_color);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
        Button positiveButton= dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.GREEN);
        positiveButton.setTextSize(30);
        LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)positiveButton.getLayoutParams();
        params1.weight=1;
        params1.gravity= Gravity.CENTER;
        positiveButton.setLayoutParams(params1);
        Button negtiveButton= dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams params2= (LinearLayout.LayoutParams)negtiveButton.getLayoutParams();
        params2.weight=1;
        params2.gravity= Gravity.CENTER;
        positiveButton.setLayoutParams(params2);
    }
}
