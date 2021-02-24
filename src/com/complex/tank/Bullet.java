package com.complex.tank;

import java.awt.*;
import java.util.UUID;

public class Bullet {
    private static final int SPEED = 10;
    private int x, y;
    private Dir dir;
    private boolean living = true;
    private TankFrame tf = null;
    private Group group;
    private UUID id = UUID.randomUUID();
    private UUID playerId;

    Rectangle rect = new Rectangle();

    public static int WIDTH = ResourceMgr.bulletU.getWidth();
    public static int HEIGHT = ResourceMgr.bulletU.getHeight();


    public Bullet(UUID playerId,int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = Group.BLUE;
        this.playerId = playerId;
        this.tf = tf;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Bullet(UUID playerId,int x, int y, Dir dir, Group group, TankFrame tf) {
        this(playerId,x, y, dir, tf);
        this.group = group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void paint(Graphics g) {

        if (!living) {
            tf.bullets.remove(this);
        }

        switch (dir) {
            case LIFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            default:
                break;
        }

        move();
    }

    public void move() {
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
        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    public void collideWith(Tank tank) {
        //if (this.group == tank.getGroup()) return;
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
        }
    }

    public void die() {
        this.living = false;
    }
}
