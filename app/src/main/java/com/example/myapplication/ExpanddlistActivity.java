package com.example.myapplication;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class ExpanddlistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1;
    private ExpandableAdapter adapter;
    private int oldposition;
    private ExpandableListView expandableListView;
    private List<String> parent_list = new ArrayList<>();
    private Map<String,List<String>> child_map = new HashMap<>();
    private static final String SDpath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/SuperMap1/data/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanddlistactivity);
        button1=(Button)findViewById(R.id.expand_activities_button);
        button1.setOnClickListener(this);
        parent_list.add("班级1");
        parent_list.add("班级2");
        parent_list.add("班级3");
        parent_list.add("班级4");
        List<String> list1 = new ArrayList<>();
        for (int i=0;i<4;i++){
            list1.add("学生"+i);
        }
        child_map.put("班级1",list1);
        List<String> list2 = new ArrayList<>();
        for (int i=0;i<4;i++){
            list2.add("学生"+i);
        }
        child_map.put("班级2",list2);
        List<String> list3 = new ArrayList<>();
        for (int i=0;i<3;i++){
            list3.add("学生"+i);
        }
        child_map.put("班级3",list3);
        List<String> list4 = new ArrayList<>();
        for (int i=0;i<2;i++){
            list4.add("学生"+i);
        }
        child_map.put("班级4",list4);
       // initdata();
    }

    private void initdata() {
        File file = new File(SDpath);
        if(!file.exists()){
            Toast.makeText(ExpanddlistActivity.this,"文件夹不存在",Toast.LENGTH_SHORT).show();
            ExpanddlistActivity.this.finish();
        }
        File[] files1 = file.listFiles();
        for(int i =0 ;files1.length> i;i++){
            if(files1[i].isDirectory()){
                File [] files2 = files1[i].listFiles();
                List <String> son_list = new ArrayList<>();
                for (int j =0;files2.length>j;j++){
                    son_list.add(files2[j].getAbsolutePath());
                }
                child_map.put(files1[i].getAbsolutePath(),son_list);
                parent_list.add(files1[i].getAbsolutePath());
            }else {
                Toast.makeText(ExpanddlistActivity.this,"文件格式不符合要求",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        View pop_view = LayoutInflater.from(ExpanddlistActivity.this).inflate(R.layout.expand_popwindow,null);
        final PopupWindow popupWindow = new PopupWindow(pop_view, 800,600);
        popupWindow.showAsDropDown(v,100,50);
        popupWindow.showAtLocation(LayoutInflater.from(ExpanddlistActivity.this).inflate(R.layout.expanddlistactivity,null), Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        final ExpandableListView expandableListView = (ExpandableListView)pop_view.findViewById(R.id.expand_popwindow);
        adapter = new ExpandableAdapter(ExpanddlistActivity.this,parent_list,child_map);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i =0;i<adapter.getGroupCount();i++){
                    if(i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }
}
