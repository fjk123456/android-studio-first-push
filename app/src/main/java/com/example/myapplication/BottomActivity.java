package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.data.User;
import com.example.util.BeanUtil;
import com.example.view.DrawableEditText;
import com.example.view.MyView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BottomActivity extends AppCompatActivity implements SensorEventListener, DrawableEditText.DrawableClick {
    private DrawableEditText draw1;
    private DrawableEditText draw2;
    private EditText edit;
    private EditText regex;
    private Button sure;
    private MyView miui;
    private  float val;
    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private Sensor mag_sensor;
    private SensorEventListener sensorEventListener;
    float accValues[] = new float[3];
    //获得手机内的磁场传感器传回的数据
    float magValues[] = new float[3];
    //旋转矩阵，用来保存磁场和加速度传感器传回的数据
    float preValues[] = new float[1];
    float r[] = new float[9];
    //模拟方向传感器取得的数据，弧度制
    float values[] = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        edit = findViewById(R.id.edit);
        regex = findViewById(R.id.regex);
        draw1 = findViewById(R.id.draw1);
        draw1.setDrawable(R.drawable.photo1);
        draw1.setDrawableClick(this);
        draw2 = findViewById(R.id.draw2);
        draw2.setDrawableClick(this);
        sure = findViewById(R.id.sure);
        miui= findViewById(R.id.miui);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button surebtn = (Button)v;
                switch (surebtn.getText().toString()){
                    case "测量":
                        miui.setIsSave(true);
                        surebtn.setText("解锁");
                        Log.e("cel","保存命令");
                        break;
                    case "解锁":
                        miui.setIsSave(false);
                        surebtn.setText("测量");
                        Log.e("cel","测量命令");
                        break;
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //给传感器注册监听：
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_NORMAL);//FASTEST,GAME,NORMAL,UI,时间
        sensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accValues = event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magValues = event.values;
        }else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            preValues = event.values;
        }

        SensorManager.getRotationMatrix(r, null, accValues, magValues);
        SensorManager.getOrientation(r, values);
        miui.setValues(values);
        float roundDia, roundDip;
        double floatPre,values0;
        roundDip = (float) Math.acos(1 /Math.sqrt(Math.tan(values[1]) *Math.tan(values[1]) +Math.tan(values[2]) *Math.tan(values[2]) +1));
        if(values[0] > 0)
            values0 = values[0];
        else
            values0 = 2 *Math.PI +values[0];
        if(values[2] < 0)
            roundDia = (float) (values0 -Math.acos(Math.tan(values[1]) /Math.tan(roundDip)));
        else
            roundDia = (float) (values0 +Math.acos(Math.tan(values[1]) /Math.tan(roundDip)));
        if(roundDia < 0)
            while (roundDia < 0)
                roundDia += 2 *Math.PI;
        else if(roundDia > 2 *Math.PI)
            while (roundDia > 2 *Math.PI)
                roundDia -= 2 *Math.PI;
        //values[0] 手机的方位，正北为0°，顺时针180°为正，逆时针180°为负
        //values[1] 手机上下倾斜程度
        //values[2] 手机左右倾斜程度
        //把数据由弧度转化成角度
        roundDip = (float) Math.toDegrees(roundDip);
        roundDia = (float) Math.toDegrees(roundDia);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void drawableClick(View view) {
        switch (view.getId()){
            case R.id.draw1:
                Toast.makeText(BottomActivity.this,"draw1被点击了",Toast.LENGTH_SHORT).show();
                break;
            case R.id.draw2:
                Toast.makeText(BottomActivity.this,"draw2被点击了",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
