package com.example.myapplication;

import android.app.UiAutomation;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.contrarywind.view.WheelView;
import com.example.util.MatrixUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class UitiaoshiActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText tv1;
    private EditText tv2;
    private LinearLayout container;
    private SmartMaterialSpinner spinner;
    TimePickerView pvTime;
    TimePickerBuilder builder;
    private util utils;
    private boolean isfirst=true;
    private TextView tv3;
    private List<String> lis= new ArrayList<>();

    private List<HashMap<String,Object>> list1;
    private List<HashMap<String,Object>> list2;
    private Spinner uiSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitiaoshi2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        container = findViewById(R.id.container);
        spinner = findViewById(R.id.spinner);
        uiSp = findViewById(R.id.uiSp);
        list1= new ArrayList<>();
        list2= new ArrayList<>();
        CaculateMatrix();
        lis.add("123");
        lis.add("456");
        lis.add("789");
        lis.add("012");
        lis.add("01dadacsdv2");
        lis.add("01nghnkiug2");
        lis.add("0adad");
        spinner.setItem(lis);
        init_data();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv3.setText(position+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv1.setInputType(InputType.TYPE_NULL);
        tv1.setFocusable(false);
        tv2.setInputType(InputType.TYPE_NULL);
        tv2.setFocusable(false);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        utils = new util(UitiaoshiActivity.this);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UitiaoshiActivity.this,spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CaculateMatrix() {
        double [][] matrixed = {{1,3,9,2},{5,8,7,6},{0,1,0,3},{4,0,5,7}};
        double [][] matrix1 = {{1,2,3,4},{1,0,1,1},{1,2,0,1},{2,0,0,1}};
        double [][] matrix2 = {{1,3,0,1},{1,2,0,1},{1,0,0,1},{0,0,1,1}};
        double values = MatrixUtil.matrixValues(matrix1);
        Log.e("matrix","行列式:"+ values);
        double [][] transed = MatrixUtil.matrixMultiply(matrix1,matrix2);

        double [][] Inv = MatrixUtil.matrixInv(matrix1);
        Log.e("matrix","内积:"+transed[0][0]+","+transed[0][1]+ transed[0][2]+ "\n"+ transed[0][3]+","
                +transed[1][0]+","+transed[1][1]+ transed[1][2]+ "\n"+ transed[1][3]+","
                +transed[2][0]+","+transed[2][1]+ transed[2][2]+ "\n"+ transed[2][3]+","
                +transed[3][0]+","+transed[3][1]+ transed[3][2]+ "\n"+ transed[3][3]);
        Log.e("matrix","求逆:"+Inv[0][0]+","+Inv[0][1]+ Inv[0][2]+ "\n"+ Inv[0][3]+","
                +Inv[1][0]+","+Inv[1][1]+ Inv[1][2]+ "\n"+ Inv[1][3]+","
                +Inv[2][0]+","+Inv[2][1]+ Inv[2][2]+ "\n"+ Inv[2][3]+","
                +Inv[3][0]+","+Inv[3][1]+ Inv[3][2]+ "\n"+ Inv[3][3]);

    }

    private void init_data() {
        //数据
        List<String> data_list = new ArrayList<String>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");
        data_list.add("请选择品牌");
        MySimpleAdapter mySimpleAdapter = new MySimpleAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data_list);
        //mySimpleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uiSp.setAdapter(mySimpleAdapter);
        uiSp.setSelection(data_list.size() - 1, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:
               utils.setEditText(tv1);
                break;
            case R.id.tv2:
                utils.setEditText(tv2);
                Toast.makeText(UitiaoshiActivity.this,spinner.getSelectedItem()==null? "":spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                String aa ="#*%*#12#*%*#1#*%*##*%*#1";
                String bb  = aa.concat("#*%*#");
                Log.e("fff",bb);
                String [] A = aa.split("#\\*%\\*#");
                for (int i =1;i<A.length;i++){
                    Log.e("fff","第"+i+A[i]);
                }
                break;
        }
    }

    public class MySimpleAdapter<T> extends ArrayAdapter {
        public MySimpleAdapter( Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}
