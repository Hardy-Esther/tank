package com.complex.tank;

import java.awt.*;

public class Tank {
    private int x = 200;
    private int y = 200;
    private Dir dir; //坦克方向
    private static final int SPEED = 5;//坦克速度
    private boolean moving = false; //坦克移动状态

    private TankFrame tf = null;


    public Tank(int x, int y, Dir dir,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);
        move();
    }

    public void move() {
        if (!moving) return;
        switch (dir) {
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

    public void fire() {
        tf.bullet = new Bullet(this.x+20,this.y+20,this.dir);
    }

}
