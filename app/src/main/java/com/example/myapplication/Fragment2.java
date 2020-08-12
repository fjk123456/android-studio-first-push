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

public class Fragment2 extends Fragment {
    private String text;
    @BindView(R.id.fra_tv_1) TextView fra_tv_2;

    public static Fragment2 getInstance(String t){
        Fragment2 fragment = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("2",t);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        text = bundle.getString("2","fragment2");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,container,false);
        fra_tv_2 = view.findViewById(R.id.fra_tv_2);
        fra_tv_2.setText(text);
        fra_tv_2.setTextColor(Color.RED);
        return view;
    }
}