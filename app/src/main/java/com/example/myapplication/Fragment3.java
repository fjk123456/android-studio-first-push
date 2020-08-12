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

public class Fragment3 extends Fragment {
    private String text;
    @BindView(R.id.fra_tv_1) TextView Fra_tv_1;

    public static Fragment3 getInstance(String t){
        Fragment3 fragment = new Fragment3();
        Bundle bundle = new Bundle();
        bundle.putString("3",t);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        text = bundle.getString("3","fragment3");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);
        Fra_tv_1 = view.findViewById(R.id.fra_tv_3);
        Fra_tv_1.setText(text);
        Fra_tv_1.setTextColor(Color.RED);
        return view;
    }
}