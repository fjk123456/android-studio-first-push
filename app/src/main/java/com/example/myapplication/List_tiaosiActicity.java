package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class List_tiaosiActicity extends AppCompatActivity {
    private ListView listView;
    private List<String> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tiaosi_acticity);
        listView=(ListView)findViewById(R.id.list1);
        list_data = new ArrayList<>();
        list_data.add("1234adadd");
        list_data.add("123fddadd");
        list_data.add("12dgdbbdadd");
        list_data.add("1234fscbbb");
        list_data.add("895224adadd");
        Myadapter myadapter= new Myadapter(this,list_data);
        listView.setAdapter(myadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(List_tiaosiActicity.this,"w我也被点击了，你呢",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
