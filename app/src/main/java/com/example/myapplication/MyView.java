package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyView extends View {
    private Context context;
    private Bitmap mbitmap;
    private Canvas mcanvas;
    private Path mpath;
    private Paint bitmappaint;
    private Paint paint;
    private float mx,my;
    private int currentsize =5;
    private int currentstyle=1;
    private int currencolor=Color.BLACK;
    private DrawPath drawPath;
    private static List<DrawPath> savepath;
    private static List<DrawPath> deletepath;
    private int screenwidth;
    private int screenheight;
    private int [] paintcolor;

    public MyView(Context context,int height,int width) {
        super(context);
        this.context = context;
        screenheight = height;
        screenwidth = width;
        paintcolor = new int []{Color.BLACK,Color.RED,Color.GRAY,Color.GREEN,Color.BLUE,Color.YELLOW};
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        init_cavas();
        savepath = new ArrayList<>();
        deletepath = new ArrayList<>();
    }

    public void init_cavas() {
        setPaintStyle();         //初始化画笔的样式
        mbitmap = Bitmap.createBitmap(screenwidth,screenheight,Bitmap.Config.ARGB_8888);
        bitmappaint = new Paint(Paint.DITHER_FLAG);
        mcanvas = new Canvas(mbitmap);
        mcanvas.drawColor(Color.TRANSPARENT);
    }
    public void setPaintStyle() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setDither(true);
        if(currentstyle==1){
            paint.setStrokeWidth(currentsize);
            paint.setColor(currencolor);
        }else {//橡皮擦的样式
            paint.setStrokeWidth(40);
            paint.setAlpha(0);
            paint.setColor(Color.TRANSPARENT);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mpath = new Path();
                drawPath = new DrawPath();
                drawPath.paint = paint;
                drawPath.path = mpath;
                mpath.moveTo(x,y);
                mx=x;
                my=y;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(x-mx)>5 && Math.abs(y-my)>5){//表示可以移动
                    mpath.quadTo(mx,my,x,y);
                    mx=x;
                    my=y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mpath.lineTo(mx,my);
                mcanvas.drawPath(mpath,paint);
                savepath.add(drawPath);
                mpath=null;
                invalidate();
                break;
        }
        return true;
    }
    public void redo(){
        if(savepath!=null && savepath.size()>0){
            savepath.clear();
            redrawPath();
        }
    }
    public void undo(){
        if(savepath!=null && savepath.size()>0){
            deletepath.add(savepath.get(savepath.size()-1));  //delet增加最后一个数据
            savepath.remove(savepath.size()-1);   //save则删掉最后一组数据
            redrawPath();
        }
    }
    /*
    将删除的那条线重新添加进画板
     */
    public void recover(){
        if(deletepath.size()>0){
            DrawPath dp = deletepath.get(deletepath.size()-1);
            savepath.add(dp);
            mcanvas.drawPath(dp.path,dp.paint);
            deletepath.remove(deletepath.size()-1);
            invalidate();
        }
    }
    public void savetoSD(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String s = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+simpleDateFormat.format(date)+".png";
        File file = new File(s);
        Log.e("myview",s);
        Log.e("myview","文件存在");
        if (!file.exists()){
            Log.e("myview","chuangjianwenjian");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("myview",file.getAbsolutePath());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(s));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        Log.e("myview","bitmap为空");
        if(mbitmap!=null){
            Log.e("myview","bitmap不为空");
        }
        if(mbitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream)){
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fileOutputStream!=null){
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         在安卓版本4.0之上，无法发送广播更新图片，否则：Caused by: java.lang.SecurityException: Permission Denial:
          not allowed to send broadcast android.int
          因此两个解决办法进行解决，都可以解决
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4版本以上
            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            scanIntent.setData(contentUri);
            context.sendBroadcast(scanIntent);
        } else {
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            context.sendBroadcast(intent);
        }
        //MediaScannerConnection.scanFile(context, new String[] {file.getAbsolutePath()},null, null);
      /*
        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
        context.sendBroadcast(intent);*/
        Toast.makeText(context,"图片保存成功",Toast.LENGTH_SHORT).show();
    }


    public void redrawPath() {
        mcanvas.setBitmap(mbitmap);
        init_cavas();
        if(savepath.size()>0){
            for(DrawPath dp :savepath){
                mcanvas.drawPath(dp.path,dp.paint);
            }
        }
        invalidate();
    }
    public void selectPaintstyle(int size ,int color){
        currencolor = paintcolor[color];
        currentsize = size;
        setPaintStyle();
    }

    public void selectPaintstyle(int mode){
        currentstyle = mode;
        setPaintStyle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mbitmap,0,0,bitmappaint);
        if(mpath!=null){
            canvas.drawPath(mpath,paint);
        }
    }

    private class DrawPath {
        private Path path;
        private Paint paint;
    }
}
