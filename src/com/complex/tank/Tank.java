package com.complex.tank;

import java.awt.*;

public class Tank {
    private int x = 200;
    private int y = 200;
    private Dir dir; //坦克方向
    private static final int SPEED = 5;//坦克速度
    private boolean moving = false; //坦克移动状态

    private TankFrame tf = null;


    public Tank(int x, int y, Dir dir, TankFrame tf) {
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
        /*Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);
        */
        switch (dir) {
            case LIFT:
                g.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD, x, y, null);
                break;
            default:
                break;
        }


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
        switch (dir) {
            case LIFT:
                tf.bullets.add(new Bullet(this.x, this.y + 22, this.dir, tf));
                break;
            case RIGHT:
                tf.bullets.add(new Bullet(this.x + 40, this.y + 23, this.dir, tf));
                break;
            case UP:
                tf.bullets.add(new Bullet(this.x + 20, this.y, this.dir, tf));
                break;
            case DOWN:
                tf.bullets.add(new Bullet(this.x + 18, this.y + 40, this.dir, tf));
                break;
            default:
                break;
        }

    }

}
