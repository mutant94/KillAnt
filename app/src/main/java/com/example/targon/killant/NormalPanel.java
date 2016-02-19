package com.example.targon.killant;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Targon on 15.02.2016.
 */
public class NormalPanel extends SurfaceView implements SurfaceHolder.Callback {

    public int HEIGHT;
    public int WIDTH;
    private NormalThread thread;
    private int score=0;
    Random r= new Random();
    //object
    Point point;
    Background background;
    static ArrayList<Ant> ants;
    //timeant

    //boolean for game

    public NormalPanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread=new NormalThread(getHolder(), this);
        setFocusable(true);


        //get w and h
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        android.graphics.Point size=new android.graphics.Point();
        display.getSize(size);
        HEIGHT=size.x;
        WIDTH=size.y;
        System.out.println("WYSOKOSC: "+HEIGHT);
        System.out.println("SZEROKOSC: " + WIDTH);

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //create all object
        point=new Point((int)(HEIGHT/2), (int)(WIDTH/2));
        ants=new ArrayList<Ant>();
        background=new Background(HEIGHT, WIDTH);
        //ants.add(0, new Ant((int)(HEIGHT/2)-50, (int)(WIDTH/2), BitmapFactory.decodeResource(getResources(), R.drawable.mrowka), 20, 49, score, 1, WIDTH, HEIGHT));
        //start game loop

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        int count=0;
        while(count<1000){
            try{
                thread.setRunning(false);
                thread.join();
                thread=null;
            }catch (InterruptedException e ){e.printStackTrace();}
            count++;
        }
    }
    public void update(){


        System.out.println(score);

        if(score%50==0) {
            int rand = r.nextInt() % 4;
            int randx = -1;
            int randy = -1;
            while (randx < 0 || randy < 0) {
                randx = r.nextInt() % HEIGHT;
                randy = r.nextInt() % WIDTH;
            }
            System.out.println("X: " + randx + " Y: " + randy);
            System.out.println("create ant!!!!");
            switch (rand) {
                case 0:
                    //randy=0;
                    break;
                case 1:
                    //randx=0;
                    break;
                case 2:
                    //randy=WIDTH;
                    break;
                case 3:
                    //randx=HEIGHT;
                    break;
            }
            ants.add(ants.size(), new Ant(randx, randy, BitmapFactory.decodeResource(getResources(), R.drawable.mrowka), 20, 49, score, 2, WIDTH, HEIGHT));
            System.out.println("size: " + ants.size());
        }

        score++;
        //update object
        point.update();
        for(Ant a:ants){
            a.update();
        }

    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //final float scaleFactoryX=getWidth()/(WIDTH*1.f);
        //final float scaleFactoryY=getHeight()/(HEIGHT*1.f);
        if(canvas!=null){
            int savedState=canvas.save();
            //scale
            //canvas.scale(scaleFactoryX, scaleFactoryY);
            //object
            background.draw(canvas);
            point.draw(canvas);

            for(Ant a:ants){
                a.draw(canvas);
            }


            //restore
            canvas.restoreToCount(savedState);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        System.out.println("x: "+x+" y: "+y);
        return super.onTouchEvent(event);
    }

}
