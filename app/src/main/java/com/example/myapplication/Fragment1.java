package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

public class Fragment1 extends Fragment {
    private String text;
    @BindView(R.id.fra_tv_1) TextView Fra_tv_1;

    public static Fragment1 getInstance(String t){
        Fragment1 fragment = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putString("1",t);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        text = bundle.getString("1","fragment1");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);
        Fra_tv_1 = view.findViewById(R.id.fra_tv_1);
        Fra_tv_1.setText(text);
        Fra_tv_1.setTextColor(Color.RED);
        return view;
    }
}
