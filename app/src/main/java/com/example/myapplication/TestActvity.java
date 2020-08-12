package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestActvity extends AppCompatActivity {
    private RadioGroup sex;
    private EditText text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_actvity);
        sex = findViewById(R.id.sex);
        text1 = findViewById(R.id.text1);

        ArrayList<String> arrayList = new ArrayList<>();
        List<View> list = findView(getWindow().getDecorView());
        Toast.makeText(this,"子view个数"+list.size(),Toast.LENGTH_SHORT).show();
        Log.e("list",arrayList.size()+"list包含的个数");
        for(View view : list){

            if(view.getTag()==null){
                Log.e("tag","view的tag为空");
            }else {
                Log.e("tag",view.getTag().toString());
            }

        }
        String temp = "洞顶";
        String text = temp.split("-")[0];
        Log.e("regex",text+"456");
    }

    private List<View> findView(View group) {
        List<View> arrayList = new ArrayList<>();
        if(group instanceof ViewGroup){
            ViewGroup group1 = (ViewGroup)group;
            for(int i = 0; i< group1.getChildCount();i++){
                View view = group1.getChildAt(i);
                if(view instanceof RadioButton  | view instanceof EditText){
                    arrayList.add(view);
                }else {
                    arrayList.addAll(findView(view));
                }
            }
        }
        return arrayList;
    }
}
