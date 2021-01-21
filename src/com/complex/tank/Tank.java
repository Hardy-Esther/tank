package com.complex.tank;

import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.util.Random;

public class Tank {
    private int x = 200;
    private int y = 200;
    private Dir dir; //坦克方向
    private static final int SPEED = 5;//坦克速度
    private boolean moving = true; //坦克移动状态
    private boolean living = true;
    private Group group;
    public static int WIDTH = ResourceMgr.tankU.getWidth();
    public static int HEIGHT = ResourceMgr.tankU.getHeight();


    private TankFrame tf = null;

    private Random random = new Random();

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = Group.BLUE;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
        }
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
            default:
                break;
        }
        if (x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
        if (random.nextInt(10) > 8) this.fire();
    }

    public void fire() {
        switch (dir) {
            case LIFT:
                tf.bullets.add(new Bullet(this.x, this.y + 15, this.dir, this.group, tf));
                break;
            case RIGHT:
                tf.bullets.add(new Bullet(this.x + 40, this.y + 15, this.dir, this.group, tf));
                break;
            case UP:
                tf.bullets.add(new Bullet(this.x + 15, this.y, this.dir, this.group, tf));
                break;
            case DOWN:
                tf.bullets.add(new Bullet(this.x + 15, this.y + 40, this.dir, this.group, tf));
                break;
            default:
                break;
        }

    }

    public void die() {
        this.living = false;
        this.tf.explodes.add(new Explode(this.x, this.y, this.tf));
    }

}
