package com.mehecske.mehecske;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Meh meh;
    private Tik[] tikek;

    //private Point playerPoint;



    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);                                                              //események megszakításához kell

        meh = new Meh(BitmapFactory.decodeResource(getResources(),R.drawable.meh), 2, this.getResources().getDisplayMetrics().widthPixels/2, this.getResources().getDisplayMetrics().heightPixels/2);
        //a mehecske a kepernyo kozepen jelenjen meg
        tikek = new Tik[4];
        tikek[0] = new Tik(BitmapFactory.decodeResource(getResources(),R.drawable.tik), 4, this.getResources().getDisplayMetrics().widthPixels-120, this.getResources().getDisplayMetrics().heightPixels-120);
        tikek[1] = new Tik(BitmapFactory.decodeResource(getResources(),R.drawable.tik), 4, this.getResources().getDisplayMetrics().widthPixels-120,120);
        tikek[2] = new Tik(BitmapFactory.decodeResource(getResources(),R.drawable.tik), 4, 120, this.getResources().getDisplayMetrics().heightPixels-120);
        tikek[3] = new Tik(BitmapFactory.decodeResource(getResources(),R.drawable.tik), 4, 120,120);


        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(), this);



        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();                                                                      //Lezárja a threadet
            } catch (InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    public void update(){
        meh.update();
        tikek[0].update();
        tikek[1].update();
        tikek[2].update();
        tikek[3].update();

        for(int i = 0; i < 8; i++){
            if(meh.getCurrentX() <= tikek[0].getCoordsX()[i] && meh.getCurrentX() + meh.getResizedBitmap().getWidth() >= tikek[0].getCoordsX()[i] &&
                    meh.getCurrentY() <= tikek[0].getCoordsY()[i] && meh.getCurrentY() + meh.getResizedBitmap().getHeight() >= tikek[0].getCoordsY()[i])
                thread.setRunning(false);
            else if(meh.getCurrentX() <= tikek[1].getCoordsX()[i] && meh.getCurrentX() + meh.getResizedBitmap().getWidth() >= tikek[1].getCoordsX()[i] &&
                    meh.getCurrentY() <= tikek[1].getCoordsY()[i] && meh.getCurrentY() + meh.getResizedBitmap().getHeight() >= tikek[1].getCoordsY()[i])
                thread.setRunning(false);
            else if(meh.getCurrentX() <= tikek[2].getCoordsX()[i] && meh.getCurrentX() + meh.getResizedBitmap().getWidth() >= tikek[2].getCoordsX()[i] &&
                    meh.getCurrentY() <= tikek[2].getCoordsY()[i] && meh.getCurrentY() + meh.getResizedBitmap().getHeight() >= tikek[2].getCoordsY()[i])
                thread.setRunning(false);
            else if(meh.getCurrentX() <= tikek[3].getCoordsX()[i] && meh.getCurrentX() + meh.getResizedBitmap().getWidth() >= tikek[3].getCoordsX()[i] &&
                    meh.getCurrentY() <= tikek[3].getCoordsY()[i] && meh.getCurrentY() + meh.getResizedBitmap().getHeight() >= tikek[3].getCoordsY()[i])
                thread.setRunning(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:                                                           //lenyomással jelezhetjük, hogy merrre menjen a méhecskénk
                meh.setTowardsX((int)event.getX());
                meh.setTowardsY((int)event.getY());
                //playerPoint.set((int)event.getX(), (int)event.getY());
        }


        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        canvas.drawColor(Color.WHITE);

        meh.draw(canvas);
        tikek[0].draw(canvas);
        tikek[1].draw(canvas);
        tikek[2].draw(canvas);
        tikek[3].draw(canvas);

        if(canvas != null){

        }
    }


}
