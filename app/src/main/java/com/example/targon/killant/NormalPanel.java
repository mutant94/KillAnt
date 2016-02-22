package com.example.targon.killant;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    Random r= new Random();
    //object
    Point point;
    Background background;
    static ArrayList<Ant> ants;
    //timeant
    private int score=0;
    //killer :D
    private int countKill=-1;
    private int best=0;
    //save
    private Game game;
    //boolean for game
    private boolean click=false;
    private boolean play=false;
    private boolean firstGame=true;
    public NormalPanel(Context context, Game game){
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
        //save
        this.game=game;
        best=game.getBest();
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //create all object
        //newGame();

        //ants.add(0, new Ant((int)(HEIGHT/2)-50, (int)(WIDTH/2), BitmapFactory.decodeResource(getResources(), R.drawable.mrowka), 20, 49, score, 1, WIDTH, HEIGHT));
        //start game loop
        background=new Background(HEIGHT, WIDTH);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry=true;
        thread.setRunning(false);
        while(retry){
            try{
                thread.join();
                thread=null;
                retry=false;
            }catch (InterruptedException e ){e.printStackTrace();}
        }
    }
    public void update(){
        if(play) {
            if (score % 10 == 0) {
                int rand = r.nextInt() % 4;
                int randx = -1;
                int randy = -1;
                while (randx < 0 || randy < 0) {
                    randx = r.nextInt() % HEIGHT;
                    randy = r.nextInt() % WIDTH;

                }
                while (rand > 3 || rand < 0) {
                    rand = r.nextInt() % 4;
                }
                switch (rand) {
                    case 0:
                        randy = -50;
                        break;
                    case 1:
                        randx = -50;
                        break;
                    case 2:
                        randy = WIDTH;
                        break;
                    case 3:
                        randx = HEIGHT;
                        break;
                }
                ants.add(ants.size(), new Ant(randx, randy, BitmapFactory.decodeResource(getResources(), R.drawable.mrowka), score, 2, WIDTH, HEIGHT));
            }
            score++;
            //update object
            point.update();
            for (Ant a : ants) {
                a.update();
                if(a.getLose()){
                    play=false;
                }
            }
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
            if(play) {

                point.draw(canvas);

                for (Ant a : ants) {
                    a.draw(canvas);
                }

            }

            drawText(canvas);
            //restore
            canvas.restoreToCount(savedState);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        int er=25;
        Ant a;
        if(event.getAction()==MotionEvent.ACTION_DOWN&&!click&&play){
            click=true;
            Rect rect=new Rect(x-er, y-er, x+er, y+er);
            for(int i=0; i<ants.size(); i++){
                a=ants.get(i);
                if(Rect.intersects(rect, a.getRectangle())){
                    ants.remove(i);
                    countKill++;
                }
            }
            return true;
        }else if(event.getAction()==MotionEvent.ACTION_DOWN&&!play&&!click){
            click=true;

            newGame();

            return true;
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            click=false;
            return true;
        }



        //System.out.println("x: "+x+" y: "+y);
        return super.onTouchEvent(event);
    }
    private void drawText(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(10);

        canvas.drawText("BEST: " + best, 0, 10, paint);

        if(play){
            canvas.drawText("Kill ants: " + countKill, HEIGHT - 60, 10, paint);
        }else{
            paint.setTextSize(20);
            canvas.drawText("Kill ants and safe drop", (HEIGHT/2)-80, WIDTH/2 , paint);
            canvas.drawText("Click to start", (HEIGHT/2)-50, (WIDTH/2)+25 ,paint);
            if(!firstGame){
                canvas.drawText("You kill "+countKill+" ants", (HEIGHT/2)-70, (WIDTH/2)+50 ,paint);
            }
        }
    }
    private void newGame(){
        firstGame=false;
        point=new Point((int)(HEIGHT/2), (int)(WIDTH/2));
        ants=new ArrayList<Ant>();
        background=new Background(HEIGHT, WIDTH);
        score=0;
        System.out.println(best+" !!!!!!!!!!");
        System.out.println(countKill + " !!!!!!!!!!");
        if(best<countKill){
            best=countKill;
            game.saveBest(best);
            System.out.println("!!!!!!!!!!NEW RECORD");
        }
        play=true;
        countKill=0;
    }



}
