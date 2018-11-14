package com.mehecske.mehecske;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameObject {
    private Bitmap bitmap;                                                                          //eredeti kép
    private Bitmap resizedBitmap;                                                                   //átméretezett kép
    private int speed;                                                                              //megadja, hogy milyen sebességgel mozog


    private int currentX, currentY;                                                                 //jelenlegi koordináták
    private int towardsX, towardsY;                                                                 //azok a koordináták, ami felé halad

    public GameObject(Bitmap bitmap, int speed, int startingX, int startingY) {
        this.bitmap = bitmap;
        this.speed = speed;
        setCurrentX(startingX);
        setCurrentY(startingY);
        setTowardsX(startingX);
        setTowardsY(startingY);
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setTowardsX(int towardsX) {
        this.towardsX = towardsX;
    }

    public void setTowardsY(int towardsY) {
        this.towardsY = towardsY;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setResizedBitmap(Bitmap bitmap, double scale) {
        this.resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*scale), (int)(bitmap.getHeight()*scale), true);      //megadott arányban kicsinyíti a képet
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap getResizedBitmap() {
        return resizedBitmap;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getTowardsX() {
        return towardsX;
    }

    public int getTowardsY() {
        return towardsY;
    }

    public int getSpeed() {
        return speed;
    }

    public void draw(Canvas canvas ){
        //canvas.drawBitmap(getResizedBitmap(),getCurrentX()-getResizedBitmap().getWidth()/2,getCurrentY()-getResizedBitmap().getHeight()/2, null);
        canvas.drawBitmap(resizedBitmap,currentX-resizedBitmap.getWidth()/2, currentY-resizedBitmap.getHeight()/2, null);
    }

    public void update(){}

}
