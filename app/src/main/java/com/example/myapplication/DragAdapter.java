package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DragAdapter extends BaseAdapter {
    private Context mcontext;
    private List<String> list;
    private LayoutInflater minflater;

    public DragAdapter(Context con,List<String>list) {
        mcontext=con;
        this.list=list;
        minflater = LayoutInflater.from(mcontext);
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
        Viewholder viewholder;
        if(convertView==null){
            viewholder = new Viewholder();
            convertView =  minflater.inflate(R.layout.drag_list_item,null);
            viewholder.imageView = convertView.findViewById(R.id.drag_image);
            viewholder.textView = convertView.findViewById(R.id.drag_tv);
            convertView.setTag(viewholder);
        }else {
            viewholder=(Viewholder)convertView.getTag();
        }
        viewholder.textView.setText(list.get(position));
       // viewholder.textView.setOnDragListener(m);
        viewholder.imageView.setImageResource(R.drawable.edit);

        return convertView;
    }
    static class Viewholder{
        ImageView imageView;
        TextView  textView;
    }
}
