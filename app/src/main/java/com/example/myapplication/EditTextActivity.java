package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditTextActivity extends AppCompatActivity {
    private EditText edit1;
    private EditText edit2;
    private List<String> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit1.addTextChangedListener(new MyTextWatcher(edit1)) ;

        edit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText temp_v = (EditText)v;
                if(!hasFocus && !temp_v.getText().toString().equals("")){
                    if(!list1.isEmpty()){
                        list1.clear();
                    }
                    float dip_angle =Float.valueOf(temp_v.getText().toString()) ;
                    if(dip_angle==0f){
                        list1.add("E");
                        list1.add("W");
                    }else if(dip_angle==90f | dip_angle==270f){
                        list1.add("N");
                        list1.add("S");
                    }else if(dip_angle>0 && dip_angle<90){
                        list1.add("NW");
                        list1.add("SE");
                    }else if(dip_angle>270 && dip_angle<360){
                        list1.add("NE");
                        list1.add("SW");
                    }
                   Log.e("list",list1.toString());
                }
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {
        private EditText editText;
        public MyTextWatcher(EditText edit1) {
            editText =edit1;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String ss = s.toString();
            if(ss.equals("")){
                return;
            }
            String regex = "^[a-zA-Z].*";
            if(!ss.matches(regex)){
                editText.setText("");
                Toast.makeText(EditTextActivity.this,"只能以字母开头",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
