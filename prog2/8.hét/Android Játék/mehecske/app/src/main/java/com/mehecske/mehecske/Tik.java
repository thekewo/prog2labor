package com.mehecske.mehecske;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;


public class Tik extends GameObject{
    private int[] coordsX = new int[8];                                                              //azokat a koordinátákat tartalmazza, amelyeket majd vizsgálunk ütközéskor
    private int[] coordsY = new int[8];


    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;



    public Tik(Bitmap img, int speed, int startingX, int startingY) {
        super(img, speed, startingX, startingY);
        setResizedBitmap(getBitmap(), 0.35);
        Random random = new Random();

        setTowardsX(random.nextInt(Resources.getSystem().getDisplayMetrics().widthPixels));
        setTowardsY(random.nextInt(Resources.getSystem().getDisplayMetrics().heightPixels));

    }

    @Override
    public void update() {
        coordsX[0] = getCurrentX();                                                                 //topleft
        coordsY[0] = getCurrentY();

        coordsX[1] = coordsX[0] + getResizedBitmap().getWidth()/2;                                  //topmid
        coordsY[1] = coordsY[0];

        coordsX[2] = coordsX[0] + getResizedBitmap().getWidth();                                    //topright
        coordsY[2] = coordsY[0];

        coordsY[3] = coordsX[0];                                                                    //midleft
        coordsX[3] = coordsY[0] + getResizedBitmap().getHeight()/2;

        coordsX[4] = coordsX[0] + getResizedBitmap().getWidth();                                    //midright
        coordsX[4] = coordsY[0] + getResizedBitmap().getHeight()/2;

        coordsX[5] = coordsX[0];                                                                    //botleft
        coordsY[5] = coordsY[0] + getResizedBitmap().getHeight();

        coordsX[6] = coordsX[0] + getResizedBitmap().getWidth()/2;                                  //botmid
        coordsY[6] = coordsY[0] + getResizedBitmap().getHeight();

        coordsX[7] = coordsX[0] + getResizedBitmap().getWidth();                                    //botright
        coordsY[7] = coordsY[0] + getResizedBitmap().getHeight();





        if(getCurrentY() != getTowardsY() || getCurrentX() != getTowardsX()){
            if(getCurrentX() < getTowardsX())
                setCurrentX(getCurrentX()+getSpeed());
            if(getCurrentY() < getTowardsY())
                setCurrentY(getCurrentY()+getSpeed());
            if(getCurrentX() > getTowardsX())
                setCurrentX(getCurrentX()-getSpeed());
            if(getCurrentY() > getTowardsY())
                setCurrentY(getCurrentY()-getSpeed());
        }
        else{
            Random random = new Random();

            setTowardsX(random.nextInt(Resources.getSystem().getDisplayMetrics().widthPixels));
            setTowardsY(random.nextInt(Resources.getSystem().getDisplayMetrics().heightPixels));

        }





    }



    public int[] getCoordsX() {
        return coordsX;
    }

    public int[] getCoordsY() {
        return coordsY;
    }
}
