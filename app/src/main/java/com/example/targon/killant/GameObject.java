package com.example.targon.killant;

import android.graphics.Rect;

/**
 * Created by Targon on 15.02.2016.
 */
public abstract class GameObject {
    protected int y;
    protected int x;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getWidthO() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeightO() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getRectangle(){
        return new Rect(x-5, y-5, x+50, y+50);
    }
}
