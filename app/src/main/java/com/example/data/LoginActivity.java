package com.example.data;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.BottomActivity;
import com.example.myapplication.List_tiaosiActicity;
import com.example.myapplication.R;
import com.example.util.ShareUtil;

import java.lang.reflect.Field;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private EditText userEV;
    private ImageView user_delet;
    private ImageView ps_visible;
    private EditText psEV;
    private ImageView ps_delet;
    private CheckBox repsCheck;
    private TextView registerPs;
    private Button submit;
    private boolean isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init_view();
        init_listener();
        GeolgicalBound_DC geolgicalBound_dc = new GeolgicalBound_DC();
        Field [] fields = geolgicalBound_dc.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String filedName = field.getName();
            Log.e("ooo",filedName);
        }
        Log.e("ooo",fields.length+"ge");
        Object ooo = geolgicalBound_dc;
        Field [] fields1 = ooo.getClass().getDeclaredFields();
        for(Field field : fields1){
            field.setAccessible(true);
            String filedName = field.getName();
            Log.e("ooo",filedName);
        }
        Log.e("ooo",fields1.length+"ge");
    }
    //控件的绑定与初始化
    private void init_view() {
        userEV = findViewById(R.id.userEV);
        user_delet = findViewById(R.id.user_delet);
        ps_visible = findViewById(R.id.ps_visible);
        psEV = findViewById(R.id.psEV);
       // psEV.setTransformationMethod(PasswordTransformationMethod.getInstance());   //刚开始默认为可见
        ps_delet = findViewById(R.id.ps_delet);
        repsCheck = findViewById(R.id.repsCheck);
        registerPs = findViewById(R.id.registerPs);
        submit = findViewById(R.id.submit);
    }
    //写控件的监听
    private void init_listener() {
        repsCheck.setOnCheckedChangeListener(this);
        registerPs.setOnClickListener(this);
        ps_delet.setOnClickListener(this);
        ps_visible.setOnClickListener(this);
        user_delet.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerPs:   //注册界面的跳转
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
                    psEV.setInputType(129);
                    Selection.setSelection(psEV.getText(),psEV.getText().length());   //设置光标的位置
                }else {
                    view.setBackgroundResource(R.drawable.visible);
                    psEV.setInputType(128);
                    Selection.setSelection(psEV.getText(),psEV.getText().length());   //设置光标的位置
                }
                break;
            case R.id.submit:   //登录按钮
                checkUserAndPs();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //记住密码，我们将其保存在sharedPreference中
        isSave = isChecked;
    }

    private void checkUserAndPs() {
        if(userEV.getText().toString().isEmpty() ){
            Toast.makeText(this,"账户不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if(psEV.getText().toString().isEmpty() ){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String sharedUser = ShareUtil.getString(this,"user","#");
        if(sharedUser.equals(userEV.getText().toString())){
            if(ShareUtil.getString(this,sharedUser,"###").equals(psEV.getText().toString())){
                Intent intent = new Intent(LoginActivity.this, List_tiaosiActicity.class);
                startActivity(intent);
                if(isSave){
                    ShareUtil.putString(this,"user",userEV.getText().toString());
                    ShareUtil.putString(this,userEV.getText().toString(),psEV.getText().toString());
                }
            }else {
                Toast.makeText(this,"密码不正确",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"此账户不存在",Toast.LENGTH_SHORT).show();
        }
    }
}
