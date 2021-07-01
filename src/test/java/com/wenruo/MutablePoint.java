package com.wenruo;

/**
 * @className: MutablePoint
 * @author: muyao
 * @description: 定位类
 * @date: 2021/3/4 4:27 下午
 * @version: 1.0
 */
public class MutablePoint {

    private int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
