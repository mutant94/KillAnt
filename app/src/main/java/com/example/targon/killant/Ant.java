package com.example.targon.killant;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

/**
 * Created by Targon on 15.02.2016.
 */
public class Ant extends GameObject{
    private int speed;
    private int score;
    Random r=new Random();
    private final static Animation animation=new Animation();
    private Bitmap spritesheet;
    private int WIDTH;
    private int HEIGHT;
    private float degress;
    private boolean lose;
    public Ant(int x, int y, Bitmap res, int s, int numFrames, int viewW, int viewH){
        this.x=x;
        this.y=y;
        width=res.getWidth();
        height=res.getHeight()/2-1;
        score=s;
        speed=5+(int)(score/100.1);
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


        degress=(float)Math.toDegrees(Math.atan2(y-centW, x-centH));

        degress-=90;
        while(degress<0){
            degress+=360;
        }
        lose=false;




    }
    public void update(){
        int centW=WIDTH/2;
        int centH=HEIGHT/2;
        degress=(float)Math.toDegrees(Math.atan2(y-centW, x-centH));

        degress-=90;
        while(degress<0){
            degress+=360;
        }

        int numcw;
        if(y>centW){
            numcw=1;
        }else {
            numcw=2;
        }
        if(x<centH){
            numcw+=2;
        }

        int length=(int)lenght(this.x, this.y);
        int sx=0;
        int sy=0;
        if(length<=speed+15 && !lose){

            switch (numcw){
                case 1:
                    sx=(int)(Math.sin(360-degress)*length);
                    sy=(int)(Math.sin(degress-270)*length);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);

                    x-=sx;
                    y-=sy;
                    break;
                case 2:
                    sx=(int)(Math.sin(degress-180)*length);
                    sy=(int)(Math.sin(270-degress)*length);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);

                    x-=sx;
                    y+=sy;
                    break;
                case 3:
                    sx=(int)(Math.sin(degress)*length);
                    sy=(int)(Math.sin(90-degress)*length);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);

                    x+=sx;
                    y-=sy;
                    break;
                case 4:
                    sx=(int)(Math.sin(180-degress)*length);
                    sy=(int)(Math.sin(degress-90)*length);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);

                    x+=sx;
                    y+=sy;
                    break;
            }
            //sx=(int)(Math.sin(270 + degress)*length);
            //sy=(int)(Math.cos(degress + 270)*length);
            //sx=Math.abs(sx);
            //sy=Math.abs(sy);
            lose = true;
        }else if(!lose){

            switch (numcw){
                case 1:
                    sx=(int)(Math.sin(360-degress)*speed);
                    sy=(int)(Math.sin(degress-270)*speed);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);
                    if(sx+sy<speed/2){
                        sx=3;
                        sy=3;
                    }
                    x-=sx;
                    y-=sy;
                    break;
                case 2:
                    sx=(int)(Math.sin(degress-180)*speed);
                    sy=(int)(Math.sin(270-degress)*speed);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);
                    if(sx+sy<speed/2){
                        sx=3;
                        sy=3;
                    }
                    x-=sx;
                    y+=sy;
                    break;
                case 3:
                    sx=(int)(Math.sin(degress)*speed);
                    sy=(int)(Math.sin(90-degress)*speed);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);
                    if(sx+sy<speed/2){
                        sx=3;
                        sy=3;
                    }
                    x+=sx;
                    y-=sy;
                    break;
                case 4:
                    sx=(int)(Math.sin(180-degress)*speed);
                    sy=(int)(Math.sin(degress-90)*speed);
                    sx=Math.abs(sx);
                    sy=Math.abs(sy);
                    if(sx+sy<speed/2){
                        sx=3;
                        sy=3;
                    }
                    x+=sx;
                    y+=sy;
                    break;
            }

        }
        //System.out.println("!!!!!!!!!!!!!!!!!!! \n KAT: "+degress+"\n x: "+this.x+" y:"+this.y + "\n numercw: "+ numcw +"\n length: "+length+"\n speed: "+speed+ "\n sx i sy: "+sx+" "+sy+"\n lose:"+lose);
        animation.update();

    }
    public void draw(Canvas canvas){
        try{
            Matrix m=new Matrix();
            m.postRotate(degress);
            Bitmap b=Bitmap.createBitmap(animation.getImage(), 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), m, true);

            canvas.drawBitmap(b, x, y, null);

        }catch (Exception e){}
    }
    public double lenght(int x, int y){
        return Math.sqrt( Math.pow(x- HEIGHT/2, 2)+ Math.pow(y-WIDTH/2, 2) );
    }
    public boolean getLose(){
        return lose;
    }
}
