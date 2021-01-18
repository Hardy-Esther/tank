package com.complex.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    int x = 200;
    int y = 200;
    public TankFrame(){
        this.setSize(800,600);
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
    @Override
    public void paint(Graphics g){
        g.fillRect(x,y,50,50);
        x += 10;
    }

    class MyKeyListener extends KeyAdapter{
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;
        @Override
        public void keyPressed(KeyEvent e){
            //x += 20;
            //调用repaint()方法会默认调用paint
            //repaint();
            //System.out.println("key pressed");
            int key = e.getKeyCode();
            switch (key){
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


        }

        @Override
        public void keyReleased(KeyEvent e){
            //System.out.println("key released");
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL =false;
                    break;
                case KeyEvent.VK_RIGHT:
                     bR = false;
                     break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                default:
                    break;
            }
            
        }

    }

}
