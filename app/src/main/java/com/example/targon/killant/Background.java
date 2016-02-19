package com.example.targon.killant;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Targon on 18.02.2016.
 */
public class Background {
    private int x;
    private int y;
    public Background(int h, int w){
        x=h;
        y=w;
    }
    public void update(){}
    public void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,x, y, paint);
    }
}
