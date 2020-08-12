package com.example.data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.util.MD5Util;
import com.example.util.ShareUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView image1;
    private EditText userEV;
    private ImageView user_delet;
    private ImageView ps_visible;
    private EditText psEV;
    private ImageView ps_delet;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        init_view();
        init_listener();
    }

    private void init_listener() {
        image1.setOnClickListener(this);
        user_delet.setOnClickListener(this);
        ps_delet.setOnClickListener(this);
        ps_visible.setOnClickListener(this);
        submit.setOnClickListener(this);
        userEV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    user_delet.setVisibility(View.VISIBLE);
                }else {
                    user_delet.setVisibility(View.INVISIBLE);
                }
            }
        });
        psEV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    ps_delet.setVisibility(View.VISIBLE);
                }else {
                    ps_delet.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void init_view() {
        image1 = findViewById(R.id.image1);
        userEV = findViewById(R.id.userEV);
        user_delet = findViewById(R.id.user_delet);
        ps_visible = findViewById(R.id.ps_visible);
        psEV = findViewById(R.id.psEV);
        psEV.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        ps_delet = findViewById(R.id.ps_delet);
        submit = findViewById(R.id.submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image1:    //返回监听
                this.finish();
                break;
            case R.id.ps_delet:    //密码框的删除按钮
                psEV.setText("");
                break;
            case R.id.user_delet:   //账户框的删除按钮
                userEV.setText("");
                break;
            case R.id.ps_visible:   //设置密码是否可见
                ImageView view = (ImageView)v;
                if(view.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.visible).getConstantState())){
                    view.setBackgroundResource(R.drawable.invisible);
                    psEV.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Selection.setSelection(psEV.getText(),psEV.getText().length());   //设置光标的位置
                }else {
                    view.setBackgroundResource(R.drawable.visible);
                    psEV.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Selection.setSelection(psEV.getText(),psEV.getText().length());   //设置光标的位置
                }
                break;
            case R.id.submit:   //登录按钮
                if(checkUserAndPs()){
                    this.finish();
                }
                break;
        }
    }

    private boolean checkUserAndPs() {
        if(psEV.getText().toString().isEmpty() ){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(userEV.getText().toString().isEmpty() ){
            Toast.makeText(this,"账户不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        ShareUtil.putString(this,"user", userEV.getText().toString());
        ShareUtil.putString(this,userEV.getText().toString(),psEV.getText().toString());
        return true;
    }
}
