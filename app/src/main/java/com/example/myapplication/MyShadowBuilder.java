package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

class MyShadowBuilder extends View.DragShadowBuilder {
    private static Drawable shadow;
    private int width,height;
    private Bitmap bitmap;

    public MyShadowBuilder(View v) {
        super(v);
        bitmap = Bitmap.createBitmap(v.getDrawingCache());
        shadow = new ColorDrawable(Color.LTGRAY);
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        width = getView().getWidth()*4/3;
        height = getView().getWidth()*4/3;
        shadow.setBounds(0,0,width,height);
        outShadowSize.set(width,height);
        outShadowTouchPoint.set(width/2,height/2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        super.onDrawShadow(canvas);
        canvas.drawBitmap(bitmap,0,0,new Paint());
    }
}
