package com.example.myapplication;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context mcontext;
    private LayoutInflater inflater;
    private List<String> mlist;
    private Map<String,List<String>> mmap;


    public ExpandableAdapter(Context context, List<String>list, Map<String,List<String>>map){
        mcontext=context;
        inflater=LayoutInflater.from(mcontext);
        mlist = list;
        mmap = map;
    }

    @Override
    public int getGroupCount() {
        return mlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  mmap.get(mlist.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mmap.get(mlist.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ParentViewHolder parentViewHolder;
        if(convertView==null){
            parentViewHolder = new ParentViewHolder();
            convertView = inflater.inflate(R.layout.first_parent_tv,null);
            parentViewHolder.first_tv = (TextView)convertView.findViewById(R.id.first_tv);
            convertView.setTag(parentViewHolder);
        }else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        parentViewHolder.first_tv.setText(mlist.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SonViewHolder sonViewHolder;
        if(convertView==null){
            sonViewHolder = new SonViewHolder();
            convertView = inflater.inflate(R.layout.second_son_tv,null);
            sonViewHolder.son_tv = (TextView)convertView.findViewById(R.id.second_tv);
            convertView.setTag(sonViewHolder);
        }else {
            sonViewHolder = (SonViewHolder) convertView.getTag();
        }
        sonViewHolder.son_tv.setText(mlist.get(groupPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ParentViewHolder{
        TextView first_tv;
    }
    class SonViewHolder{
        TextView son_tv;
    }
}
