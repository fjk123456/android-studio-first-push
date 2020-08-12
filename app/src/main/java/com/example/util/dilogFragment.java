package com.example.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class dilogFragment extends DialogFragment implements View.OnClickListener{
    private ArrayList<String> list_array = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    String filesavepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SuperMap1/collector";
    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
    // 照片命名
    String origFileName = "pic_origin_" + timeStamp + ".jpg";
    String cropFileName = "pic_after_crop_" + timeStamp + ".jpg";
    String soundName = "sound_" + timeStamp + ".mp3";
    String videoName = "video_" + timeStamp + ".mp4";
    private Uri picUri;
    private Uri videoUri;
    private Uri cropUri = Uri.fromFile(new File(filesavepath,cropFileName));
    private int width1;
    private afterPhotoComplete afterPhoComp;
    private aftarSoundComplete afterSouComp;

    public static dilogFragment getInstance(ArrayList<String> list1,int width){
        dilogFragment dilogFrag = new dilogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("map",list1);
        bundle.putInt("width",width);
        dilogFrag.setArguments(bundle);
        return dilogFrag;
    }
    public  interface  afterPhotoComplete{
        void getPhotoPath(String path);
    }
    public interface aftarSoundComplete{
        void getSoundPath(String path);
    }
    private void setafterPhotoComplete(afterPhotoComplete comp){
        afterPhoComp = comp;
    }
    private void setafterSoundComplete(aftarSoundComplete sound){
        afterSouComp = sound;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        setafterPhotoComplete(activity);
        setafterSoundComplete(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final File  file = new File(filesavepath);
        if(!file.exists()){
            file.mkdirs();
        }
        list_array = getArguments().getStringArrayList("map");
        width1 = getArguments().getInt("width");
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialogfrag,null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width1, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
       ListView listData = view.findViewById(R.id.listData);
       arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,list_array);
       listData.setAdapter(arrayAdapter);
       listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       if(Build.VERSION.SDK_INT>24){
                           picUri= FileProvider.getUriForFile(getContext(), "com.example.myapplication.FileProvider", new File(filesavepath,origFileName));
                           intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                       }else {
                           picUri = Uri.fromFile(new File(filesavepath,origFileName));
                       }
                       intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                       ((Activity) getContext()).startActivityForResult(intent, 11);
                       break;
                   case 1:
                       File file1 = new File(filesavepath,soundName);
                       if(file.exists()){
                           file.delete();
                       }
                       try {
                           file.createNewFile();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       Intent intentsound = new Intent();
                       intentsound.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                       ((Activity) getContext()).startActivityForResult(intentsound, 13);
                       break;
                   case 2:
                       Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                       if(Build.VERSION.SDK_INT>24){
                           videoUri= FileProvider.getUriForFile(getContext(), "com.example.myapplication.FileProvider", new File(filesavepath,videoName));
                           videointent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                       }else {
                           videoUri = Uri.fromFile(new File(filesavepath,videoName));
                       }
                       videointent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                       videointent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,256*512);
                       startActivityForResult(videointent, 14);
               }
           }
       });
        ImageView add = view.findViewById(R.id.add);
        add.setOnClickListener(this);
        ImageView delet = view.findViewById(R.id.delet);
        delet.setOnClickListener(this);
        builder.setView(view);
       return builder.create();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.add:
               int data = list_array.size();
               int temp = data+1;
               list_array.add(String.valueOf(temp));
               arrayAdapter.notifyDataSetChanged();
               break;
           case R.id.delet:
               super.dismiss();
               break;
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && Activity.RESULT_OK==resultCode){
            startCrop(picUri,cropUri);
        }else if(requestCode==12 && Activity.RESULT_OK==resultCode){
            afterPhoComp.getPhotoPath(filesavepath+"/"+ origFileName);
        }else if(requestCode==13 && Activity.RESULT_OK==resultCode){
            Uri uri = data.getData();
            String path = getUriToPath(uri);
            if(!path.equals("")){
                moveToPointPosition(path,filesavepath+"/"+ soundName);
            }
            afterSouComp.getSoundPath(filesavepath+"/"+ soundName);
            Log.e("sound",path);
        }
        else {
            Toast.makeText(getContext(),"视频录制成功",Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToPointPosition(String path, String target) {
        InputStream inputStream =null;
        OutputStream outputStream =null;
        try {
            inputStream = new FileInputStream(path);
            outputStream = new FileOutputStream(target);
            byte [] bytes = new byte[1024];
            int length;
            try {
                while ((length=inputStream.read(bytes))!=-1){
                    Log.e("sound",length+"");
                    outputStream.write(bytes,0,length);
                }
                Toast.makeText(getContext(),"音频保存完成",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(outputStream!=null){
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(inputStream!=null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(path);
        file.delete();
    }

    private String getUriToPath(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        String temp = cursor.getString(index);
        cursor.close();
        return temp;
    }

    private void startCrop(Uri picUri, Uri cropUri) {
        Intent intentCamera = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentCamera.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intentCamera.setDataAndType(picUri, "image/*");// 源文件地址
        intentCamera.putExtra("crop", true);
        intentCamera.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intentCamera.putExtra("noFaceDetection", true);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// 输出地址
        intentCamera.putExtra("return-data", false);
        ((Activity)getContext()).startActivityForResult(intentCamera,12);
    }
}
