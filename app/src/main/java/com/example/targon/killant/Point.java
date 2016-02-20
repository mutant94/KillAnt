package com.example.targon.killant;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Targon on 15.02.2016.
 */
public class Point extends GameObject {
    int r;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
        r=20;
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(0xFF000000);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x, y, r, paint);
    }

}
