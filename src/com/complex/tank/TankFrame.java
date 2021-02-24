package com.complex.tank;

import com.complex.tank.net.Client;
import com.complex.tank.net.TankStartMovingMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    Tank myTank = new Tank(200,400,Dir.UP,Group.RED,this);

    List<Bullet>  bullets = new ArrayList<>();

    Map<UUID,Tank> tanks = new HashMap<>();

    List<Explode> explodes = new ArrayList<>();


    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;

    private TankFrame() {
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("tank war");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new MyKeyListener());
    }

    Image offScreenImage = null;
    @Override
    public void update(Graphics g){
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量："+bullets.size(),10,60);
        g.setColor(c);
        myTank.paint(g);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        tanks.values().stream().forEach((e)->e.paint(g));
        /*
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        */

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
    }


    class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            //x += 20;
            //调用repaint()方法会默认调用paint
            //repaint();
            //System.out.println("key pressed");
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("key released");
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bR && !bU && !bD){
                myTank.setMoving(false);
                return;
            }
            Dir oldDir = myTank.getDir();
            if (bL) myTank.setDir(Dir.LIFT);
            if (bR) myTank.setDir(Dir.RIGHT);
            if (bU) myTank.setDir(Dir.UP);
            if (bD) myTank.setDir(Dir.DOWN);
            if(!myTank.getMoving() || !oldDir.equals(myTank.getDir())) Client.INSTANCE.send(new TankStartMovingMsg(myTank));
            myTank.setMoving(true);
        }

    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    public Tank getMainTank() {
        return this.myTank;
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }


}
