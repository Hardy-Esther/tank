package com.complex.tank;

import java.awt.*;

public class Tank {
    private int x = 200;
    private int y = 200;
    private Dir dir = Dir.LIFT; //坦克方向
    private static final int SPEED = 10;//坦克速度

    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        switch (dir){
            case LIFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
    }

}
