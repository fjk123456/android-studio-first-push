package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DragListView extends AppCompatActivity {
    private ListView listView;
   // private String [] list = new String[]{"e","d","c","b","a"};
    private  List<String> list = new ArrayList<>();
    private DragAdapter adapter;
    private MyDragEventListener mDragListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list_view);
        listView = findViewById(R.id.draglist);
        list.add("faatherandmother");
        list.add("motherandfather");
        list.add("mjnfjsslfsf,smfbfy");
        list.add("mjnfjss5995d55d22f5f5");
        list.add("123088sdskfurolmvn");
        list.add("123088sdfffffffff");
        adapter=new DragAdapter(DragListView.this,list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String orin_data = list.get(position);
                ClipData.Item item = new ClipData.Item(orin_data);
                ClipData.Item item1 = new ClipData.Item(position+"");
                ClipData clipData = new ClipData(orin_data,new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},item);
                clipData.addItem(item1);
                View.DragShadowBuilder shadowBuilder = new MyShadowBuilder(view);
                view.startDrag(clipData,shadowBuilder,null ,0);
                return true;
            }
        });
        mDragListen = new MyDragEventListener();
    }

    protected class MyDragEventListener implements  View.OnDragListener{
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch(action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    ClipData.Item item2 = event.getClipData().getItemAt(1);
                    int pos = Integer.valueOf(item2.getText().toString());
                    String dragData = item.getText().toString();
                    int dragedPos = (Integer) v.getTag();
                    list.remove(pos);
                    list.add(pos,list.get(dragedPos));
                    list.remove(dragedPos);
                    list.add(dragedPos,dragData);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    adapter.notifyDataSetChanged();
                    return false;
                default:

            }
            return false;
        }
    }
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
            com.example.myapplication.DragAdapter.Viewholder viewholder;
            if(convertView==null){
                viewholder = new com.example.myapplication.DragAdapter.Viewholder();
                convertView =  minflater.inflate(R.layout.drag_list_item,null);
                viewholder.imageView = convertView.findViewById(R.id.drag_image);
                viewholder.textView = convertView.findViewById(R.id.drag_tv);
                convertView.setTag(viewholder);
            }else {
                viewholder=(com.example.myapplication.DragAdapter.Viewholder)convertView.getTag();
            }
            viewholder.textView.setText(list.get(position));
            viewholder.textView.setOnDragListener(mDragListen);
            viewholder.imageView.setImageResource(R.drawable.edit);

            return convertView;
        }
        public class Viewholder{
            ImageView imageView;
            TextView textView;
        }
    }

}



