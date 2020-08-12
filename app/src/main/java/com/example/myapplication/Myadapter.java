package com.example.myapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

class Myadapter extends BaseAdapter {
    private Context mcontext;
    private List<String>list = new ArrayList<>();
    private LayoutInflater minflater;
    public Myadapter(Context context, List<String> data) {
        mcontext=context;
        list=data;
        minflater= LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder= new ViewHolder();
            convertView = minflater.inflate(R.layout.list_item, null);
            holder.seekBar= (SeekBar) convertView.findViewById(R.id.list_seek);
            holder.button=(Button) convertView.findViewById(R.id.list_button);
            holder.button2=(Button) convertView.findViewById(R.id.list_button2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.button2.setText(list.get(position));
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mcontext,"当前进度为："+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button.setText("我被点击了");
            }
        });
        return convertView;
    }

    private class ViewHolder {
        SeekBar seekBar;
        Button button;
        Button button2;
    }

}
