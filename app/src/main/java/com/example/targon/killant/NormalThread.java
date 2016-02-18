package com.example.targon.killant;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Targon on 15.02.2016.
 */
public class NormalThread extends Thread{
    private int FPS= 30;
    private SurfaceHolder surfaceHolder;
    private NormalPanel normalPanel;
    private boolean running;
    public static Canvas canvas;
    public NormalThread(SurfaceHolder s, NormalPanel np){
        super();
        this.surfaceHolder=s;
        this.normalPanel=np;
    }
    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime=0;
        int frameCount=0;
        long targetTime=1000/FPS;
        while (running){
            startTime=System.nanoTime();
            canvas=null;
            try{
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.normalPanel.update();
                    this.normalPanel.draw(canvas);
                }
            }catch (Exception e){}finally{
                if(canvas!=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){}
                }
            }
            timeMillis=(System.nanoTime()-startTime)/1000000;
            waitTime=targetTime-timeMillis;
            try{
                this.sleep(waitTime);
            }catch (Exception e){}
            totalTime +=System.nanoTime()+startTime;
            frameCount++;
            if(frameCount==FPS){
                frameCount=0;
                totalTime=0;
            }

        }
    }


    public void setRunning(boolean r){running =r;}
}
