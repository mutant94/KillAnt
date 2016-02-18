package com.example.targon.killant;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Targon on 15.02.2016.
 */
public class Ant extends GameObject{
    private int speed;
    private int score;
    Random r=new Random();
    private Animation animation=new Animation();
    private Bitmap spritesheet;
    private int WIDTH;
    private int HEIGHT;
    private float degress;
    public Ant(int x, int y, Bitmap res, int w, int h, int s, int numFrames, int viewW, int viewH){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        score=s;
        speed=30+(int)(score/10.1);
        WIDTH=viewW;
        HEIGHT=viewH;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet=res;
        for(int i=0; i<image.length; i++){
            image[i]=Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(100-speed);
        int centW=WIDTH/2;
        int centH=HEIGHT/2;
        int numcw=0;
        if(y>centW){
            numcw=1;
        }else {
            numcw=2;
        }
        if(x<centH){
            numcw+=2;
        }
        degress=(float)Math.toDegrees(Math.atan2(0, 0));
        if(degress<0){
            degress+=360;
        }
    }
    public void update(){
        int centW=WIDTH/2;
        int centH=HEIGHT/2;

    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(animation.getImage(), x, y, null);
            canvas.rotate(degress);
        }catch (Exception e){}
    }
    public double lenght(int x, int y){
        return Math.sqrt( Math.pow(x- HEIGHT/2, 2)+ Math.pow(y-WIDTH/2, 2) );
    }
}
