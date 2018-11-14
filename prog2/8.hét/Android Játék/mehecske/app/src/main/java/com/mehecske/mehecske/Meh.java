package com.mehecske.mehecske;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Meh extends GameObject {

    public Meh(Bitmap img, int speed, int startingX, int startingY) {
        super(img, speed, startingX, startingY);
        setResizedBitmap(getBitmap(), 0.1);
    }


    @Override
    public void update() {
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

    }


}
